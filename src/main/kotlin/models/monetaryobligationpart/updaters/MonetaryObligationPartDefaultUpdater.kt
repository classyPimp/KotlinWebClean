package models.monetaryobligationpart.updaters

import models.monetaryobligationpart.MonetaryObligationPart
import models.monetaryobligationpart.MonetaryObligationPartRequestParametersWrapper


object MonetaryObligationPartDefaultUpdater {

    fun update(model: MonetaryObligationPart, params: MonetaryObligationPartRequestParametersWrapper) {
        model.record.also {
            record ->
            record.amount = params.amount
            record.dueDate = params.dueDate
            params.markedForDestruction?.let {
                model.markedForDestruction = it
            }
        }
    }

}