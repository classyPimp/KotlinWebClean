package models.documenttemplate

import models.documenttemplatetodocumentvariablelink.DocumentTemplateToDocumentVariableLink
import models.uploadeddocument.UploadedDocument
import org.jooq.generated.tables.DocumentTemplates
import orm.annotations.*
import orm.documenttemplategeneratedrepository.DocumentTemplateRecord
import java.sql.Timestamp

@IsModel(jooqTable = DocumentTemplates::class)
class DocumentTemplate {

    val record: DocumentTemplateRecord by lazy { DocumentTemplateRecord(this) }

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "NAME")
    var name: String? = null

    @TableField(name = "DESCRIPTION")
    var description: String? = null

    @TableField(name = "UPLOADED_DOCUMENT_ID")
    var uploadedDocumentId: Long? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    @BelongsTo(model = UploadedDocument::class, fieldOnThis = "UPLOADED_DOCUMENT_ID", fieldOnThat = "ID")
    var uploadedDocument: UploadedDocument? = null

    @HasMany(model = DocumentTemplateToDocumentVariableLink::class, fieldOnThat = "DOCUMENT_TEMPLATE_ID", fieldOnThis = "ID")
    var documentTemplateToDocumentTemplateVariableLinks: MutableList<DocumentTemplateToDocumentVariableLink>? = null

}

