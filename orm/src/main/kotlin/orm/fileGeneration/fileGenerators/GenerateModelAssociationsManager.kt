package orm.fileGeneration.fileGenerators

import freemarker.template.Template
import orm.configs.templatingEngine.TemplateFilePathsConfig
import orm.fileGeneration.GeneratedFileFactory
import orm.fileGeneration.TemplateFactory
import orm.services.TemplateFileWriterService
import orm.templateDataModels.model.AssociationBeansFactory
import orm.templateDataModels.model.ModelDataModel
import orm.templateDataModels.model.ModelDataModelFactory
import java.io.File

/**
 * Created by classypimp on 9/22/17.
 */
class GenerateModelAssociationsManager(val modelDataModel: ModelDataModel) {

    fun run(){

        val file: File = GeneratedFileFactory.createDefault(
                packageName = modelDataModel.packagesBean.baseGenerated,
                fileName = "${modelDataModel.modelClass}DefaultAssociationManager.kt"
        )

        val templateName: String = TemplateFilePathsConfig.modelDefaultAssociationManager
        val template: Template = TemplateFactory.createTemplate(templateName)

        TemplateFileWriterService.writeTemplate(template, modelDataModel, file)
    }

}