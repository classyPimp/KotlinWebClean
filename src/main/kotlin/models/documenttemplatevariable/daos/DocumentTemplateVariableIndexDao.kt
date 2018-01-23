package models.documenttemplatevariable.daos

import org.jooq.generated.tables.DocumentTemplateVariables.DOCUMENT_TEMPLATE_VARIABLES
import orm.documenttemplatevariablegeneratedrepository.DocumentTemplateVariableRecord
import models.documenttemplatevariable.DocumentTemplateVariable

object DocumentTemplateVariableIndexDao {

    val table = DOCUMENT_TEMPLATE_VARIABLES

    fun forIndex(): MutableList<DocumentTemplateVariable> {
        return DocumentTemplateVariableRecord.GET()
                .execute()
    }

    fun forDocumentTemplatePrevalidationsCreate(namesByWhichToQueryDocumentTemplateVariables: MutableSet<String>): MutableList<DocumentTemplateVariable> {
        return DocumentTemplateVariableRecord.GET()
                .where(
                        table.NAME.`in`(namesByWhichToQueryDocumentTemplateVariables)
                )
                .execute()
    }


}