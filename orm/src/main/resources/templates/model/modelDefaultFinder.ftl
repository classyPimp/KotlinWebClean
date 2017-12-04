package ${packagesBean.baseGenerated}

import org.jooq.DSLContext
import java.sql.ResultSet
import ${packagesBean.jooqGeneratedTable}.*
import ${packagesBean.model}
<#list packagesBean.fieldTypes as fieldType>
import ${fieldType}
</#list>
import orm.dependencymanagement.OrmDependenciesManager

object ${modelClass}DefaultFinder {

    <#list fieldBeans as fieldBean>
    fun findBy${fieldBean.capitalizedProperty}(value: ${fieldBean.type}): ${modelClass} {
        val models = executeAndParse {
            it.selectFrom(${jooqTableInstance})
                .where(${jooqTableInstance}.${fieldBean.tableFieldName}.eq(value))
                .fetchResultSet()
        }

        val modelToReturn =  models.firstOrNull()

        if (modelToReturn == null) {
            throwModelNotFoundException()
        }

        return modelToReturn!!
    }

    fun findBy${fieldBean.capitalizedProperty}OrNull(value: ${fieldBean.type}): ${modelClass}? {
        val models = executeAndParse {
            it.selectFrom(${jooqTableInstance})
                .where(${jooqTableInstance}.${fieldBean.tableFieldName}.eq(value))
                .fetchResultSet()
        }

        return  models.firstOrNull()
    }

    fun where${fieldBean.capitalizedProperty}In(values: MutableList<${fieldBean.nonNullableType}>): MutableList<${modelClass}> {
        return executeAndParse {
            it.selectFrom(${jooqTableInstance})
                .where(${jooqTableInstance}.${fieldBean.tableFieldName}.`in`(values))
                .fetchResultSet()
        }
    }

    fun where${fieldBean.capitalizedProperty}(value: ${fieldBean.type}): MutableList<${modelClass}> {
        return executeAndParse {
            it.selectFrom(${jooqTableInstance})
                .where(${jooqTableInstance}.${fieldBean.tableFieldName}.eq(value))
                .fetchResultSet()
        }
    }
    </#list>

    fun findByPrimaryKey(primaryKey: ${primaryKey.nonNullableType}): ${modelClass} {
        val models = executeAndParse {
            it.selectFrom(${jooqTableInstance})
                .where(${jooqTableInstance}.${primaryKey.tableFieldName}.eq(primaryKey))
                .fetchResultSet()
        }

        if (models.isEmpty()) {
            throwModelNotFoundException()
        }

        return models.first()
    }

    fun throwModelNotFoundException() {
        throw Throwable("Model: ${modelClass} not found")
    }

    fun findWherePrimaryKeyIn(primaryKeys: MutableList<${primaryKey.nonNullableType}>): MutableList<${modelClass}> {
        return executeAndParse {
            it.selectFrom(${jooqTableInstance})
                .where(${jooqTableInstance}.${primaryKey.tableFieldName}.`in`(primaryKeys))
                .fetchResultSet()
        }
    }

    inline fun executeAndParse(block: (DSLContext)->ResultSet): MutableList<${modelClass}> {
        val resultSet = block.invoke(OrmDependenciesManager.provider.defaultDslContext)
        val models = parse(resultSet)
        return models
    }

    fun parse(resultSet: ResultSet): MutableList<${modelClass}> {
        return ${modelClass}ResultSetParser.parseResultSet(resultSet)
    }

}
