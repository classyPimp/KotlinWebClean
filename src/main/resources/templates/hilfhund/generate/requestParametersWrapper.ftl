package models.${classNameLowerCase}

import models.${classNameLowerCase}.${className}
import utils.requestparameters.IParam

import java.sql.Timestamp

<#list associatedTypesToImport as associated>
import org.jooq.generated.tables.${associated.pluralClassName}
import models.${associated.lowerCaseClassName}.${associated.className}
import models.${associated.lowerCaseClassName}.${associated.className}RequestParametersWrapper
</#list>

class ${className}RequestParametersWrapper(val requestParameters: IParam) {

    val id: Long? by lazy { requestParameters.get("id")?.long }
    val createdAt: Timestamp? by lazy { requestParameters.get("createdAt")?.timestamp }
    val updatedAt: Timestamp? by lazy { requestParameters.get("updatedAt")?.timestamp }
    <#list tableFields as tableField>
    val ${tableField.name}: ${tableField.type}? by lazy { requestParameters.get("${tableField.name}")?.${tableField.decapitalizedFieldType} }
    </#list>
    <#list associatedModels as associated>
    <#if associated.associationType == "HasMany" || associated.associationType == "HasManyAsPolymorphic">
    val ${associated.property}: MutableList<${associated.className}RequestParametersWrapper>? by lazy {
    requestParameters.get("${associated.property}")?.paramList()?.let {
        it.mapTo(mutableListOf<${associated.className}RequestParametersWrapper>()) {
            ${associated.className}RequestParametersWrapper(it)
        }
    }
    }
    <#elseif associated.associationType == "HasOne" || associated.associationType == "HasOneAsPolymorphic">
    val ${associated.property}: ${associated.className}RequestParametersWrapper? by lazy {
        requestParameters.get("${associated.property}")?.let {
            ${associated.className}RequestParametersWrapper(it)
        }
    }
    <#elseif associated.associationType == "BelongsTo" || associated.associationType == "BelongsToPolymorphic">
    val ${associated.property}: ${associated.className}RequestParametersWrapper? by lazy {
        requestParameters.get("${associated.property}")?.let {
            ${associated.className}RequestParametersWrapper(it)
        }
    }
    </#if>
    </#list>


}