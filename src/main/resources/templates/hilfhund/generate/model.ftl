package models.${classNameLowerCase}

import org.jooq.generated.tables.${pluralClassName}
import orm.annotations.*
import orm.${decapitalizedClassName}generatedrepository.${className}Record
import java.sql.Timestamp
<#list associatedModels as associated>
import org.jooq.generated.tables.${associated.className}s
import models.${associated.lowerCaseClassName}.${associated.className}
</#list>

@IsModel(jooqTable = ${pluralClassName})
class ${className} {

    val record: ${className}Record by lazy { ${className}Record(this) }

    <#list tableFields as tableField>
        @TableField(name = "${tableField.name}")
        <#if tableField.isPrimaryKey()>
        @IsPrimaryKey
        </#if>
        var ${tableField.name}: ${tableField.type}? = null
    </#list>

    <#list associatedModels as associated>
    @${associated.associationType}(model = ${associated.className}::class, fieldOnThis = "${associated.fieldOnThis}", fieldOnThat = "${associated.fieldOnThat}")
    var ${associated.property}: ${associated.className}? = null
    </#list>

}

