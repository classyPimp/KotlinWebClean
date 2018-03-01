package models.monetaryobligation.tojsonserializers

import models.monetaryobligation.MonetaryObligation
import orm.monetaryobligationgeneratedrepository.MonetaryObligationToJsonSerializer

/**
 * Created by Муса on 27.02.2018.
 */
object ForContractManageMonetaryObligationIndexSerializer {

    fun onSuccess(monetaryObligations: MutableList<MonetaryObligation>): String {
        return MonetaryObligationToJsonSerializer.serialize(monetaryObligations) {
            it.includeMonetaryObligationParts()
        }.toString()
    }

}