package models.monetaryobligation.tojsonserializers

import models.monetaryobligation.MonetaryObligation
import orm.monetaryobligationgeneratedrepository.MonetaryObligationToJsonSerializer

object MonetaryObligationUpdateSerializer {

    fun onSuccess(monetaryObligation: MonetaryObligation): String {
        MonetaryObligationToJsonSerializer(monetaryObligation).let {
            it.includeMonetaryObligationParts()
            return it.serializeToString()
        }
    }

    fun onError(monetaryObligation: MonetaryObligation): String {
        MonetaryObligationToJsonSerializer(monetaryObligation). let {
            it.includeMonetaryObligationParts() {
                it.includeErrors()
                if (it.model.markedForDestruction != null) {
                    it.set("markedForDestruction", true)
                }
            }
            it.includeErrors()
            return it.serializeToString()
        }
    }

}