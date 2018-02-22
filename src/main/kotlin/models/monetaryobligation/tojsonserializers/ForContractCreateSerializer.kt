package models.monetaryobligation.tojsonserializers

import models.monetaryobligation.MonetaryObligation
import orm.monetaryobligationgeneratedrepository.MonetaryObligationToJsonSerializer

object ForContractCreateSerializer {

    fun onSuccess(monetaryObligation: MonetaryObligation): String {
        MonetaryObligationToJsonSerializer(monetaryObligation).let {

            return it.serializeToString()
        }
    }

    fun onError(monetaryObligation: MonetaryObligation): String {
        MonetaryObligationToJsonSerializer(monetaryObligation). let {


            it.includeErrors()
            return it.serializeToString()
        }
    }

}