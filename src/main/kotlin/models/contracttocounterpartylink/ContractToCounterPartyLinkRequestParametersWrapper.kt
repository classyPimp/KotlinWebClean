package models.contracttocounterpartylink

import models.contracttocounterpartylink.ContractToCounterPartyLink
import utils.requestparameters.IParam

class ContractToCounterPartyLinkRequestParametersWrapper(val requestParameters: IParam) {

    val counterPartyId: Long? by lazy { requestParameters.get("counterPartyId")?.long() }
    val contractId: Long? by lazy { requestParameters.get("contractId")?.long() }
    val roleAccordingToContract: String? by lazy { requestParameters.get("roleAccordingToContract")?.string }

}