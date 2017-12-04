package orm.fileGeneration

import freemarker.template.Template
import orm.configs.templatingEngine.TemplateFilePathsConfig
import orm.configs.templatingEngine.TemplatingEngineConfig

/**
 * Created by classypimp on 9/8/17.
 */
object TemplateFactory {

    fun createTemplate(templateName: String): Template {
        try {
            return TemplatingEngineConfig.templateEngineConfiguration.getTemplate(
                    templateName
            )
        } catch (error: Exception) {
            throw error
        }
    }

}