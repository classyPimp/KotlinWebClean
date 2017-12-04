package orm.services

import freemarker.template.Template
import orm.templateDataModels.model.ModelDataModel
import java.io.File

/**
 * Created by classypimp on 9/20/17.
 */
object TemplateFileWriterService {

    fun writeTemplate(
            template: Template,
            modelDataModel: ModelDataModel,
            file: File
    ){
        val writer = file.writer()
        template.process(modelDataModel, writer)
        writer.close()
    }

    fun writeTemplate(
            template: Template,
            dataModel: MutableMap<String,String>,
            file: File
    ) {
        val writer = file.writer()
        template.process(dataModel, writer)
        writer.close()
    }

}