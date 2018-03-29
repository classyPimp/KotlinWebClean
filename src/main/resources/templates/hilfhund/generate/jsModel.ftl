import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { HasOne } from '../../modelLayer/annotations/HasOne'
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { ModelCollection } from '../../modelLayer/ModelCollection'
import { RequestOptions, Route } from '../../modelLayer/annotations/ModelRoute'

<#list associatedTypesToImport as associated>
import { ${associated.className} } from './${associated.className}'
</#list>

export class ${className} extends BaseModel {

    static className = "${decapitalizedClassName}"

    @Property
    id: number

    @Property
    updatedAt: string

    @Property
    createdAt: string

    <#list tableFields as tableField>
    @Property
    ${tableField.name}: ${tableField.jsType}
    </#list>

    <#list associatedModels as associated>
    <#if associated.associationType == "HasMany" || associated.associationType == "HasManyAsPolymorphic">
        @HasMany("${associated.className}")
        ${associated.property}: ModelCollection<${associated.className}>
    <#elseif associated.associationType == "HasOne" || associated.associationType == "HasOneAsPolymorphic">
        @HasOne("${associated.className}")
        ${associated.property}: ${associated.className}
    <#elseif associated.associationType == "BelongsTo" || associated.associationType == "BelongsToPolymorphic">
        @HasOne("${associated.className}")
        ${associated.property}: ${associated.className}
    </#if>
    </#list>

}