package controllers.documenttemplates.arbitrary

import composers.documenttemplates.DocumentTemplateComposers
import controllers.BaseController
import models.documenttemplate.daos.DocumentTemplateDaos
import models.documenttemplate.tojsonserializers.DocumentTemplateSerializers
import router.src.ServletRequestContext
import java.io.FileInputStream
import javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR
import javax.servlet.http.HttpServletResponse.SC_NOT_FOUND

class DocumentTemplateArbitraryController(context: ServletRequestContext) : BaseController(context) {

    fun create() {

        val params = requestParams()
        val composer = DocumentTemplateComposers.Arbitrary.create(params)

        composer.onError = {
            renderJson(
                    DocumentTemplateSerializers.arbitraryCreate.onError(it)
            )
        }

        composer.onSuccess = {file ->
            val outputStream = context.response.getOutputStream()
            val inputStream = FileInputStream(file)
            try {
                context.response.setHeader("Content-Disposition", "attachment;filename=${file.name}")
                context.response.contentType = "blob"
                val buffer = ByteArray(4096)
                while (true) {
                    val length = inputStream.read(buffer)
                    if (length > 0) {
                        outputStream.write(buffer, 0, length)
                    } else {
                        break
                    }
                }
            } finally {
                inputStream.close()
                outputStream.flush()
            }
        }

        composer.run()
    }

    fun show() {
        val id = context.routeParameters.get("id")?.toLongOrNull()

        if (id == null) {
            sendError(SC_INTERNAL_SERVER_ERROR)
            return
        }

        val documentTemplate = DocumentTemplateDaos.show.forArbitraryShow(id)

        if (documentTemplate == null) {
            sendError(SC_NOT_FOUND)
            return
        }

        renderJson(
                DocumentTemplateSerializers.arbitraryShow.onSuccess(documentTemplate)
        )
    }

}