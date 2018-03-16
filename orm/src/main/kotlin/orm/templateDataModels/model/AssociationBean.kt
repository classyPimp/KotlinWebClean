package orm.templateDataModels.model

/**
 * Created by classypimp on 9/22/17.
 */
class AssociationBean
constructor(
        var associationType: String,
        val propertyName: String,
        val associatedModelDataModel: ModelDataModel,
        val fieldOnThis: FieldBean,
        val fieldOnThat: FieldBean
)
{
    val capitalizedPropertyName = propertyName.first().toUpperCase() + propertyName.substring(1, propertyName.length)

    lateinit var polymorphicTypeField: FieldBean
    lateinit var associatedPolymorphicModelDataModels: MutableList<ModelDataModel>
    lateinit var completeReturnType: String

    init {
        when(associationType) {
            "HAS_MANY" -> {
                completeReturnType = "MutableList<${associatedModelDataModel.modelClass}>?"
            }
            "HAS_MANY_AS_POLYMORPHIC" -> {
                completeReturnType = "MutableList<${associatedModelDataModel.modelClass}>?"
            }
            "BELONGS_TO_POLYMORPHIC" -> {
                completeReturnType = "Any?"
            }
            else -> {
                completeReturnType = "${associatedModelDataModel.modelClass}?"
            }
        }
    }

}

