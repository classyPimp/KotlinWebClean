package composers.incorporationforms.formfeeds

import controllers.BaseController
import models.incorporationform.IncorporationForm
import models.incorporationform.daos.IncorporationFormDaos
import models.incorporationform.tojsonserializers.IncorporationFormSerializers
import orm.incorporationformgeneratedrepository.IncorporationFormRecord
import router.src.ServletRequestContext

class FormFeedsController(context: ServletRequestContext) : BaseController(context) {

    fun index() {
        val incorporationForms = IncorporationFormDaos.index.forFormFeed()

        renderJson(
                IncorporationFormSerializers.formFeeds.index.onSuccess(incorporationForms)
        )
    }

}