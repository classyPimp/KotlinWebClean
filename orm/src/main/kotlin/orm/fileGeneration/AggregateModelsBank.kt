package orm.fileGeneration

import orm.templateDataModels.model.ModelDataModel

/**
 * Created by classypimp on 9/14/17.
 */
object AggregateModelsBank {

    val models: HashMap<String, ModelDataModel> = hashMapOf()

    fun registerModel(modelDataModel: ModelDataModel) {
        val valueAtKey = models[modelDataModel.modelClass]
        if (valueAtKey != null) {
            throw Throwable("model ${modelDataModel.modelClass}: already registered")
        }
        models[modelDataModel.modelClass] = modelDataModel
    }

}