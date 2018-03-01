package models.monetaryobligation.updaters

import models.monetaryobligation.MonetaryObligation
import models.monetaryobligation.MonetaryObligationRequestParametersWrapper


object DefaultUpdater {

    fun update(model: MonetaryObligation, params: MonetaryObligationRequestParametersWrapper) {
        model.record.also {
            record ->
            record.description = params.description
            record.totalAmount = params.totalAmount
        }
    }

}