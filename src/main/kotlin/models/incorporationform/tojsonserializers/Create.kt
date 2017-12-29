package models.incorporationform.tojsonserializers

import models.incorporationform.IncorporationForm
import orm.incorporationformgeneratedrepository.IncorporationFormToJsonSerializer

object Create {

    fun onSuccess(incorporationForm: IncorporationForm): String {
        IncorporationFormToJsonSerializer(incorporationForm).let {

            return it.serializeToString()
        }
    }

    fun onError(incorporationForm: IncorporationForm): String {
        IncorporationFormToJsonSerializer(incorporationForm). let {


            it.includeErrors()
            return it.serializeToString()
        }
    }

}