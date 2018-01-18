package models.documenttemplatevariable.daos

import org.jooq.generated.tables.DocumentTemplateVariables
import models.documenttemplatevariable.daos.DocumentTemplateVariableShowDao
import models.documenttemplatevariable.daos.DocumentTemplateVariableIndexDao
import models.documenttemplatevariable.daos.DocumentTemplateVariableEditDao
import models.documenttemplatevariable.daos.DocumentTemplateVariableUpdateDao
import models.documenttemplatevariable.daos.DocumentTemplateVariableDestroyDao

object DocumentTemplateVariableDaos {

    val show = DocumentTemplateVariableShowDao

    val index = DocumentTemplateVariableIndexDao

    val edit = DocumentTemplateVariableEditDao

    val update = DocumentTemplateVariableUpdateDao

    val destroy = DocumentTemplateVariableDestroyDao

}