package orm.templateDataModels.model

import org.jetbrains.annotations.Nullable
import orm.annotations.DelegatesFunction
import orm.annotations.DelegatesGetter
import orm.annotations.DelegatesProperty
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.ExecutableElement

/**
 * Created by Муса on 16.11.2017.
 */

class DelegateFromRecordPropertiesFactory {

    fun create(element: Element): MutableList<DelegateFromRecordPropertyBean> {
        val delegateProperties = mutableListOf<DelegateFromRecordPropertyBean>()
        element.enclosedElements.forEach {
            produceAndAddIfDelegates(it, delegateProperties)
        }
        return delegateProperties
    }

    private fun produceAndAddIfDelegates(element: Element, delegateProperties: MutableList<DelegateFromRecordPropertyBean>) {
        ifDelegatesGetter(element) {
            delegatesGetterAnnotation ->
            delegateProperties.add(produceDelegatesGetter(delegatesGetterAnnotation, element))
        }

        ifDelegatesProperty(element) {
            delegatesPropertyAnnotation ->
            delegateProperties.add(produceDelegatesProperty(delegatesPropertyAnnotation, element))
        }
        //to hard to make it right so it's unused
//        ifDelegatesFunction(element) {
//            delegatesFunctionAnnotation ->
//            delegateProperties.add(produceDelegatesFunction(delegatesFunctionAnnotation, element))
//        }
    }

    //to hard to make it right so it's unused
    private fun produceDelegatesFunction(delegatesFunctionAnnotation: DelegatesFunction, element: Element): DelegateFromRecordPropertyBean {
        val delegatesType = "FUNCTION"
        val exElement = (element as ExecutableElement)

        val property: String = element.simpleName.toString()
        val parameters = mutableMapOf<String, String>()

        val type: String = element.returnType.toString()
        val nonNullableType: String = constructNonNullableType(type)

        exElement.parameters.forEach {
            parameters[it.simpleName.toString()] = constructType(it)
        }

        return DelegateFromRecordPropertyBean(
                delegatesType = delegatesType,
                element = element,
                property = property,
                type = type,
                nonNullableType = nonNullableType
        ).also { it.parameters = parameters }
    }

    private fun ifDelegatesFunction(element: Element, block: (DelegatesFunction) -> Unit) {
        val delegatePropertyAnnotation: DelegatesFunction? = element.getAnnotation(DelegatesFunction::class.java)
        delegatePropertyAnnotation?.let {
            block.invoke(it)
        }
    }

    private fun produceDelegatesProperty(delegatesPropertyAnnotation: DelegatesProperty, element: Element): DelegateFromRecordPropertyBean {
        val delegatesType = "PROPERTY"
        val property: String = element.simpleName.toString()
        val type: String = constructType(element)
        val nonNullableType = constructNonNullableType(type)

        return DelegateFromRecordPropertyBean(
                delegatesType = delegatesType,
                element = element,
                property = property,
                type = type,
                nonNullableType = nonNullableType
        )
    }

    private fun ifDelegatesProperty(element: Element, block: (DelegatesProperty) -> Unit) {
        val delegatePropertyAnnotation: DelegatesProperty? = element.getAnnotation(DelegatesProperty::class.java)
        delegatePropertyAnnotation?.let {
            block.invoke(it)
        }
    }

    private fun produceDelegatesGetter(delegatesGetterAnnotation: DelegatesGetter, element: Element): DelegateFromRecordPropertyBean {
        val delegatesType = "GETTER"
        val property: String = element.simpleName.toString().substring(3).let {
            it.first().toLowerCase() + it.substring(1)
        }
        val type: String = constructType(element).substring(2)
        val nonNullableType = constructNonNullableType(type)

        return DelegateFromRecordPropertyBean(
                delegatesType = delegatesType,
                element = element,
                property = property,
                type = type,
                nonNullableType = nonNullableType
        )
    }

    private fun constructType(element: Element): String {
        var baseTypeName = element.asType().toString()//.split('.').last()
        if (isNullableProperty(element)) {
            baseTypeName += "?"
        }
        return baseTypeName
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

    private fun ifDelegatesGetter(element: Element, block: (DelegatesGetter)->Unit) {
        if (element.kind == ElementKind.METHOD) {
            val delegatePropertyAnnotation: DelegatesGetter? = element.getAnnotation(DelegatesGetter::class.java)
            delegatePropertyAnnotation?.let {
                block.invoke(it)
            }
        }
    }

}