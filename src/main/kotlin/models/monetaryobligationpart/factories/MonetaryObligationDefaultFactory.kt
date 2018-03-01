package models.monetaryobligationpart.factories

import models.monetaryobligationpart.MonetaryObligationPart
import models.monetaryobligationpart.MonetaryObligationPartRequestParametersWrapper

/**
 * Created by Муса on 22.02.2018.
 */
object MonetaryObligationDefaultFactory {

    fun create(params: MonetaryObligationPartRequestParametersWrapper): MonetaryObligationPart {
        return MonetaryObligationPart().also {
            it.dueDate = params.dueDate
            it.amount = params.amount
        }
    }

}