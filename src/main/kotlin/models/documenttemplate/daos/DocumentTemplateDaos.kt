package models.documenttemplate.daos

import org.jooq.generated.tables.DocumentTemplates
import models.documenttemplate.daos.DocumentTemplateShowDao
import models.documenttemplate.daos.DocumentTemplateIndexDao
import models.documenttemplate.daos.DocumentTemplateEditDao
import models.documenttemplate.daos.DocumentTemplateUpdateDao
import models.documenttemplate.daos.DocumentTemplateDestroyDao

object DocumentTemplateDaos {

    val show = DocumentTemplateShowDao

    val index = DocumentTemplateIndexDao

    val edit = DocumentTemplateEditDao

    val update = DocumentTemplateUpdateDao

    val destroy = DocumentTemplateDestroyDao

}