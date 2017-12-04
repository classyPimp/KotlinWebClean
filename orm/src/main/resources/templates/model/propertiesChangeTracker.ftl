package ${packagesBean.baseGenerated}

import ${packagesBean.jooqGeneratedTable}.*
import ${packagesBean.jooqGeneratedTable}
import ${packagesBean.jooqTablesRoot}.records.${jooqRecordClass}
import ${packagesBean.model}
<#list packagesBean.fieldTypes as fieldType>
import ${fieldType}
</#list>

class ${modelClass}PropertiesChangeTracker(val model: ${modelClass}) {
  
    var hasChanges = false

    <#list fieldBeans as fieldBean>
    var ${fieldBean.property}IsChanged = false
    var old${fieldBean.capitalizedProperty}Value: ${fieldBean.nonNullableType}? = null
    var new${fieldBean.capitalizedProperty}Value: ${fieldBean.nonNullableType}? = null
    </#list>
    
    <#list fieldBeans as fieldBean>
    fun record${fieldBean.capitalizedProperty}Change(newValue: ${fieldBean.type}){
        if (model.${fieldBean.property} != newValue) {
            hasChanges = true
            ${fieldBean.property}IsChanged = true
            if (old${fieldBean.capitalizedProperty}Value == null) {
                old${fieldBean.capitalizedProperty}Value = model.${fieldBean.property}
            }
            new${fieldBean.capitalizedProperty}Value = newValue
        }
    }
    </#list>
    
    fun reset(){
        hasChanges = false
        <#list fieldBeans as fieldBean>
        ${fieldBean.property}IsChanged = false
        old${fieldBean.capitalizedProperty}Value = null
        new${fieldBean.capitalizedProperty}Value = null
        </#list>
    }


}