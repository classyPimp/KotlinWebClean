package models.incorporationform.tojsonserializers

import models.incorporationform.IncorporationForm
import orm.incorporationformgeneratedrepository.IncorporationFormToJsonSerializer

object Index {

    fun onSuccess(incorporationForms: MutableList<IncorporationForm>): String {
        return IncorporationFormToJsonSerializer.serialize(incorporationForms).toString()
    }

}