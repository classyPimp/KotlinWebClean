package models.contracttocounterpartylink

import models.contract.Contract
import models.counterparty.CounterParty
import org.jooq.generated.tables.ContractToCounterPartyLinks
import orm.annotations.*
import orm.contracttocounterpartylinkgeneratedrepository.ContractToCounterPartyLinkRecord
import java.sql.Timestamp

@IsModel(jooqTable = ContractToCounterPartyLinks::class)
class ContractToCounterPartyLink {

    val record: ContractToCounterPartyLinkRecord by lazy { ContractToCounterPartyLinkRecord(this) }

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "COUNTER_PARTY_ID")
    var counterPartyId: Long? = null

    @TableField(name = "CONTRACT_ID")
    var contractId: Long? = null

    @TableField(name = "ROLE_ACCORDING_TO_CONTRACT")
    var roleAccordingToContract: String? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    @BelongsTo(model = Contract::class, fieldOnThis = "CONTRACT_ID", fieldOnThat = "ID")
    var contract: Contract? = null

    @BelongsTo(model = CounterParty::class, fieldOnThis = "COUNTER_PARTY_ID", fieldOnThat = "ID")
    var counterParty: CounterParty? = null

}

