package models.incorporationform.factories

import models.incorporationform.IncorporationForm
import models.incorporationform.IncorporationFormRequestParametersWrapper
import utils.stdlibextensions.string.trimAndSquishWhiteSpace

object Create {

    fun create(params: IncorporationFormRequestParametersWrapper): IncorporationForm {
        return IncorporationForm().also {
            it.name = params.name?.trimAndSquishWhiteSpace()
            it.nameShort = params.nameShort?.trimAndSquishWhiteSpace()
        }
    }

}