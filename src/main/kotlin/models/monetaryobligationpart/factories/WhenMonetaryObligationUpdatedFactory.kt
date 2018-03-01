package models.monetaryobligationpart.factories

import models.monetaryobligationpart.MonetaryObligationPart
import models.monetaryobligationpart.MonetaryObligationPartRequestParametersWrapper
import org.apache.commons.lang3.mutable.Mutable

object WhenMonetaryObligationUpdatedFactory {
    fun create(params: MonetaryObligationPartRequestParametersWrapper, monetaryObligationId: Long): MonetaryObligationPart {
        return MonetaryObligationPart().also {
            it.amount = params.amount
            it.dueDate = params.dueDate
            it.monetaryObligationId = monetaryObligationId
        }
    }

}