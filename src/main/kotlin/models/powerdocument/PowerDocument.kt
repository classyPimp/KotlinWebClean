package models.powerdocument

import models.companyrepresentative.CompanyRepresentative
import org.jooq.generated.tables.PowerDocuments
import orm.annotations.*
import orm.powerdocumentgeneratedrepository.PowerDocumentRecord
import java.sql.Timestamp

@IsModel(jooqTable = PowerDocuments::class)
class PowerDocument {

    val record: PowerDocumentRecord by lazy { PowerDocumentRecord(this) }

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "COMPANY_REPRESENTATIVE_ID")
    var companyRepresentativeId: Long? = null

    @TableField(name = "FILE_NAME")
    var fileName: String? = null

    @TableField(name = "FILE_MIME")
    var fielMime: String? = null

    @TableField(name = "FILE_SIZE")
    var fileSize: String? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    @BelongsTo(model = CompanyRepresentative::class, fieldOnThis = "COMPANY_REPRESENTATIVE_ID", fieldOnThat = "ID")
    var comapnyRepresentative: CompanyRepresentative? = null


}

