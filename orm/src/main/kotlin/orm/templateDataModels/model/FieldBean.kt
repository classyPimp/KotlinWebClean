package orm.templateDataModels.model

import javax.lang.model.element.*

/**
 * Created by classypimp on 9/14/17.
 */
class FieldBean
constructor(
        val element: Element,
        val property: String,
        val tableFieldName: String,
        val type: String,
        val isPrimaryKey: Boolean,
        val nonNullableType: String
)
{
    var capitalizedProperty: String
    init {
        capitalizedProperty = property.first().toUpperCase() + property.substring(1, property.length)
    }
    val actualDbFieldName = tableFieldName.toLowerCase()
}

//class FieldTestBean(element: Element) {
//    val simpleName: String
//    val kind: String
//    val returnType: String
//    val enclElems: String
//    //val rtqn: String
//    init {
//        var enc = ""
//        element.getAnnotation(org.jetbrains.annotations.Nullable::class.java)
//
//        enclElems = element.enclosedElements.size.toString()
//
//        val el = element
//        simpleName = el.simpleName.toString()
//        kind = el.kind.toString()
//        returnType = el.asType().toString()
//
//        //val rt  = el.returnType as TypeElement
//        //rtqn = rt.qualifiedName.toString()
//    }
//
//}