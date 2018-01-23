package models.documenttemplate.daos

import org.jooq.generated.tables.DocumentTemplates
import orm.documenttemplategeneratedrepository.DocumentTemplateRecord
import models.documenttemplate.DocumentTemplate

object DocumentTemplateIndexDao {
    fun forIndexAction(): MutableList<DocumentTemplate> {
        return DocumentTemplateRecord.GET()
                .execute()
    }


}