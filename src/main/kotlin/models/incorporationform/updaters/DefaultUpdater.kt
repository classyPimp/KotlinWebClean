package models.incorporationform.updaters

import models.incorporationform.IncorporationForm
import models.incorporationform.IncorporationFormRequestParametersWrapper
import utils.stdlibextensions.string.trimAndSquishWhiteSpace


object DefaultUpdater {

    fun update(model: IncorporationForm, params: IncorporationFormRequestParametersWrapper) {
        model.record.let {
            it.name = params.name?.trimAndSquishWhiteSpace()
            it.nameShort = params.name?.trimAndSquishWhiteSpace()
        }
    }

}