package ${packagesBean.baseGenerated}

import org.jooq.DSLContext
import orm.modelUtils.RepositoryDbUtils
import ${packagesBean.jooqGeneratedTable}.*
import ${packagesBean.model}
import orm.dependencymanagement.OrmDependenciesManager
<#list packagesBean.fieldTypes as fieldType>
import ${fieldType}
</#list>

object ${modelClass}DefaultCreator {

    fun create(model: ${modelClass}, dslContext: DSLContext = OrmDependenciesManager.provider.defaultDslContext): ${modelClass}{

    <#if hasTimestamps>
        model.createdAt = Timestamp(Instant.now().toEpochMilli())
        model.updatedAt = model.createdAt
    </#if>

        val resultSet = dslContext
                .insertInto(${jooqTableInstance})
                .columns(
                <#list fieldBeansExceptPrimaryKey as fieldBean>
                    ${jooqTableInstance}.${fieldBean.tableFieldName}<#sep>,</#sep>
                </#list>
                )
                .values(
                <#list fieldBeansExceptPrimaryKey as fieldBean>
                   model.${fieldBean.property}<#sep>,</#sep>
                </#list>
                )
                .returning(
                    ${jooqTableInstance}.${primaryKey.tableFieldName}
                )
                .fetch().intoResultSet()

        resultSet.next()

        RepositoryDbUtils.executeThisBlockAndCloseResultSet(resultSet) {
            val ${primaryKey.property} = resultSet.get${primaryKey.nonNullableType}(1)
            model.${primaryKey.property} = ${primaryKey.property}
        }

        return model
    }

}
