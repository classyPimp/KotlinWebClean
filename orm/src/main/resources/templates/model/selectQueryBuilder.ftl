package ${packagesBean.baseGenerated}

import org.jooq.SelectQuery
import org.jooq.Condition
import org.jooq.DSLContext
import org.jooq.TableLike
import org.jooq.SelectField
import org.jooq.impl.DSL.field
import java.sql.ResultSet
import ${packagesBean.jooqGeneratedTable}.*
import ${packagesBean.jooqGeneratedTable}
import ${packagesBean.jooqTablesRoot}.records.${jooqRecordClass}
import ${packagesBean.model}
<#list packagesBean.fieldTypes as fieldType>
import ${fieldType}
</#list>
import orm.dependencymanagement.OrmDependenciesManager


open class ${modelClass}SelectQueryBuilder
constructor(
  dslContext: DSLContext,
  var tableOrAlias: ${jooqTableSimpleName}
) {
    constructor(): this(OrmDependenciesManager.provider.defaultDslContext, ${jooqTableInstance})
    constructor(tableOrAlias: ${jooqTableSimpleName}): this(OrmDependenciesManager.provider.defaultDslContext, tableOrAlias)
    constructor(dslContext: DSLContext): this(dslContext, ${jooqTableInstance})
    val selectQuery: SelectQuery<${jooqTableSimpleName}Record>
    init {
        selectQuery = dslContext.selectQuery(tableOrAlias)
    }

    private var selectIsCalled: Boolean = false

    var associationsPreloader: ${modelClass}AssociationsPreloader? = null

    fun preload(block: (${modelClass}AssociationsPreloader)->Unit): ${modelClass}SelectQueryBuilder {
        associationsPreloader?.let {
            block.invoke(it)
            return this
        }
        ${modelClass}AssociationsPreloader().let {
            associationsPreloader = it
            block.invoke(it)
        }
        return this
    }

    fun wherePrimaryKeyEq(primaryKey: ${primaryKey.nonNullableType}): ${modelClass}SelectQueryBuilder {
        where(tableOrAlias.${primaryKey.tableFieldName}.eq(primaryKey))
        return this
    }

    fun throwModelNotFoundException(message: String) {
        throw Exception("Model: ${modelClass} not found: " + message)
    }

    fun wherePrimaryKeyIn(primaryKeys: MutableList<${primaryKey.nonNullableType}>): ${modelClass}SelectQueryBuilder {
        where(tableOrAlias.${primaryKey.tableFieldName}.`in`(primaryKeys))
        return this
    }

    fun executeThrowIfNotFoundFirst(): ${modelClass} {
        val model = execute().firstOrNull()
        if (model == null) {
            throwModelNotFoundException("foo")
        }
        return model!!
    }

    fun parse(resultSet: ResultSet): MutableList<${modelClass}> {
        return ${modelClass}ResultSetParser.parseResultSet(resultSet)
    }

    fun select(vararg fields: SelectField<*>): ${modelClass}SelectQueryBuilder {
        selectIsCalled = true
        selectQuery.addSelect(*fields)
        return this
    }

    fun where(vararg conditions: Condition): ${modelClass}SelectQueryBuilder {
        selectQuery.addConditions(*conditions)
        return this
    }

    fun limit(numberOfRows: Int): ${modelClass}SelectQueryBuilder {
        selectQuery.addLimit(numberOfRows)
        return this
    }

    fun join(tableLike: TableLike<*>, vararg conditions: Condition): ${modelClass}SelectQueryBuilder{
        selectQuery.addJoin(tableLike, *conditions)
        return this
    }

    fun join(block: (${modelClass}AssociationsJoinBuilder)->Unit): ${modelClass}SelectQueryBuilder {
        block.invoke(${modelClass}AssociationsJoinBuilder(this))
        return this
    }

    fun execute(): MutableList<${modelClass}> {
        if (!selectIsCalled) {
            select(field("${r"${tableOrAlias.name}.*"}"))
        }
        val resultSet = selectQuery.fetchResultSet()
        val models = ${modelClass}ResultSetParser.parseResultSet(resultSet)
        associationsPreloader?.let {
            it.preload(models)
        }
        return models
    }

    <#list fieldBeans as fieldBean>
    fun where${fieldBean.capitalizedProperty}Eq(value: ${fieldBean.type}): ${modelClass}SelectQueryBuilder {
        where(tableOrAlias.${fieldBean.tableFieldName}.eq(value))
        return this
    }

    fun where${fieldBean.capitalizedProperty}In(values: MutableList<${fieldBean.nonNullableType}>): ${modelClass}SelectQueryBuilder {
        where(tableOrAlias.${fieldBean.tableFieldName}.`in`(values))
        return this
    }

    fun where${fieldBean.capitalizedProperty}In(values: MutableSet<${fieldBean.nonNullableType}>): ${modelClass}SelectQueryBuilder {
      where(tableOrAlias.${fieldBean.tableFieldName}.`in`(values))
      return this
    }
    </#list>

}
