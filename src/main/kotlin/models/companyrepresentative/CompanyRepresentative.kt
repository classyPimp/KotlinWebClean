package models.companyrepresentative

import models.company.Company
import models.powerdocument.PowerDocument
import org.jooq.generated.tables.CompanyRepresentatives
import orm.annotations.*
import orm.companyrepresentativegeneratedrepository.CompanyRepresentativeRecord
import java.sql.Timestamp

@IsModel(jooqTable = CompanyRepresentatives::class)
class CompanyRepresentative {

    val record: CompanyRepresentativeRecord by lazy { CompanyRepresentativeRecord(this) }

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "NAME")
    var name: String? = null

    @TableField(name = "BIN")
    var bin: String? = null

    @TableField(name = "IDENTIFYING_DOCUMENT")
    var identifyingDocument: String? = null

    @TableField(name = "COMPANY_ID")
    var companyId: Long? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    @BelongsTo(model= Company::class, fieldOnThis = "COMPANY_ID", fieldOnThat = "ID")
    var company: Company? = null

    @HasMany(model = PowerDocument::class, fieldOnThis = "ID", fieldOnThat = "COMPANY_REPRESENTATIVE_ID")
    var powerDocuments: MutableList<PowerDocument>? = null

}

