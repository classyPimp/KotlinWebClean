package models.documenttemplatecategory.daos

import org.jooq.generated.tables.DocumentTemplateCategories
import orm.documenttemplatecategorygeneratedrepository.DocumentTemplateCategoryRecord
import models.documenttemplatecategory.DocumentTemplateCategory

object DocumentTemplateCategoryIndexDao {
    fun forInputFeed(): MutableList<DocumentTemplateCategory> {
        return DocumentTemplateCategoryRecord.GET()
                .execute()
    }

    fun forIndex(): MutableList<DocumentTemplateCategory> {
        return DocumentTemplateCategoryRecord.GET()
                .execute()
    }


}