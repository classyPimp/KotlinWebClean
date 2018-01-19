package models.documenttemplatevariable.daos

import org.jooq.generated.tables.DocumentTemplateVariables.DOCUMENT_TEMPLATE_VARIABLES
import orm.documenttemplatevariablegeneratedrepository.DocumentTemplateVariableRecord
import models.documenttemplatevariable.DocumentTemplateVariable

object DocumentTemplateVariableShowDao {

    val table = DOCUMENT_TEMPLATE_VARIABLES

    fun forDestroyById(id: Long): DocumentTemplateVariable? {
        return DocumentTemplateVariableRecord.GET()
                .where(
                    table.ID.eq(id)
                )
                .limit(1)
                .execute()
                .firstOrNull()
    }

    fun forUpdate(id: Long): DocumentTemplateVariable? {
        return DocumentTemplateVariableRecord.GET()
                .where(
                        table.ID.eq(id)
                )
                .limit(1)
                .execute()
                .firstOrNull()
    }

    fun forShow(id: Long): DocumentTemplateVariable? {
        return DocumentTemplateVariableRecord.GET()
                .where(
                        table.ID.eq(id)
                )
                .limit(1)
                .execute()
                .firstOrNull()
    }

    fun forEdit(id: Long): DocumentTemplateVariable? {
        return forShow(id)
    }

}