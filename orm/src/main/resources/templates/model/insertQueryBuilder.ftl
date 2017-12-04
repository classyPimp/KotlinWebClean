package ${packagesBean.baseGenerated}

import org.jooq.SelectQuery
import org.jooq.Condition
import org.jooq.DSLContext
import org.jooq.TableLike
import org.jooq.SelectField
import org.jooq.InsertQuery
import java.sql.ResultSet
import ${packagesBean.jooqGeneratedTable}.*
import ${packagesBean.jooqGeneratedTable}
import ${packagesBean.jooqTablesRoot}.records.${jooqRecordClass}
import ${packagesBean.model}
import orm.dependencymanagement.OrmDependenciesManager
<#list packagesBean.fieldTypes as fieldType>
import ${fieldType}
</#list>

class ${modelClass}InsertQueryBuilder
constructor(
        val model: ${modelClass},
        dslContext: DSLContext = OrmDependenciesManager.provider.defaultDslContext
){  
    constructor(
        record: ${modelClass}Record,
        dslContext: DSLContext = OrmDependenciesManager.provider.defaultDslContext
    ) : this(record.model, dslContext)

    val insertQuery: InsertQuery<${jooqRecordClass}>
    init {
        insertQuery = dslContext.insertQuery(${jooqTableInstance})
    }

    var setValuesCalled = false

    <#list fieldBeans as fieldBean>
    fun set${fieldBean.capitalizedProperty}(): ${modelClass}InsertQueryBuilder {
        setValuesCalled = true
        insertQuery.addValue(${jooqTableInstance}.${fieldBean.tableFieldName}, model.${fieldBean.property})
        return this
    }
    </#list>

    fun setTimestamps(): ${modelClass}InsertQueryBuilder {
        setValuesCalled = true
        model.createdAt = model.createdAt ?: Timestamp(Instant.now().toEpochMilli())
        model.updatedAt = model.createdAt
        insertQuery.addValue(${jooqTableInstance}.CREATED_AT, model.createdAt)
        insertQuery.addValue(${jooqTableInstance}.UPDATED_AT, model.updatedAt)
        return this
    }

    fun setDefaultValues(): ${modelClass}InsertQueryBuilder {
        model.createdAt = Timestamp(Instant.now().toEpochMilli())
        model.updatedAt = model.createdAt
        <#list fieldBeansExceptPrimaryKey as fieldBean>
        insertQuery.addValue(${jooqTableInstance}.${fieldBean.tableFieldName}, model.${fieldBean.property})
        </#list>
        return this
    }

    fun execute() {
        if (!setValuesCalled) {
            setDefaultValues()
        }
        insertQuery.setReturning(${jooqTableInstance}.${primaryKey.tableFieldName})
        insertQuery.execute()
        val jooqReturnedRecord = insertQuery.returnedRecord
        model.${primaryKey.property} = jooqReturnedRecord.${primaryKey.property}
    }

}
