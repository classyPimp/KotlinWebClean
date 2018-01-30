package models.documenttemplatecategory.daos

import models.documenttemplate.DocumentTemplate
import org.jooq.generated.tables.DocumentTemplateCategories.DOCUMENT_TEMPLATE_CATEGORIES
import orm.documenttemplatecategorygeneratedrepository.DocumentTemplateCategoryRecord
import models.documenttemplatecategory.DocumentTemplateCategory

object DocumentTemplateCategoryShowDao {

    val table = DOCUMENT_TEMPLATE_CATEGORIES

    fun forDestroy(id: Long): DocumentTemplateCategory? {
        return DocumentTemplateCategoryRecord.GET()
                .where(
                        table.ID.eq(id)
                )
                .limit(1)
                .execute()
                .firstOrNull()
    }

    fun forUpdate(id: Long): DocumentTemplateCategory? {
        return forDestroy(id)
    }

    fun byIdForShow(id: Long): DocumentTemplateCategory? {
        return DocumentTemplateCategoryRecord.GET()
                .where(
                        table.ID.eq(id)
                )
                .limit(1)
                .execute()
                .firstOrNull()
    }

    fun byIdForEdit(id: Long): DocumentTemplateCategory? {
        return byIdForShow(id)
    }

    fun byNameForUniquenessValidation(name: String): DocumentTemplateCategory? {
        return DocumentTemplateCategoryRecord.GET()
                .where(table.NAME.eq(name))
                .limit(1)
                .execute()
                .firstOrNull()
    }


}