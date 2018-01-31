package models.documenttemplate.daos

import org.jooq.generated.tables.DocumentTemplates.DOCUMENT_TEMPLATES
import orm.documenttemplategeneratedrepository.DocumentTemplateRecord
import models.documenttemplate.DocumentTemplate

object DocumentTemplateShowDao {

    val table = DOCUMENT_TEMPLATES

    fun forDestroy(id: Long): DocumentTemplate? {
        return DocumentTemplateRecord.GET()
                .where(table.ID.eq(id))
                .limit(1)
                .preload {
                    it.documentTemplateToDocumentVariableLinks() {
                        it.preload {
                            it.documentTemplateVariable()
                        }
                    }
                    it.uploadedDocument()
                }
                .execute()
                .firstOrNull()
    }

    fun forArbitraryCreate(id: Long): DocumentTemplate? {
        return DocumentTemplateRecord.GET()
                .preload {
                    it.uploadedDocument()
                    it.documentTemplateToDocumentVariableLinks() {
                        it.preload {
                            it.documentTemplateVariable()
                        }
                    }
                }
                .where(table.ID.eq(id))
                .limit(1)
                .execute()
                .firstOrNull()
    }

    fun forArbitraryShow(id: Long): DocumentTemplate? {
        return DocumentTemplateRecord.GET()
                .preload {
                    it.uploadedDocument()
                    it.documentTemplateToDocumentVariableLinks() {
                        it.preload {
                            it.documentTemplateVariable()
                        }
                    }
                    it.documentTemplateCategory()
                }
                .where(table.ID.eq(id))
                .limit(1)
                .execute()
                .firstOrNull()
    }

}