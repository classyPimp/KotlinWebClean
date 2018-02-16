package models.contract.tojsonserializers.manage

import models.contract.Contract
import orm.contractgeneratedrepository.ContractToJsonSerializer

/**
 * Created by Муса on 07.02.2018.
 */
object ManageEditSerializer {

    fun onSuccess(contract: Contract): String {
        ContractToJsonSerializer(contract).let {
            it.includeContractCategory()
            it.includeContractToCounterPartyLinks() {
                it.includeCounterParty() {
                    it.includeIncorporationForm()
                }
            }
            it.includeContractToUploadedDocumentLinks() {
                it.includeUploadedDocument()
                it.includeContractToUploadedDocumentLinkReason()
            }
            it.includeContractStatus()
            return it.serializeToString()
        }
    }

}