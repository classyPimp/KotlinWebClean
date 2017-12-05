package orm.templateDataModels.model

import orm.annotations.IsModel
import orm.fileGeneration.AggregateModelsBank
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.lang.model.type.MirroredTypeException
import javax.lang.model.util.Elements


/**
 * Created by classypimp on 9/8/17.
 */
object ModelDataModelFactory {

    fun createAllAndAddToModelsBank(annotatedElements: MutableSet<out Element>, elementUtils: Elements) {
        annotatedElements.forEach {
            val modelDataModel = createDefaultUsing(it, elementUtils)
            AggregateModelsBank.registerModel(modelDataModel)
        }
    }

    fun injectInPackagesBeanAssociatedModelTypesToImportSet(modelDataModel: ModelDataModel, associationBeans: MutableList<AssociationBean>){
        val setToInject = mutableSetOf<String>()

        for (associationBean in associationBeans) {
            if (associationBean.associatedModelDataModel.modelClass == modelDataModel.modelClass) {
                continue
            }
            if (associationBean.associationType == "BELONGS_TO_POLYMORPHIC") {
                associationBean.associatedPolymorphicModelDataModels.forEach {
                    setToInject.add(it.packagesBean.model)
                    setToInject.add("${it.packagesBean.baseGenerated}.${it.modelClass}DefaultDeleter")
                    setToInject.add("${it.packagesBean.baseGenerated}.${it.modelClass}DefaultFinder")
                    setToInject.add("${it.packagesBean.baseGenerated}.${it.modelClass}SelectQueryBuilder")
                    setToInject.add("${it.packagesBean.baseGenerated}.${it.modelClass}Utils")
                    setToInject.add("${it.packagesBean.baseGenerated}.${it.modelClass}AssociationsJoinBuilder")
                    setToInject.add("${it.packagesBean.baseGenerated}.${it.modelClass}ToJsonSerializer")
                    setToInject.add("${it.packagesBean.jooqGeneratedTable}")
                    setToInject.add("${it.packagesBean.jooqGeneratedTable}.*")
                }
                continue
            }
            setToInject.add(associationBean.associatedModelDataModel.packagesBean.model)
            setToInject.add("${associationBean.associatedModelDataModel.packagesBean.baseGenerated}.${associationBean.associatedModelDataModel.modelClass}DefaultDeleter")
            setToInject.add("${associationBean.associatedModelDataModel.packagesBean.baseGenerated}.${associationBean.associatedModelDataModel.modelClass}DefaultFinder")
            setToInject.add("${associationBean.associatedModelDataModel.packagesBean.baseGenerated}.${associationBean.associatedModelDataModel.modelClass}Utils")
            setToInject.add("${associationBean.associatedModelDataModel.packagesBean.baseGenerated}.${associationBean.associatedModelDataModel.modelClass}SelectQueryBuilder")
            setToInject.add("${associationBean.associatedModelDataModel.packagesBean.baseGenerated}.${associationBean.associatedModelDataModel.modelClass}AssociationsJoinBuilder")
            setToInject.add("${associationBean.associatedModelDataModel.packagesBean.baseGenerated}.${associationBean.associatedModelDataModel.modelClass}ToJsonSerializer")
            setToInject.add("${associationBean.associatedModelDataModel.packagesBean.jooqGeneratedTable}")
            setToInject.add("${associationBean.associatedModelDataModel.packagesBean.jooqGeneratedTable}.*")
        }

        modelDataModel.packagesBean.associatedModelTypesToImport = setToInject
    }

    fun createDefaultUsing(element: Element, elementUtils: Elements): ModelDataModel {

        val typeElement = element as TypeElement

        val modelClassName = element.simpleName.toString()
        val packageName = "orm.${modelClassName.toLowerCase()}generatedrepository"
        val fieldBeans = constructFields(element)
        val primaryKey = getPrimaryKeyFieldBeanFrom(fieldBeans)
        val modelPackage = typeElement.qualifiedName.toString()
        val jooqGeneratedTableToImport = getJooqGeneratedTableToImport(element)
        val importStatementsForFieldTypes = getImportStatementsForFieldTypes(fieldBeans)
        val jooqTableClassInstance = getJooqTableClassInstance(jooqGeneratedTableToImport)
        val fieldBeansExceptPrimaryKey = buildFieldBeanListWithoutPrimaryKey(fieldBeans)
        val hasTimestamps = defineIfHasTimestamps(fieldBeans)
        val jooqTableSimpleName = getJooqTableSimpleName(jooqGeneratedTableToImport)
        val jooqTablesRoot = getJooqTableRoot(jooqGeneratedTableToImport)
        val delegateFromRecordProperties = DelegateFromRecordPropertiesFactory().create(element)

        val packagesBean = PackagesBean(
                baseGenerated = packageName,
                jooqGeneratedTable = jooqGeneratedTableToImport,
                model = modelPackage,
                fieldTypes = importStatementsForFieldTypes,
                jooqTablesRoot = jooqTablesRoot
        )

        val modelDataModelToReturn =  ModelDataModel(
                typeElement = typeElement,
                modelClass = modelClassName,
                fieldBeans = fieldBeans,
                primaryKey = primaryKey,
                jooqTableInstance = jooqTableClassInstance,
                fieldBeansExceptPrimaryKey = fieldBeansExceptPrimaryKey,
                packagesBean = packagesBean,
                hasTimestamps = hasTimestamps,
                jooqTableSimpleName = jooqTableSimpleName,
                delegateFromRecordProperties = delegateFromRecordProperties
        )

        return modelDataModelToReturn
    }

    private fun getJooqTableRoot(jooqGeneratedTableToImport: String): String {
        val list = jooqGeneratedTableToImport.split('.').toMutableList()
        list.removeAt(list.lastIndex)
        return list.joinToString(".")
    }

    private fun getJooqTableSimpleName(jooqGeneratedTableToImport: String): String {
        val list = jooqGeneratedTableToImport.split('.')
        return list.last()
    }

    private fun defineIfHasTimestamps(fieldBeans: MutableList<FieldBean>): Boolean {
        var createdAtFound = false
        var updatedAtFound = false

        fieldBeans.forEach {
            when (it.tableFieldName) {
                "CREATED_AT" ->
                        createdAtFound = true
                "UPDATED_AT" ->
                        updatedAtFound =true
            }
        }
        return (createdAtFound && updatedAtFound)
    }

    private fun buildFieldBeanListWithoutPrimaryKey(fieldBeans: MutableList<FieldBean>): MutableList<FieldBean> {
        return fieldBeans.fold(mutableListOf<FieldBean>()) {
            newList, fieldBean ->
            if (!fieldBean.isPrimaryKey) {
                newList.add(fieldBean)
            }
            newList
        }
    }

    private fun getJooqTableClassInstance(jooqGeneratedTebleToImport: String): String {
        val sb = StringBuilder()
        val name = jooqGeneratedTebleToImport.split(".").last()
        name.forEach {
            if (Character.isUpperCase(it)) {
                sb.append('_')
            }
            sb.append(it.toUpperCase())
        }

        sb.toString().let {
            if (it[0] == '_') {
                return it.substring(1)
            } else {
                return it
            }
        }

    }

    private fun getImportStatementsForFieldTypes(fieldBeans: List<FieldBean>): MutableSet<String> {
        val importStatements = mutableSetOf<String>()
        for (fieldBean in fieldBeans) {
            val typesForField = FieldTypeImportPathSetter.getPathToType(fieldBean.type)

            for (importTypeStatement in typesForField) {
                importStatements.add(importTypeStatement)
            }
        }
        return importStatements
    }

    private fun getJooqGeneratedTableToImport(element: Element): String {
        val isModelAnnotation = element.getAnnotation(IsModel::class.java)
        try {
            return isModelAnnotation.jooqTable.qualifiedName.toString()
        } catch (error: MirroredTypeException) {
            val typeMirror = error.typeMirror
            return typeMirror.toString()
        }

    }

    private fun getModelPackage(element: Element): String {
        return element.enclosingElement.asType().toString()
    }

    private fun getPrimaryKeyFieldBeanFrom(fieldBeans: MutableList<FieldBean>): FieldBean {
        var primaryKeyedFieldBean: FieldBean? = null
        fieldBeans.forEach {
            fieldBean ->
            if (fieldBean.isPrimaryKey) {
                primaryKeyedFieldBean = fieldBean
            }
        }
        if (primaryKeyedFieldBean == null) {
            throw Exception("no fieldBean with primary key found")
        }

        return primaryKeyedFieldBean!!
    }

    private fun constructFields(element: Element): MutableList<FieldBean> {
        val fieldBeans = FieldBeansListFactory.create(element)
        return fieldBeans
    }

}