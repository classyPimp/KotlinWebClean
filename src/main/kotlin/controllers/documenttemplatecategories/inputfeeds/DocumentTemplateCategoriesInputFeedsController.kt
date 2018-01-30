package controllers.documenttemplatecategories.inputfeeds

import controllers.BaseController
import models.documenttemplatecategory.DocumentTemplateCategory
import models.documenttemplatecategory.daos.DocumentTemplateCategoryDaos
import models.documenttemplatecategory.tojsonserializers.DocumentTemplateCategorySerializers
import org.jooq.generated.tables.DocumentTemplateCategories
import router.src.ServletRequestContext

/**
 * Created by Муса on 30.01.2018.
 */
class DocumentTemplateCategoriesInputFeedsController(context: ServletRequestContext) : BaseController(context) {

    fun index() {
        val documentTemplateCategories = DocumentTemplateCategoryDaos.index.forInputFeed()

        renderJson(
                DocumentTemplateCategorySerializers.index.onSuccess(documentTemplateCategories)
        )
    }

}
