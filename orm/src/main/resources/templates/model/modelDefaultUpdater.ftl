package ${packagesBean.baseGenerated}

import org.jooq.DSLContext
import ${packagesBean.jooqGeneratedTable}.*
import orm.modelUtils.RepositoryDbUtils
import ${packagesBean.model}
<#list packagesBean.fieldTypes as fieldType>
import ${fieldType}
</#list>
import orm.dependencymanagement.OrmDependenciesManager

object ${modelClass}DefaultUpdater {

    fun update(model: ${modelClass}, dslContext: DSLContext = OrmDependenciesManager.provider.defaultDslContext){

        <#if hasTimestamps>
        model.updatedAt = Timestamp(Instant.now().toEpochMilli())
        </#if>

        dslContext.update(${jooqTableInstance})
            <#list fieldBeansExceptPrimaryKey as fieldBean>
                .set(${jooqTableInstance}.${fieldBean.tableFieldName}, model.${fieldBean.property})
            </#list>
                .where(${jooqTableInstance}.${primaryKey.tableFieldName}.eq(model.${primaryKey.property}))
                .execute()
    }

}
