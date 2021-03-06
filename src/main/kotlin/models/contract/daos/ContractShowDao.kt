package models.contract.daos

import models.contract.Contract
import models.contracttocounterpartylink.ContractToCounterPartyLink
import org.jooq.generated.tables.Contracts.CONTRACTS
import orm.contractgeneratedrepository.ContractRecord
import orm.contracttocounterpartylinkgeneratedrepository.ContractToCounterPartyLinkAssociationsPreloader
import orm.contracttocounterpartylinkgeneratedrepository.ContractToCounterPartyLinkDefaultAssociationsManager

object ContractShowDao {

    val table = CONTRACTS

    fun preloadRequiredForValidationOnCreate(contract: Contract) {
        contract.record.also {
            it.loadContractCategory()
            it.contractToCounterPartyLinks?.let {
                ContractToCounterPartyLinkDefaultAssociationsManager.loadCounterParty(it) {
                    it.preload {
                        it.incorporationForm()
                    }
                }
            }
        }
    }

    fun forUpdateGeneralInfo(contractId: Long): Contract? {
        return ContractRecord.GET()
                .where(table.ID.eq(contractId))
                .preload {
                    it.contractCategory()
                    it.contractStatus()
                }
                .limit(1)
                .execute()
                .firstOrNull()
    }

    fun forShow(id: Long): Contract? {
        return ContractRecord.GET()
                .where(table.ID.eq(id))
                .limit(1)
                .preload {
                    it.contractCategory()
                    it.contractToCounterPartyLinks() {
                        it.preload {
                            it.counterParty() {
                                it.preload {
                                    it.incorporationForm()
                                }
                            }
                        }
                    }
                    it.contractStatus()
                }
                .execute()
                .firstOrNull()
    }

    fun forEditGeneralInfo(contractId: Long): Contract? {
        return ContractRecord.GET()
                .where(
                        table.ID.eq(contractId)
                )
                .preload {
                    it.contractCategory()
                    it.contractStatus()
                }
                .limit(1)
                .execute()
                .firstOrNull()
    }

    fun generalInfo(contractId: Long): Contract? {
        return ContractRecord.GET()
                .where(table.ID.eq(contractId))
                .limit(1)
                .preload {
                    it.contractCategory()
                    it.contractToCounterPartyLinks() {
                        it.preload {
                            it.counterParty() {
                                it.preload {
                                    it.incorporationForm()
                                }
                            }
                        }
                    }
                    it.contractStatus()
                }
                .execute()
                .firstOrNull()
    }

    fun forManageEdit(id: Long): Contract? {
        val contract = ContractRecord.GET()
                .where(
                        table.ID.eq(id)
                )
                .preload {
                    it.contractCategory()
                    it.monetaryObligations() {
                        it.preload {
                            it.monetaryObligationParts()
                        }
                    }
                    it.contractToCounterPartyLinks() {
                        it.preload {
                            it.counterParty() {
                                it.preload {
                                    it.incorporationForm()
                                }
                            }
                        }
                    }
                    it.contractToUploadedDocumentLinks() {
                        it.preload {
                            it.uploadedDocument()
                            it.contractToUploadedDocumentLinkReason()
                        }
                    }
                    it.contractStatus()
                }
                .limit(1)
                .execute()
                .firstOrNull()

        return contract
    }

    fun forContractToCounterPartyLinkCreate(id: Long): Contract? {
        return ContractRecord.GET()
                .preload {
                    it.contractToCounterPartyLinks()
                }
                .where(table.ID.eq(id))
                .limit(1)
                .execute()
                .firstOrNull()
    }

    fun forContractToUploadedDocumentLinkCreate(contractId: Long): Contract? {
        return ContractRecord.GET()
                .where(table.ID.eq(contractId))
                .limit(1)
                .execute()
                .firstOrNull()
    }

    fun exists(contractId: Long): Boolean {
        val contract = ContractRecord.GET()
                .where(table.ID.eq(contractId))
                .limit(1)
                .execute()
                .firstOrNull()

        return (contract != null)
    }

    fun byIdPreloadingContractStatus(contractId: Long): Contract? {
        return ContractRecord.GET()
                .where(table.ID.eq(contractId))
                .preload {
                    it.contractStatus()
                }
                .limit(1)
                .execute()
                .firstOrNull()
    }

    fun byIdForMarkingAsApproved(contractId: Long): Contract? {
        return ContractRecord.GET()
                .where(table.ID.eq(contractId))
                .preload {
                    it.contractStatus()
                }
                .limit(1)
                .execute()
                .firstOrNull()
    }
}