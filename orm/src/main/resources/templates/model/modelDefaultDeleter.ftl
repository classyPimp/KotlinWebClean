package ${packagesBean.baseGenerated}

import org.jooq.DSLContext
import ${packagesBean.jooqGeneratedTable}.*
import ${packagesBean.model}
import orm.dependencymanagement.OrmDependenciesManager
object ${modelClass}DefaultDeleter {

    fun delete(model: ${modelClass}, dslContext: DSLContext = OrmDependenciesManager.provider.defaultDslContext) {
        dslContext.deleteFrom(${jooqTableInstance})
            .where(${jooqTableInstance}.${primaryKey.tableFieldName}.eq(model.${primaryKey.property}))
            .execute()

        model.${primaryKey.property} = null
    }

}
