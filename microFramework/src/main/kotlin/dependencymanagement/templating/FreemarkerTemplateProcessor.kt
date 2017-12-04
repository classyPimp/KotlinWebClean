package dependencymanagement.templating

import dependencymanagement.templating.ITemplateProcessor
import freemarker.template.Configuration
import java.io.Writer
/**
 * Created by classypimp on 11/1/17.
 */
class FreemarkerTemplateProcessor(val configuration: Configuration) : ITemplateProcessor {

    override fun process(templateName: String, dataModel: Any, writer: Writer) {
        val template = configuration.getTemplate(templateName)
        template.process(dataModel, writer)
    }



}