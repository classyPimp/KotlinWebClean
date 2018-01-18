package models.documenttemplatetodocumentvariablelink

import models.documenttemplate.DocumentTemplate
import models.documenttemplatevariable.DocumentTemplateVariable
import org.jooq.generated.tables.DocumentTemplateToDocumentVariableLinks
import orm.annotations.*
import orm.documenttemplatetodocumentvariablelinkgeneratedrepository.DocumentTemplateToDocumentVariableLinkRecord
import java.sql.Timestamp

@IsModel(jooqTable = DocumentTemplateToDocumentVariableLinks::class)
class DocumentTemplateToDocumentVariableLink {

    val record: DocumentTemplateToDocumentVariableLinkRecord by lazy { DocumentTemplateToDocumentVariableLinkRecord(this) }

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "DOCUMENT_TEMPLATE_ID")
    var documentTemplateId: Long? = null

    @TableField(name = "DOCUMENT_TEMPLATE_VARIABLE_ID")
    var documentTemplateVariableId: Long? = null

    @TableField(name = "UNIQUE_IDENTIFIER")
    var uniqueIdentifier: String? = null

    @TableField(name = "DEFAULT_VALUE")
    var defaultValue: String? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    @BelongsTo(model = DocumentTemplate::class, fieldOnThis = "DOCUMENT_TEMPLATE_ID", fieldOnThat = "ID")
    var documentTemplate: DocumentTemplate? = null

    @BelongsTo(model = DocumentTemplateVariable::class, fieldOnThis = "DOCUMENT_TEMPLATE_VARIABLE_ID", fieldOnThat = "ID")
    var documentTemplateVariable: DocumentTemplateVariable? = null

}

