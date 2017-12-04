package orm.templateDataModels.model

import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements

/**
 * Created by classypimp on 9/7/17.
 */
class ModelDataModel(
        val typeElement: TypeElement,
        val modelClass: String,
        val fieldBeans: MutableList<FieldBean>,
        val primaryKey: FieldBean,
        val jooqTableInstance: String,
        val fieldBeansExceptPrimaryKey: MutableList<FieldBean>,
        val packagesBean: PackagesBean,
        val hasTimestamps: Boolean?,
        val jooqTableSimpleName: String,
        val delegateFromRecordProperties: MutableList<DelegateFromRecordPropertyBean> = mutableListOf()
) {
    lateinit var associationBeans: MutableList<AssociationBean>
    val modelClassDecapitalized = Character.toLowerCase(modelClass[0]) + modelClass.substring(1);
    val jooqRecordClass: String = jooqTableInstance.let {
        jooqTableSimpleName + "Record"
    }
}
//
//class TestBean(element: Element, elementUtils: Elements) {
//    val qualifiedName: String
//    val simpleName: String
//    val enc: String
//    var accData = ""
//    init {
//        var en = ""
//        element.enclosedElements.forEach {
//            e ->
//            var ans = ""
//            e.annotationMirrors.forEach {
//                m ->
//                ans += (m.annotationType.toString() + "--")
//            }
//            en += "kind: ${e.kind}, name: ${e.simpleName} anns: ${ans} ; "
//        }
//        enc = en
//        val typeElem = element as TypeElement
//        qualifiedName = typeElem.qualifiedName.toString()
//        simpleName = typeElem.simpleName.toString()
//        val acc = elementUtils.getTypeElement("models.account.Account")
//        accData += "${acc.simpleName} -- ${acc.qualifiedName}"
//    }
//
//}

