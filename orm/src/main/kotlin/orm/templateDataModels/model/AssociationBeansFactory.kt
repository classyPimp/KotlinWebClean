package orm.templateDataModels.model

import orm.annotations.*
import orm.fileGeneration.AggregateModelsBank
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.type.MirroredTypeException
import javax.lang.model.type.MirroredTypesException
import javax.lang.model.type.TypeMirror

/**
 * Created by classypimp on 9/22/17.
 */
object AssociationBeansFactory {

    fun createAll(modelDataModels: MutableCollection<ModelDataModel>) {
        modelDataModels.forEach {
            it.associationBeans = create(it)
            ModelDataModelFactory.injectInPackagesBeanAssociatedModelTypesToImportSet(it, it.associationBeans)
        }
    }

    fun create(modelDataModel: ModelDataModel): MutableList<AssociationBean> {
        val associationsBeansToReturn = mutableListOf<AssociationBean>()
        buildAssociationBeans(associationsBeansToReturn, modelDataModel)
        return associationsBeansToReturn
    }

    fun buildAssociationBeans(associationsBeansToReturn: MutableList<AssociationBean>, modelDataModel: ModelDataModel) {

        for (element: Element in modelDataModel.typeElement.enclosedElements) {
            if (element.kind != ElementKind.FIELD) {
                continue
            }
            element.getAnnotation(BelongsToPolymorphic::class.java)?.let {
                annotation ->
                val associationBean = buildBelongsToPolymorphicAssociationBean(modelDataModel, element, annotation)
                associationsBeansToReturn.add(associationBean)
            }
            ?: element.getAnnotation(BelongsTo::class.java)?.let {
                annotaion ->
                val associationBean = buildBelongsToAssociationBean(modelDataModel, element, annotaion)
                associationsBeansToReturn.add(associationBean)
            }
            ?: element.getAnnotation(HasOne::class.java)?.let {
                annotation ->
                val associationBean = buildHasOneAssociationBean(modelDataModel, element, annotation)
                associationsBeansToReturn.add(associationBean)
            }
            ?: element.getAnnotation(HasMany::class.java)?.let {
                annotation ->
                val associationBean = buildHasManyAssociationBean(modelDataModel, element, annotation)
                associationsBeansToReturn.add(associationBean)
            }
            ?: element.getAnnotation(HasOneAsPolymorphic::class.java)?.let {
                annotation ->
                val associationBean = buildHasOneAsPolymorphicAssociationBean(modelDataModel, element, annotation)
                associationsBeansToReturn.add(associationBean)
            }
            ?: element.getAnnotation(HasManyAsPolymorphic::class.java)?.let {
                annotation ->
                buildHasManyAsPolymorphicAssociationBean(modelDataModel, element, annotation).let {
                    associationsBeansToReturn.add(it)
                }
            }
        }
    }

    private fun buildHasManyAsPolymorphicAssociationBean(
            modelDataModel: ModelDataModel,
            element: Element,
            annotation: HasManyAsPolymorphic
    ): AssociationBean {
        val associatedPropertyName: String = element.simpleName.toString()
        val fieldOnThisName: String = annotation.fieldOnThis
        val fieldOnThatName: String = annotation.fieldOnThat
        val associatedModelDataModel: ModelDataModel = getAssociatedModelDataModel {
            annotation.model
        }
        val associationBean = AssociationBean(
                associationType = "HAS_MANY_AS_POLYMORPHIC",
                propertyName = associatedPropertyName,
                associatedModelDataModel = associatedModelDataModel,
                fieldOnThis = FieldBeansUtils.getFieldBeanByName(modelDataModel.fieldBeans, fieldOnThisName),
                fieldOnThat = FieldBeansUtils.getFieldBeanByName(associatedModelDataModel.fieldBeans, fieldOnThatName)
        )
        associationBean.associationType = "HAS_MANY_AS_POLYMORPHIC"
        associationBean.polymorphicTypeField = FieldBeansUtils.getFieldBeanByName(associationBean.associatedModelDataModel.fieldBeans, annotation.polymorphicTypeField)
        return associationBean
    }

    private fun buildHasOneAsPolymorphicAssociationBean(
            modelDataModel: ModelDataModel,
            element: Element,
            annotation: HasOneAsPolymorphic
    ): AssociationBean {
        val associatedPropertyName: String = element.simpleName.toString()
        val fieldOnThisName: String = annotation.fieldOnThis
        val fieldOnThatName: String = annotation.fieldOnThat
        val associatedModelDataModel: ModelDataModel = getAssociatedModelDataModel {
            annotation.model
        }

        val associationBean = AssociationBean(
                associationType = "HAS_ONE",
                propertyName = associatedPropertyName,
                associatedModelDataModel = associatedModelDataModel,
                fieldOnThis = FieldBeansUtils.getFieldBeanByName(modelDataModel.fieldBeans, fieldOnThisName),
                fieldOnThat = FieldBeansUtils.getFieldBeanByName(associatedModelDataModel.fieldBeans, fieldOnThatName)
        )
        associationBean.associationType = "HAS_ONE_AS_POLYMORPHIC"
        associationBean.polymorphicTypeField = FieldBeansUtils.getFieldBeanByName(associationBean.associatedModelDataModel.fieldBeans, annotation.polymorphicTypeField)
        return associationBean
    }

    private fun buildBelongsToPolymorphicAssociationBean(
             modelDataModel: ModelDataModel,
             enclosedElement: Element,
             belongsToPolymorphicAnnotation: BelongsToPolymorphic
    ): AssociationBean {
        val associatedPropertyName: String = enclosedElement.simpleName.toString()
        val fieldOnThisName: String = belongsToPolymorphicAnnotation.fieldOnThis
        val fieldOnThatName: String = belongsToPolymorphicAnnotation.fieldOnThat

        val polymorphicTypeFieldName: String = belongsToPolymorphicAnnotation.polymorphicTypeField
        val associatedPolymorphicModelDataModels: MutableList<ModelDataModel> = getAssociatedModelDataModelList {
            belongsToPolymorphicAnnotation.models
        }
        val firstModel = associatedPolymorphicModelDataModels[0]
        val beanToReturn =  AssociationBean(
                associationType = "BELONGS_TO_POLYMORPHIC",
                propertyName = associatedPropertyName,
                associatedModelDataModel = firstModel, //don't know how to do it without breaking interface, this will be unused
                fieldOnThis = FieldBeansUtils.getFieldBeanByName(modelDataModel.fieldBeans, fieldOnThisName),
                fieldOnThat = FieldBeansUtils.getFieldBeanByName(firstModel.fieldBeans, fieldOnThatName) //fieldOnThat should be of same type on all models so first's is used
        )
        beanToReturn.associatedPolymorphicModelDataModels = associatedPolymorphicModelDataModels
        beanToReturn.polymorphicTypeField = FieldBeansUtils.getFieldBeanByName(modelDataModel.fieldBeans, polymorphicTypeFieldName)
        return beanToReturn
    }

    private fun buildBelongsToAssociationBean(
            modelDataModel: ModelDataModel,
            enclosedElement: Element,
            belongsToAnnotation: BelongsTo
    ): AssociationBean {
        val associatedPropertyName: String = enclosedElement.simpleName.toString()
        val fieldOnThisName: String = belongsToAnnotation.fieldOnThis
        val fieldOnThatName: String = belongsToAnnotation.fieldOnThat
        val associatedModelDataModel: ModelDataModel = getAssociatedModelDataModel {
            belongsToAnnotation.model
        }

        return AssociationBean(
                associationType = "BELONGS_TO",
                propertyName = associatedPropertyName,
                associatedModelDataModel = associatedModelDataModel,
                fieldOnThis = FieldBeansUtils.getFieldBeanByName(modelDataModel.fieldBeans, fieldOnThisName),
                fieldOnThat = FieldBeansUtils.getFieldBeanByName(associatedModelDataModel.fieldBeans, fieldOnThatName)
        )
    }

    private fun buildHasManyAssociationBean(
            modelDataModel: ModelDataModel,
            enclosedElement: Element,
            hasManyAnnotation: HasMany
    ): AssociationBean {
        val associatedPropertyName: String = enclosedElement.simpleName.toString()
        val fieldOnThisName: String = hasManyAnnotation.fieldOnThis
        val fieldOnThatName: String = hasManyAnnotation.fieldOnThat
        val associatedModelDataModel: ModelDataModel = getAssociatedModelDataModel {
            hasManyAnnotation.model
        }

        return AssociationBean(
                associationType = "HAS_MANY",
                propertyName = associatedPropertyName,
                associatedModelDataModel = associatedModelDataModel,
                fieldOnThis = FieldBeansUtils.getFieldBeanByName(modelDataModel.fieldBeans, fieldOnThisName),
                fieldOnThat = FieldBeansUtils.getFieldBeanByName(associatedModelDataModel.fieldBeans, fieldOnThatName)
        )
    }

    private fun buildHasOneAssociationBean(
            modelDataModel: ModelDataModel,
            enclosedElement: Element,
            hasOneAnnotation: HasOne
    ): AssociationBean {
        val associatedPropertyName: String = enclosedElement.simpleName.toString()
        val fieldOnThisName: String = hasOneAnnotation.fieldOnThis
        val fieldOnThatName: String = hasOneAnnotation.fieldOnThat
        val associatedModelDataModel: ModelDataModel = getAssociatedModelDataModel {
            hasOneAnnotation.model
        }

        return AssociationBean(
                associationType = "HAS_ONE",
                propertyName = associatedPropertyName,
                associatedModelDataModel = associatedModelDataModel,
                fieldOnThis = FieldBeansUtils.getFieldBeanByName(modelDataModel.fieldBeans, fieldOnThisName),
                fieldOnThat = FieldBeansUtils.getFieldBeanByName(associatedModelDataModel.fieldBeans, fieldOnThatName)
        )
    }

    private fun getAssociatedModelDataModel(block: ()->Unit): ModelDataModel {
        var associatedModelDataModel: ModelDataModel? = null
        val simpleName2: String
        try {
            block.invoke()
            throw Exception("block should throw MirroredTypeException")
        } catch (error: MirroredTypeException) {
            val typeMirror = error.typeMirror
            val simpleName = typeMirror.toString().split('.').last()
            simpleName2=  simpleName
            associatedModelDataModel =  AggregateModelsBank.models[simpleName]
        }
        if (associatedModelDataModel == null) {
            throw Exception("Associated class not found ${simpleName2}")
        } else {
            return associatedModelDataModel
        }
    }

    private fun getAssociatedModelDataModelList(block: ()->Unit): MutableList<ModelDataModel> {
        val associatedModelDataModelList = mutableListOf<ModelDataModel>()
        try {
            block.invoke()
            throw Exception("block should throw MirroredTypesException")
        } catch (error: MirroredTypesException) {
            error.typeMirrors.forEach {
                val simpleName = it.toString().split('.').last()
                val modelDataModel = AggregateModelsBank.models[simpleName]
                if (modelDataModel == null) {
                    throw Exception("Associated class not found")
                } else {
                    associatedModelDataModelList.add(modelDataModel)
                }
            }
        }
        return associatedModelDataModelList
    }

}