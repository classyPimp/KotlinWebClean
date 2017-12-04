package ${packagesBean.baseGenerated}

import ${packagesBean.model}
<#list packagesBean.associatedModelTypesToImport as typeToImport>
import ${typeToImport}
</#list>



class ${modelClass}AssociationsPreloader {

    var preloaders: MutableList<(MutableList<${modelClass}>)->Unit> = mutableListOf()

    fun preload(models: MutableList<${modelClass}>){
        preloaders.forEach {
            it.invoke(models)
        }
    }

    <#list associationBeans as ab>
    <#if ab.associationType == "BELONGS_TO_POLYMORPHIC">
    fun ${ab.propertyName}(block: (${modelClass}DefaultAssociationsManager.${ab.capitalizedPropertyName}SelectQueryBuilders)->Unit) {
        preloaders.add(
            {models: MutableList<${modelClass}> ->
                ${modelClass}DefaultAssociationsManager.load${ab.capitalizedPropertyName}(models, block)
            }
        )
    }
    <#else>
    fun ${ab.propertyName}(block: (${ab.associatedModelDataModel.modelClass}SelectQueryBuilder)->Unit) {
        preloaders.add(
            {models: MutableList<${modelClass}> ->
                ${modelClass}DefaultAssociationsManager.load${ab.capitalizedPropertyName}(models, block)
            }
        )
    }

    fun ${ab.propertyName}() {
        preloaders.add(
            {models: MutableList<${modelClass}> ->
                ${modelClass}DefaultAssociationsManager.load${ab.capitalizedPropertyName}(models)
            }
        )
    }

    </#if>
    </#list>


}
