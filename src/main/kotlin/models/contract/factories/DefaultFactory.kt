package models.contract.factories

import models.contract.Contract
import models.contract.ContractRequestParametersWrapper
import models.contractnumber.factories.ContractNumberFactories
import models.contractstatus.factories.ContractStatusFactories
import models.contracttocounterpartylink.ContractToCounterPartyLink
import models.contracttocounterpartylink.factories.ContractToCounterPartyLinkFactories

object DefaultFactory {
    fun create(wrappedParams: ContractRequestParametersWrapper): Contract {
        return Contract().also {

            it.contractCategoryId = wrappedParams.contractCategoryId
            it.description = wrappedParams.description

            wrappedParams.contractToCounterPartyLinks?.let {params ->
                it.contractToCounterPartyLinks = params.mapTo(mutableListOf<ContractToCounterPartyLink>()) {
                    ContractToCounterPartyLinkFactories.default.create(it)
                }
            }

            it.contractStatus = ContractStatusFactories.default.createForContractCreate()

            it.contractNumber = ContractNumberFactories.default.ofContractCreate()
        }
    }

}