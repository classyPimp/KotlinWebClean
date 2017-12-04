package models.company

import models.incorporationform.IncorporationForm
import org.jooq.generated.tables.Companies
import orm.annotations.*
import orm.companygeneratedrepository.CompanyRecord
import java.sql.Timestamp

@IsModel(jooqTable = Companies::class)
class Company {

    val record: CompanyRecord by lazy { CompanyRecord(this) }

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "INCORPORATION_FORM_ID")
    var incorporationFormId: Long? = null

    @TableField(name="NAME")
    var name: String? = null

    @TableField(name="NAME_SHORT")
    var nameShort: String? = null

    @TableField(name="ADRESS_LEGAL")
    var adressLegal: String? = null

    @TableField(name="ADRESS_POSTAL")
    var adressPostal: String? = null

    @TableField(name="BIN")
    var bin: String? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    @BelongsTo(model = IncorporationForm::class, fieldOnThis = "INCORPORATION_FORM_ID", fieldOnThat = "ID")
    var incorporationForm: IncorporationForm? = null

}

