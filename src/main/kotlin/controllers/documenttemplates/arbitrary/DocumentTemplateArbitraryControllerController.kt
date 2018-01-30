package controllers.documenttemplates.arbitrary

import composers.documenttemplates.DocumentTemplateComposers
import controllers.BaseController
import router.src.ServletRequestContext

class DocumentTemplateArbitraryControllerController(context: ServletRequestContext) : BaseController(context) {

    fun create() {

        val params = requestParams()
        val composer = DocumentTemplateComposers.Arbitrary.create(params)

        composer.onError = {

        }

        composer.onSuccess = {

        }
    }

}