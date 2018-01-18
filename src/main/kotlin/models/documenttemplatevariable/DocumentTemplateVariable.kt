package models.documenttemplatevariable

import models.documenttemplatetodocumentvariablelink.DocumentTemplateToDocumentVariableLink
import org.jooq.generated.tables.DocumentTemplateVariables
import orm.annotations.*
import orm.documenttemplatevariablegeneratedrepository.DocumentTemplateVariableRecord
import java.sql.Timestamp

@IsModel(jooqTable = DocumentTemplateVariables::class)
class DocumentTemplateVariable {

    val record: DocumentTemplateVariableRecord by lazy { DocumentTemplateVariableRecord(this) }

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "NAME")
    var name: String? = null

    @TableField(name = "HUMANIZED_NAME")
    var humanizedName: String? = null

    @TableField(name = "DESCRIPTION")
    var description: String? = null

    @TableField(name = "DEFAULT_VALUE")
    var defaultValue: String? = null

    @TableField(name = "IS_PREPROGRAMMED")
    var isPreprogrammed: Boolean? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    @HasMany(model = DocumentTemplateToDocumentVariableLink::class, fieldOnThis = "ID", fieldOnThat = "DOCUMENT_TEMPLATE_VARIABLE_ID")
    var documentTemplateToDocumentVariableLinks: MutableList<DocumentTemplateToDocumentVariableLink>? = null
}

