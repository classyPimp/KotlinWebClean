package ${packagesBean.baseGenerated}

import ${packagesBean.model}
<#list packagesBean.associatedModelTypesToImport as typeToImport>
import ${typeToImport}
</#list>
<#list packagesBean.fieldTypes as fieldType>
import ${fieldType}
</#list>

object ${modelClass}DefaultAssociationsManager {

    <#list associationBeans as ab>
    <#if ab.associationType == "HAS_ONE">
    <#include "/model/associationPartials/hasOne.ftl">
    </#if>

    <#if ab.associationType == "HAS_MANY">
    <#include "/model/associationPartials/hasMany.ftl">
    </#if>

    <#if ab.associationType == "BELONGS_TO">
    <#include "/model/associationPartials/belongsTo.ftl">
    </#if>

    <#if ab.associationType == "BELONGS_TO_POLYMORPHIC">
    <#include "/model/associationPartials/belongsToPolymorphic.ftl">
    </#if>

    <#if ab.associationType == "HAS_ONE_AS_POLYMORPHIC">
    <#include "/model/associationPartials/hasOneAsPolymorphic.ftl">
    </#if>

    <#if ab.associationType == "HAS_MANY_AS_POLYMORPHIC">
    <#include "/model/associationPartials/hasManyAsPolymorphic.ftl">
    </#if>

    </#list>
}
