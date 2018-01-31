package controllers.documenttemplates

import composers.documenttemplates.DocumentTemplateComposers
import controllers.BaseController
import controllers.documenttemplates.arbitrary.DocumentTemplateArbitraryController
import controllers.documenttemplates.prevalidations.DocumentTemplatePrevalidationsController
import models.documenttemplate.daos.DocumentTemplateDaos
import models.documenttemplate.tojsonserializers.DocumentTemplateSerializers
import router.src.ServletRequestContext

class DocumentTemplatesController(context: ServletRequestContext) : BaseController(context) {

    companion object {
        fun prevalidations(context: ServletRequestContext): DocumentTemplatePrevalidationsController {
            return DocumentTemplatePrevalidationsController(context)
        }

        fun arbitrary(context: ServletRequestContext): DocumentTemplateArbitraryController {
            return DocumentTemplateArbitraryController(context)
        }
    }

    fun create() {
        val params = requestParams()

        val composer = DocumentTemplateComposers.create(params)

        composer.onError = {
            renderJson(
                    DocumentTemplateSerializers.create.onError(it)
            )
        }

        composer.onSuccess = {
            renderJson(
                    DocumentTemplateSerializers.create.onSuccess(it)
            )
        }

        composer.run()
    }

    fun index() {
        val documentTemplates = DocumentTemplateDaos.index.forIndexAction()

        renderJson(
                DocumentTemplateSerializers.index.onSuccess(documentTemplates)
        )
    }

    fun destroy() {
        val id = context.routeParameters.get("id")?.toLongOrNull()

        val composer = DocumentTemplateComposers.destroy(id)

        composer.onError = {
            renderJson(
                    DocumentTemplateSerializers.destroy.onError(it)
            )
        }

        composer.onSuccess = {
            renderJson(
                    DocumentTemplateSerializers.destroy.onSuccess(it)
            )
        }

        composer.run()
    }

}