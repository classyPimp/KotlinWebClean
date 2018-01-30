package controllers.documenttemplatecategories

import composers.documenttemplatecategories.DocumentTemplateCategoriesComposers
import controllers.BaseController
import controllers.documenttemplatecategories.inputfeeds.DocumentTemplateCategoriesInputFeedsController
import models.documenttemplatecategory.DocumentTemplateCategory
import models.documenttemplatecategory.daos.DocumentTemplateCategoryDaos
import models.documenttemplatecategory.tojsonserializers.DocumentTemplateCategorySerializers
import org.jooq.generated.tables.DocumentTemplateCategories
import router.src.ServletRequestContext
import javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR
import javax.servlet.http.HttpServletResponse.SC_NOT_FOUND

class DocumentTemplateCategoriesController(context: ServletRequestContext) : BaseController(context) {

    companion object {
        fun inputFeeds(context: ServletRequestContext): DocumentTemplateCategoriesInputFeedsController {
            return DocumentTemplateCategoriesInputFeedsController(context)
        }
    }

    fun create() {
        val params = requestParams()
        val composer = DocumentTemplateCategoriesComposers.create(params)

        composer.onError = {
            renderJson(
                    DocumentTemplateCategorySerializers.create.onError(it)
            )
        }

        composer.onSuccess = {
            renderJson(
                    DocumentTemplateCategorySerializers.create.onSuccess(it)
            )
        }

        composer.run()
    }

    fun edit() {
        val id = context.routeParameters.get("id")?.toLongOrNull()

        if (id == null) {
            sendError(SC_INTERNAL_SERVER_ERROR)
            return
        }

        val category = DocumentTemplateCategoryDaos.show.byIdForEdit(id)

        if (category == null) {
            sendError(SC_NOT_FOUND)
            return
        }

        renderJson(
                DocumentTemplateCategorySerializers.edit.onSuccess(category)
        )
    }

    fun show() {
        val id = context.routeParameters.get("id")?.toLongOrNull()

        if (id == null) {
            sendError(SC_INTERNAL_SERVER_ERROR)
            return
        }

        val category = DocumentTemplateCategoryDaos.show.byIdForShow(id)

        if (category == null) {
            sendError(SC_NOT_FOUND)
            return
        }

        renderJson(
                DocumentTemplateCategorySerializers.show.onSuccess(category)
        )
    }

    fun index() {
        val documentTemplateCategories = DocumentTemplateCategoryDaos.index.forIndex()

        renderJson(
                DocumentTemplateCategorySerializers.index.onSuccess(documentTemplateCategories)
        )
    }

    fun update() {
        val params = requestParams()
        val id = context.routeParameters.get("id")?.toLongOrNull()

        val composer = DocumentTemplateCategoriesComposers.update(params, id)

        composer.onError = {
            renderJson(
                    DocumentTemplateCategorySerializers.update.onError(it)
            )
        }

        composer.onSuccess = {
            renderJson(
                    DocumentTemplateCategorySerializers.update.onSuccess(it)
            )
        }

        composer.run()
    }

    fun destroy() {
        val id = context.routeParameters.get("id")?.toLongOrNull()

        val composer = DocumentTemplateCategoriesComposers.destroy(id)

        composer.onError = {
            renderJson(
                    DocumentTemplateCategorySerializers.destroy.onError(it)
            )
        }

        composer.onSuccess = {
            renderJson(
                    DocumentTemplateCategorySerializers.destroy.onSuccess(it)
            )
        }

        composer.run()
    }

}