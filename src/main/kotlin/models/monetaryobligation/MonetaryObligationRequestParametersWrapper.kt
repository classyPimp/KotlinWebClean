package models.monetaryobligation

import models.monetaryobligation.MonetaryObligation
import models.monetaryobligationpart.MonetaryObligationPartRequestParametersWrapper
import utils.requestparameters.IParam

class MonetaryObligationRequestParametersWrapper(val requestParameters: IParam) {

    val totalAmount: Long? by lazy {
        requestParameters.get("totalAmount")?.long()
    }
    val description: String? by lazy { requestParameters.get("description")?.string }

    val isCreadit by lazy { requestParameters.get("isCreadit")?.boolean }

    val contractId by lazy { requestParameters.get("contractId")?.long() }

    val monetaryObligationParts: MutableList<MonetaryObligationPartRequestParametersWrapper>? by lazy {
        requestParameters.get("monetaryObligationParts")?.paramList()?.mapTo(
                mutableListOf<MonetaryObligationPartRequestParametersWrapper>()
        ) {
            MonetaryObligationPartRequestParametersWrapper(it)
        }
    }

}