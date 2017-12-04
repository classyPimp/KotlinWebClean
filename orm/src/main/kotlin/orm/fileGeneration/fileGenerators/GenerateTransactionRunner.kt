package orm.fileGeneration.fileGenerators

import freemarker.template.Template
import orm.fileGeneration.GeneratedFileFactory
import orm.fileGeneration.TemplateFactory
import orm.services.TemplateFileWriterService
import java.io.File

/**
 * Created by Муса on 02.11.2017.
 */
class GenerateTransactionRunner() {
    fun run(){
        val file: File = GeneratedFileFactory.createDefault(
                packageName = "orm.generated.utils",
                fileName = "TransactionRunner.kt"
        )

        val template: Template = TemplateFactory.createTemplate("transactionRunner.ftl")

        TemplateFileWriterService.writeTemplate(template, mutableMapOf<String,String>(), file)
    }
}