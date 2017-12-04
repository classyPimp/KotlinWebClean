package orm.templateDataModels.model

import org.jetbrains.annotations.Nullable
import orm.annotations.IsPrimaryKey
import orm.annotations.TableField
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind

/**
 * Created by classypimp on 9/14/17.
 */
object FieldBeansListFactory {

    fun create(element: Element): MutableList<FieldBean> {
        val fieldBeans = mutableListOf<FieldBean>()

        element.enclosedElements.forEach { enclosedElement ->
            ifIsTableField(enclosedElement) {
                tableFieldAnnotation ->

                fieldBeans.add(
                        produceFieldBean(tableFieldAnnotation, enclosedElement)
                )
            }
        }

        return fieldBeans
    }

    inline private fun ifIsTableField(element: Element, lambda: (tableField: TableField) -> Unit) {
        if(element.kind == ElementKind.FIELD){
            val fieldAnnotation: TableField? = element.getAnnotation(TableField::class.java)

            if (fieldAnnotation != null) {
                lambda.invoke(fieldAnnotation)
            }
        }
    }

    private fun produceFieldBean(tableFieldAnnotation: TableField, element: Element): FieldBean {

        val property: String = element.simpleName.toString()//.toString().split("$").get(0)
        val tableFieldName: String = tableFieldAnnotation.name
        val type: String = counstructType(element)//element.asType().toString().split('.').last() + "?"
        val isPrimaryKey = doesHavePrimaryKeyAnnotation(element)
        val nonNullableType = constructNonNullableType(type)

        return FieldBean(
                element = element,
                property = property,
                tableFieldName = tableFieldName,
                type = type,
                isPrimaryKey = isPrimaryKey,
                nonNullableType = nonNullableType
        )
    }

    private fun counstructType(element: Element): String {
        var baseTypeName = element.asType().toString().split('.').last()
        baseTypeName = convertTypeIfNecessary(baseTypeName)
        if (isNullableProperty(element)) {
            baseTypeName += "?"
        }
        return baseTypeName
    }

    private fun convertTypeIfNecessary(baseTypeName: String): String {
        when (baseTypeName) {
            "Integer" -> {
                return "Int"
            }
            else -> {
                return baseTypeName
            }
        }
    }

    private fun isNullableProperty(element: Element): Boolean {
        val nullableAnnotation = element.getAnnotation(Nullable::class.java)
        if (nullableAnnotation == null) {
            return false
        } else {
            return true
        }
    }

    private fun constructNonNullableType(type: String): String {
        val kotlinQuestionMark = type[type.length - 1]
        if (kotlinQuestionMark == '?'){
            return type.substring(0, type.length - 1)
        } else {
            return type
        }
    }

    private fun doesHavePrimaryKeyAnnotation(element: Element): Boolean {
        val annotation = element.getAnnotation(IsPrimaryKey::class.java)
        if (annotation != null) {
            return true
        } else {
            return false
        }
    }

}
