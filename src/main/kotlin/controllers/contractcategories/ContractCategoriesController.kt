package controllers.contractcategories

import composers.contractcategories.ContractCategoryComposers
import controllers.BaseController
import controllers.contractcategories.formfeeds.ContractCategoriesFormFeedsController
import models.contractcategory.ContractCategory
import models.contractcategory.daos.ContractCategoryDaos
import models.contractcategory.tojsonserializers.ContractCategorySerializers
import router.src.ServletRequestContext
import javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR
import javax.servlet.http.HttpServletResponse.SC_NOT_FOUND

class ContractCategoriesController(context: ServletRequestContext) : BaseController(context) {

    companion object {
        fun formFeeds(context: ServletRequestContext): ContractCategoriesFormFeedsController {
            return ContractCategoriesFormFeedsController(context)
        }

    }

    fun create() {
        val params = requestParams()

        val composer = ContractCategoryComposers.create(params)

        composer.onError = {
            renderJson(
                    ContractCategorySerializers.create.onError(it)
            )
        }

        composer.onSuccess = {
            renderJson(
                    ContractCategorySerializers.create.onSuccess(it)
            )
        }

        composer.run()
    }

    fun index() {
        val contractCategories = ContractCategoryDaos.index.forIndex()

        renderJson(
                ContractCategorySerializers.index.onSuccess(contractCategories)
        )
    }

    fun show() {
        val id = context.routeParameters.get("id")?.toLongOrNull()

        if (id == null) {
            sendError(SC_INTERNAL_SERVER_ERROR)
            return
        }

        val contractCategory = ContractCategoryDaos.show.forShow(id = id)

        if (contractCategory == null) {
            sendError(SC_NOT_FOUND)
            return
        }

        renderJson(
                ContractCategorySerializers.show.onSuccess(contractCategory)
        )
    }

    fun update() {
        val params = requestParams()
        val id = context.routeParameters.get("id")?.toLongOrNull()

        val composer = ContractCategoryComposers.update(params, id)

        composer.onError = {
            renderJson(
                    ContractCategorySerializers.update.onError(it)
            )
        }

        composer.onSuccess = {
            renderJson(
                    ContractCategorySerializers.update.onSuccess(it)
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

        val contractCategory = ContractCategoryDaos.show.forEdit(id = id)

        if (contractCategory == null) {
            sendError(SC_NOT_FOUND)
            return
        }

        renderJson(
                ContractCategorySerializers.edit.onSuccess(contractCategory)
        )
    }

    fun destroy() {
        val id = context.routeParameters.get("id")?.toLongOrNull()

        val composer = ContractCategoryComposers.destroy(id)

        composer.onError = {
            renderJson(
                    ContractCategorySerializers.destroy.onError(it)
            )
        }

        composer.onSuccess = {
            renderJson(
                    ContractCategorySerializers.destroy.onSuccess(it)
            )
        }

        composer.run()
    }

}