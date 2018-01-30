package models.documenttemplatecategory.daos

import org.jooq.generated.tables.DocumentTemplateCategories
import models.documenttemplatecategory.daos.DocumentTemplateCategoryShowDao
import models.documenttemplatecategory.daos.DocumentTemplateCategoryIndexDao
import models.documenttemplatecategory.daos.DocumentTemplateCategoryEditDao
import models.documenttemplatecategory.daos.DocumentTemplateCategoryUpdateDao
import models.documenttemplatecategory.daos.DocumentTemplateCategoryDestroyDao

object DocumentTemplateCategoryDaos {

    val show = DocumentTemplateCategoryShowDao

    val index = DocumentTemplateCategoryIndexDao

    val edit = DocumentTemplateCategoryEditDao

    val update = DocumentTemplateCategoryUpdateDao

    val destroy = DocumentTemplateCategoryDestroyDao

}