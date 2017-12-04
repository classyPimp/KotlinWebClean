package ${packagesBean.baseGenerated}

import orm.services.ModelPropertyValidator
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

open class ${modelClass}ValidatorTrait(
        open val model: ${modelClass},
        open val validationManager: ${modelClass}ValidationManager
) {

    <#list fieldBeans as fieldBean>
    fun ${fieldBean.property}Tester(): ModelPropertyValidator {
        return ModelPropertyValidator("${fieldBean.property}", validationManager)
    }
    </#list>

    <#list associationBeans as ab>
    fun ${ab.propertyName}Tester(): ModelPropertyValidator {
        return ModelPropertyValidator("${ab.propertyName}", validationManager)
    }
    </#list>

    inline fun testGeneral(errorMessage: String = "invalid", block: ()->Boolean) {
        if (!block.invoke()) {
            validationManager.addError("general", errorMessage)
        }
    }

    inline fun test(fieldName: String, errorMessage: String = "invalid", block: ()->Boolean) {
        if (!block.invoke()) {
            validationManager.addError(fieldName, errorMessage)
        }
    }

}
