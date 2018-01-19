package models.documenttemplatevariable.daos

import org.jooq.generated.tables.DocumentTemplateVariables
import orm.documenttemplatevariablegeneratedrepository.DocumentTemplateVariableRecord
import models.documenttemplatevariable.DocumentTemplateVariable

object DocumentTemplateVariableIndexDao {
    fun forIndex(): MutableList<DocumentTemplateVariable> {
        return DocumentTemplateVariableRecord.GET()
                .execute()
    }


}