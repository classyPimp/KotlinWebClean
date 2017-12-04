package daggerComponents.modules

import dagger.Module
import dagger.Provides
import freemarker.template.Configuration
import dependencymanagement.templating.FreemarkerTemplateProcessor
import dependencymanagement.templating.ITemplateProcessor
import java.io.File
import javax.inject.Singleton

/**
 * Created by classypimp on 11/1/17.
 */

@Module
class TemplatingModule {

    @Singleton
    @Provides
    fun providesTemplateProcessor(configuration: Configuration): ITemplateProcessor {
        return FreemarkerTemplateProcessor(configuration)
    }

    @Provides
    fun providesFreemarkerConfiguration(): Configuration {
        val configuration = Configuration(Configuration.VERSION_2_3_26)
        configuration.apply {
            setDirectoryForTemplateLoading(
                    File("src/main/resources/templates")
            )
        }
        return configuration
    }

}