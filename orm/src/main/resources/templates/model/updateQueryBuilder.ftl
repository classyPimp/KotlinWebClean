package ${packagesBean.baseGenerated}

import org.jooq.UpdateQuery
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
import orm.dependencymanagement.OrmDependenciesManager

open class ${modelClass}UpdateQueryBuilder
constructor(
    val model: ${modelClass},
    dslContext: DSLContext,
    var tableOrAlias: ${jooqTableSimpleName}
) {
    constructor(model: ${modelClass}): this(model, OrmDependenciesManager.provider.defaultDslContext, ${jooqTableInstance})
    constructor(model: ${modelClass}, tableOrAlias: ${jooqTableSimpleName}): this(model, OrmDependenciesManager.provider.defaultDslContext, tableOrAlias)
    constructor(model: ${modelClass}, dslContext: DSLContext): this(model, dslContext, ${jooqTableInstance})
    val updateQuery: UpdateQuery<${jooqTableSimpleName}Record>
    init {
        updateQuery = dslContext.updateQuery(tableOrAlias)
    }

    var setValuesCalled = false

    <#list fieldBeans as fieldBean>
    fun set${fieldBean.capitalizedProperty}(): ${modelClass}UpdateQueryBuilder {
        setValuesCalled = true
        updateQuery.addValue(tableOrAlias.${fieldBean.tableFieldName}, model.${fieldBean.property})
        return this
    }
    </#list>

    fun setTimestamps(): ${modelClass}UpdateQueryBuilder {
        model.updatedAt = Timestamp(Instant.now().toEpochMilli())
        updateQuery.addValue(tableOrAlias.UPDATED_AT, model.updatedAt)
        return this
    }

    fun setDefaultValues(): ${modelClass}UpdateQueryBuilder {
        model.updatedAt = Timestamp(Instant.now().toEpochMilli())
        <#list fieldBeansExceptPrimaryKey as fieldBean>
        updateQuery.addValue(tableOrAlias.${fieldBean.tableFieldName}, model.${fieldBean.property})
        </#list>
        return this
    }

    fun execute() {
        if (model.${primaryKey.property} == null) {
            throw(Exception("update failed: no primary key on model"))
        }
        if (!setValuesCalled) {
            setDefaultValues()
        }
        updateQuery.addConditions(tableOrAlias.${primaryKey.tableFieldName}.eq(model.${primaryKey.property}))
        updateQuery.execute()
    }

}
