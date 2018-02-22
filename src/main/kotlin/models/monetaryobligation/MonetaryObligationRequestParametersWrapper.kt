package models.monetaryobligation

import models.monetaryobligation.MonetaryObligation
import models.monetaryobligationpart.MonetaryObligationPartRequestParametersWrapper
import utils.requestparameters.IParam

class MonetaryObligationRequestParametersWrapper(val requestParameters: IParam) {

    val totalAmount: Long? by lazy {
        var amount: Long? = requestParameters.get("totalAmount")?.long()
        if (amount != null) {
            amount = amount * 100
        }
        amount
    }
    val isCreadit by lazy { requestParameters.get("isCreadit")?.boolean }
    val contractId by lazy { requestParameters.get("contractId")?.long() }
    val monetaryObligationParts: MutableList<MonetaryObligationPartRequestParametersWrapper>? by lazy {
        var monetaryObligationParts = mutableListOf<MonetaryObligationPartRequestParametersWrapper>()
        requestParameters.get("monetaryObligationParts")?.paramList()?.forEach {
            val monetaryObligationPart = MonetaryObligationPartRequestParametersWrapper(it)
            monetaryObligationParts.add(monetaryObligationPart)
        }
        monetaryObligationParts
    }


}