package models.${classNameLowerCase}

import org.jooq.generated.tables.${pluralClassName}
import orm.annotations.*
import orm.${classNameLowerCase}generatedrepository.${className}Record
import java.sql.Timestamp
<#list associatedTypesToImport as associated>
import org.jooq.generated.tables.${associated.pluralClassName}
import models.${associated.lowerCaseClassName}.${associated.className}
</#list>

@IsModel(jooqTable = ${pluralClassName}::class)
class ${className} {

    val record: ${className}Record by lazy { ${className}Record(this) }

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    <#list tableFields as tableField>
    @TableField(name = "${tableField.name}")
    var ${tableField.name}: ${tableField.type}? = null

    </#list>
    <#list associatedModels as associated>
    <#if associated.associationType == "HasMany" || associated.associationType == "HasManyAsPolymorphic">
    @${associated.associationType}(model = ${associated.className}::class, fieldOnThis = "${associated.fieldOnThis}", fieldOnThat = "${associated.fieldOnThat}")
    var ${associated.property}: MutableList<${associated.className}>? = null

    <#elseif associated.associationType == "HasOne" || associated.associationType == "HasOneAsPolymorphic">
    @${associated.associationType}(model = ${associated.className}::class, fieldOnThis = "${associated.fieldOnThis}", fieldOnThat = "${associated.fieldOnThat}")
    var ${associated.property}: ${associated.className}? = null

    <#elseif associated.associationType == "BelongsTo" || associated.associationType == "BelongsToPolymorphic">
    @${associated.associationType}(model = ${associated.className}::class, fieldOnThis = "${associated.fieldOnThis}", fieldOnThat = "${associated.fieldOnThat}")
    var ${associated.property}: ${associated.className}? = null

    </#if>
    </#list>

}

