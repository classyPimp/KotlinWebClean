package controllers.documenttemplatevariables

import composers.documenttemplatevariables.DocumentTemplateVariablesComposers
import controllers.BaseController
import models.documenttemplatevariable.DocumentTemplateVariable
import models.documenttemplatevariable.daos.DocumentTemplateVariableDaos
import models.documenttemplatevariable.tojsonserializers.DocumentTemplateVariableSerializers
import router.src.ServletRequestContext
import javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR

class DocumentTemplateVariablesController(context: ServletRequestContext) : BaseController(context) {

    fun create() {
        val params = requestParams()
        val composer = DocumentTemplateVariablesComposers.create(params)

        composer.onError = {
            renderJson(
                    DocumentTemplateVariableSerializers.create.onError(it)
            )
        }

        composer.onSuccess = {
            renderJson(
                    DocumentTemplateVariableSerializers.create.onSuccess(it)
            )
        }

        composer.run()

    }

    fun show() {
        val id = context.routeParameters.get("id")?.toLongOrNull()
        if (id == null) {
            sendError(SC_INTERNAL_SERVER_ERROR)
            return
        }

        val documentTemplateVariable = DocumentTemplateVariableDaos.show.forShow(id = id)

        if (documentTemplateVariable == null) {
            sendError(404)
            return
        }

        renderJson(
                DocumentTemplateVariableSerializers.show.onSuccess(documentTemplateVariable)
        )
    }

    fun index() {
        val documentTemplateVariables = DocumentTemplateVariableDaos.index.forIndex()

        renderJson(
                DocumentTemplateVariableSerializers.index.onSuccess(documentTemplateVariables)
        )
    }

    fun edit() {
        val id = context.routeParameters.get("id")?.toLongOrNull()
        if (id == null) {
            sendError(SC_INTERNAL_SERVER_ERROR)
            return
        }

        val documentTemplateVariable = DocumentTemplateVariableDaos.show.forEdit(id = id)

        if (documentTemplateVariable == null) {
            sendError(404)
            return
        }

        renderJson(
                DocumentTemplateVariableSerializers.edit.onSuccess(documentTemplateVariable)
        )
    }

    fun update() {
        val params = requestParams()
        val id = context.routeParameters.get("id")?.toLongOrNull()

        val composer = DocumentTemplateVariablesComposers.update(params, id)

        composer.onError = {
            renderJson(
                    DocumentTemplateVariableSerializers.update.onError(it)
            )
        }

        composer.onSuccess = {
            renderJson(
                    DocumentTemplateVariableSerializers.update.onSuccess(it)
            )
        }

        composer.run()
    }

    fun destroy() {
        val id = context.routeParameters.get("id")?.toLongOrNull()

        val composer = DocumentTemplateVariablesComposers.destroy(id)

        composer.onError = {
            renderJson(
                    DocumentTemplateVariableSerializers.destroy.onError(it)
            )
        }

        composer.onSuccess = {
            renderJson(
                    DocumentTemplateVariableSerializers.destroy.onSuccess(it)
            )
        }

        composer.run()
    }

}