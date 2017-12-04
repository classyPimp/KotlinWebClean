package orm.fileGeneration.fileGenerators

import orm.fileGeneration.AggregateModelsBank
import orm.templateDataModels.model.ModelDataModel
import orm.templateDataModels.model.ModelDataModelFactory
import javax.lang.model.element.Element
import javax.lang.model.util.Elements

/**
 * Created by classypimp on 9/6/17.
 */
object GenerateModelFiles {

    private fun generate(modelDataModel: ModelDataModel) {

        GenerateModelUtils(modelDataModel).run()

        GenerateModelSelectQueryBuilder(modelDataModel).run()

        GenerateModelInsertqueryBuilder(modelDataModel).run()

        GenerateModelUpdateQueryBuilder(modelDataModel).run()

        GenerateModelDeleteQueryBuilder(modelDataModel).run()

        GenerateModelToJsonSerializer(modelDataModel).run()

        GenerateModelPropertiesChangeTracker(modelDataModel).run()

        GenerateModelRecord(modelDataModel).run()

        GenerateModelAssociationsPreloader(modelDataModel).run()

        GenerateModelJoinBuilder(modelDataModel).run()

        GenerateModelFieldsAccessor(modelDataModel).run()

        GenerateModelDefaultResultSetParser(modelDataModel).run()

        GenerateModelDefaultCreator(modelDataModel).run()

        GenerateModelDefaultFinder(modelDataModel).run()

        GenerateModelDefaultUpdater(modelDataModel).run()

        GenerateModelDefaultDeleter(modelDataModel).run()

        GenerateModelAssociationsManager(modelDataModel).run()

        GenerateModelValidationManager(modelDataModel).run()

        GenerateModelValidatorTrait(modelDataModel).run()

    }

    fun generate(modelDataModels: MutableCollection<ModelDataModel>) {
        modelDataModels.forEach {
            generate(it)
        }
    }


}