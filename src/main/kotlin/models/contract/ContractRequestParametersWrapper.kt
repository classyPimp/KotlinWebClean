package models.contract

import models.contract.Contract
import models.contractstatus.ContractStatusRequestParametersWrapper
import models.contracttocounterpartylink.ContractToCounterPartyLink
import models.contracttocounterpartylink.ContractToCounterPartyLinkRequestParametersWrapper
import utils.requestparameters.IParam

class ContractRequestParametersWrapper(val requestParameters: IParam) {

    val contractCategoryId: Long? by lazy { requestParameters.get("contractCategoryId")?.long() }
    val formalDate: String? by lazy { requestParameters.get("formalDate")?.string }
    val description: String? by lazy { requestParameters.get("description")?.string }
    val contractNumberId: Long? by lazy { requestParameters.get("contractNumberId")?.long() }
    val contractStatusId: Long? by lazy { requestParameters.get("contractStatusId")?.long() }
    val contractToCounterPartyLinks: MutableList<ContractToCounterPartyLinkRequestParametersWrapper>? by lazy {
        requestParameters.get("contractToCounterPartyLinks")?.paramList()?.let {
            it.mapTo(mutableListOf<ContractToCounterPartyLinkRequestParametersWrapper>()) {
                ContractToCounterPartyLinkRequestParametersWrapper(it)
            }
        }
    }
    val contractStatus: ContractStatusRequestParametersWrapper? by lazy {
        requestParameters.get("contractStatus")?.let {
            ContractStatusRequestParametersWrapper(it)
        }
    }

}