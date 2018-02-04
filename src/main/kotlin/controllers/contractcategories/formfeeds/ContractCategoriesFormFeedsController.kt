package controllers.contractcategories.formfeeds

import controllers.BaseController
import models.contractcategory.daos.ContractCategoryDaos
import models.contractcategory.tojsonserializers.ContractCategorySerializers
import org.jooq.generated.tables.ContractCategories
import router.src.ServletRequestContext

class ContractCategoriesFormFeedsController(context: ServletRequestContext) : BaseController(context) {
    fun index() {
        val contractCategories = ContractCategoryDaos.index.forFormFeedsIndex()

        renderJson(
                ContractCategorySerializers.FormFeeds.index.onSuccess(contractCategories)
        )
    }


}