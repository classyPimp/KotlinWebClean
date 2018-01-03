package models.incorporationform.tojsonserializers.formfeeds

import models.incorporationform.IncorporationForm
import orm.incorporationformgeneratedrepository.IncorporationFormToJsonSerializer

object FormFeedsIndex {

    fun onSuccess(incorporationForms: MutableList<IncorporationForm>): String {
        return IncorporationFormToJsonSerializer.serialize(incorporationForms).toString()
    }


}