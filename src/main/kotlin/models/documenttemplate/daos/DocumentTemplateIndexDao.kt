package models.documenttemplate.daos

import org.jooq.generated.tables.DocumentTemplates
import orm.documenttemplategeneratedrepository.DocumentTemplateRecord
import models.documenttemplate.DocumentTemplate
import models.person.daos.PersonIndexDao
import org.jooq.generated.Tables.DOCUMENT_TEMPLATES
import org.jooq.impl.DSL

object DocumentTemplateIndexDao {

    val table = DOCUMENT_TEMPLATES

    fun forIndexAction(): MutableList<DocumentTemplate> {
        return DocumentTemplateRecord.GET()
                .execute()
    }

    fun search(query: String?): MutableList<DocumentTemplate> {
        val queryBuilder = DocumentTemplateRecord.GET()

        if (query == null) {
            return queryBuilder.execute()
        }

        return queryBuilder.where(
                DSL.trueCondition().and("{0} ~* {1}", table.NAME, DSL.`val`(query))
        ).execute()
    }


}