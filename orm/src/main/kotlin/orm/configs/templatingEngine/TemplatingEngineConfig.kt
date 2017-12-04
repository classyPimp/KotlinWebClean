package orm.configs.templatingEngine

import freemarker.template.Configuration
import freemarker.template.TemplateExceptionHandler
import java.io.File

/**
 * Created by classypimp on 9/6/17.
 */
object TemplatingEngineConfig : Runnable {

    var hasRun : Boolean = false
    lateinit var templateEngineConfiguration: Configuration

    fun runIfNotRunAlready() {
        if (!hasRun) {
            run()
        }
    }

    @Throws override fun run() {
        throwDuplicateRunErrorIf(hasRun)
        markSelfAsRun()

        this.templateEngineConfiguration = initializeConfigurationObject()
        setConfigProperties()
    }

    private fun setConfigProperties() {
        templateEngineConfiguration.apply {
            setDirectoryForTemplateLoading(
                    File("orm/src/main/resources/templates")
            )
            setDefaultEncoding("UTF-8")
            setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER)
            setLogTemplateExceptions(false)
        }
    }

    private fun initializeConfigurationObject(): Configuration {
        return Configuration(Configuration.VERSION_2_3_26)
    }

    private fun markSelfAsRun() {
        this.hasRun = true
    }

    private fun throwDuplicateRunErrorIf(hasRun: Boolean) {
        if (hasRun) {
            throw Throwable("TemplatingEngineConfig.run() called multiple times")
        }
    }



}