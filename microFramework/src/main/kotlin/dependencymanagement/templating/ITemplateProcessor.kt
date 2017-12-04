package dependencymanagement.templating

import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.io.Writer

/**
 * Created by classypimp on 11/1/17.
 */
interface ITemplateProcessor {

    fun process(templateName: String, dataModel: Any, writer: Writer)

}