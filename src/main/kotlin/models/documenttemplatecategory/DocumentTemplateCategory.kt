package models.documenttemplatecategory

import models.documenttemplate.DocumentTemplate
import org.jooq.generated.tables.DocumentTemplateCategories
import orm.annotations.*
import orm.documenttemplatecategorygeneratedrepository.DocumentTemplateCategoryRecord
import java.sql.Timestamp

@IsModel(jooqTable = DocumentTemplateCategories::class)
class DocumentTemplateCategory {

    val record: DocumentTemplateCategoryRecord by lazy { DocumentTemplateCategoryRecord(this) }

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "NAME")
    var name: String? = null

    @TableField(name = "DESCRIPTION")
    var description: String? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    @HasMany(model = DocumentTemplate::class, fieldOnThis = "ID", fieldOnThat = "DOCUMENT_TEMPLATE_CATEGORY_ID")
    var documentTemplates: MutableList<DocumentTemplate>? = null

}

