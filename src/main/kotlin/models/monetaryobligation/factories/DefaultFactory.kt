package models.monetaryobligation.factories

import models.monetaryobligation.MonetaryObligation
import models.monetaryobligation.MonetaryObligationRequestParametersWrapper
import models.monetaryobligationpart.MonetaryObligationPart
import models.monetaryobligationpart.MonetaryObligationPartRequestParametersWrapper
import models.monetaryobligationpart.factories.MonetaryObligationPartFactories

object DefaultFactory {

    fun create(params: MonetaryObligationRequestParametersWrapper, contractId: Long): MonetaryObligation {
        return MonetaryObligation().also {
            it.contractId = contractId
            it.totalAmount = params.totalAmount
            it.isCredit = true
            it.monetaryObligationParts = params.monetaryObligationParts?.mapTo(mutableListOf<MonetaryObligationPart>()) {
                partParams: MonetaryObligationPartRequestParametersWrapper ->
                MonetaryObligationPartFactories.default.create(partParams)
            }
        }
    }

}