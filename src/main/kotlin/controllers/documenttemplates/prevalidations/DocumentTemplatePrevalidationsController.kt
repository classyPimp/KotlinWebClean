package controllers.documenttemplates.prevalidations

import composers.documenttemplates.DocumentTemplateComposers
import controllers.BaseController
import models.documenttemplate.DocumentTemplate
import models.documenttemplate.tojsonserializers.DocumentTemplateSerializers
import router.src.ServletRequestContext

/**
 * Created by Муса on 23.01.2018.
 */

class DocumentTemplatePrevalidationsController(context: ServletRequestContext) : BaseController(context) {

    fun create() {
        val params = requestParams()

        val composer = DocumentTemplateComposers.Prevalidations.create(params)

        composer.onError = {
            renderJson(
                    DocumentTemplateSerializers.Prevalidations.create.onError(it)
            )
        }

        composer.onSuccess = {
            renderJson(
                    DocumentTemplateSerializers.Prevalidations.create.onSuccess(it)
            )
        }

        composer.run()

    }

}
