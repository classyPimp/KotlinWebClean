package ${packagesBean.baseGenerated}

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


object ${modelClass}Utils {

    <#list fieldBeans as fieldBean>
    fun build${fieldBean.capitalizedProperty}To${modelClass}Map(models: MutableList<${modelClass}>): MutableMap<${fieldBean.nonNullableType}, ${modelClass}> {
        val map = mutableMapOf<${fieldBean.nonNullableType}, ${modelClass}>()
        for (model in models) {
            model.${fieldBean.property}?.let {
                map[it] = model
            }
        }
        return map
    }
    </#list>

}
