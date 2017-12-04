package orm.configs.templatingEngine

/**
 * Created by classypimp on 9/8/17.
 */
object TemplateFilePathsConfig {
    val modelFieldsAccessorTemplate: String = "model/modelFieldAccessor.ftl"
    val modelDefaultCreatorTemplate: String = "model/modelDefaultCreator.ftl"
    val modelDefaultDeleterTemplate: String = "model/modelDefaultDeleter.ftl"
    val modelDefaultResultSetParser: String = "model/modelDefaultResultSetParser.ftl"
    val modelDefaultFinder: String = "model/modelDefaultFinder.ftl"
    val modelDefaultUpdater: String = "model/modelDefaultUpdater.ftl"
    val modelDefaultAssociationManager: String = "model/modelDefaultAssociationManager.ftl"
    val modelSelectQueryBuilder = "model/selectQueryBuilder.ftl"
    val modelJoinBuilder = "model/joinBuilder.ftl"
    val modelUtils = "model/utils.ftl"
    val modelAssociationsPreloaderTemplate = "model/associationsPreloader.ftl"
    val modelInsertQueryBuilder = "model/insertQueryBuilder.ftl"
    val modelUpdateQueryBuilder = "model/updateQueryBuilder.ftl"
    val modelDeleteQueryBuilder = "model/deleteQueryBuilder.ftl"
    val modelPropertiesChangeTracker = "model/propertiesChangeTracker.ftl"
    val modelRecord = "model/modelRecord.ftl"
    val modelValidationManager = "model/validationManager.ftl"
    val modelValidatorTrait = "model/validatorTrait.ftl"
    val modelToJsonSerializer = "model/toJsonSerializer.ftl"
}