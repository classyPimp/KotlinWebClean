package ${packagesBean.baseGenerated}

import orm.services.ModelValidationManagerTrait
import org.jooq.SelectQuery
import org.jooq.Condition
import org.jooq.DSLContext
import org.jooq.TableLike
import org.jooq.SelectField
import java.sql.ResultSet
import ${packagesBean.jooqGeneratedTable}.*
import ${packagesBean.jooqGeneratedTable}
import ${packagesBean.jooqTablesRoot}.records.${jooqRecordClass}
import ${packagesBean.model}
<#list packagesBean.fieldTypes as fieldType>
import ${fieldType}
</#list>
<#list packagesBean.associatedModelTypesToImport as typeToImport>
import ${typeToImport}
</#list>


class ${modelClass}ValidationManager : ModelValidationManagerTrait {
    override var errors: MutableMap<String, MutableList<String>>? = null

    <#list fieldBeans as fieldBean>
    fun add${fieldBean.capitalizedProperty}Error(message: String) {
        addError("${fieldBean.property}", message)
    }

    fun add${fieldBean.capitalizedProperty}Errors(list: MutableList<String>?) {
        list?.let {
            nonNullableErrors("${fieldBean.property}").plus(it)
        }
    }
    </#list>

    fun addGeneralError(errorMessage: String){
        addError("general", errorMessage)
    }

    <#list associationBeans as ab>
    fun add${ab.capitalizedPropertyName}Error(message: String) {
        addError("${ab.propertyName}", message)
    }

    fun add${ab.capitalizedPropertyName}Errors(list: MutableList<String>?) {
        list?.let {
            nonNullableErrors("${ab.propertyName}").plus(it)
        }
    }
    </#list>
}
