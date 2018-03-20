package models.contract

import models.approval.Approval
import models.contractcategory.ContractCategory
import models.contractstatus.ContractStatus
import models.contracttocounterpartylink.ContractToCounterPartyLink
import models.contracttouploadeddocumentlink.ContractToUploadedDocumentLink
import models.monetaryobligation.MonetaryObligation
import org.jooq.generated.tables.ContractToCounterPartyLinks
import org.jooq.generated.tables.Contracts
import orm.annotations.*
import orm.contractgeneratedrepository.ContractRecord
import java.sql.Timestamp

@IsModel(jooqTable = Contracts::class)
class Contract {
    val record: ContractRecord by lazy { ContractRecord(this) }

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "CONTRACT_STATUS_ID")
    var contractStatusId: Long? = null

    @TableField(name = "CONTRACT_NUMBER_ID")
    var contractNumberId: Long? = null

    @TableField(name = "FORMAL_DATE")
    var formalDate: Timestamp? = null

    @TableField(name = "CONTRACT_CATEGORY_ID")
    var contractCategoryId: Long? = null

    @TableField(name = "DESCRIPTION")
    var description: String? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    @HasMany(model = ContractToUploadedDocumentLink::class, fieldOnThat = "CONTRACT_ID", fieldOnThis = "ID")
    var contractToUploadedDocumentLinks: MutableList<ContractToUploadedDocumentLink>? = null

    @BelongsTo(model = ContractStatus::class, fieldOnThat = "ID", fieldOnThis = "CONTRACT_STATUS_ID")
    var contractStatus: ContractStatus? = null

    @BelongsTo(model = ContractCategory::class, fieldOnThat = "ID", fieldOnThis = "CONTRACT_CATEGORY_ID")
    var contractCategory: ContractCategory? = null

    @HasMany(model = ContractToCounterPartyLink::class, fieldOnThat = "CONTRACT_ID", fieldOnThis = "ID")
    var contractToCounterPartyLinks: MutableList<ContractToCounterPartyLink>? = null

    @HasMany(model = MonetaryObligation::class, fieldOnThat = "CONTRACT_ID", fieldOnThis = "ID")
    var monetaryObligations: MutableList<MonetaryObligation>? = null

    @HasOneAsPolymorphic(model = Approval::class, fieldOnThis = "ID", fieldOnThat = "APPROVABLE_ID", polymorphicTypeField = "APPROVABLE_TYPE")
    var approval: Approval? = null
}

