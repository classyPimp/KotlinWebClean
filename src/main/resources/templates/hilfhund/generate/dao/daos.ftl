package models.${classNameLowerCase}.daos

import org.jooq.generated.tables.${pluralClassName}
import models.${classNameLowerCase}.daos.${className}ShowDao
import models.${classNameLowerCase}.daos.${className}IndexDao
import models.${classNameLowerCase}.daos.${className}EditDao
import models.${classNameLowerCase}.daos.${className}UpdateDao
import models.${classNameLowerCase}.daos.${className}DestroyDao

object ${className}Daos {

    val show = ${className}ShowDao

    val index = ${className}IndexDao

    val edit = ${className}EditDao

    val update = ${className}UpdateDao

    val destroy = ${className}DestroyDao

}