package ${packagesBean.baseGenerated}

import org.jooq.SelectQuery
import org.jooq.Condition
import org.jooq.DSLContext
import java.sql.ResultSet
import ${packagesBean.jooqGeneratedTable}
import ${packagesBean.jooqGeneratedTable}.*
import ${packagesBean.model}
<#list packagesBean.fieldTypes as fieldType>
import ${fieldType}
</#list>
<#list packagesBean.associatedModelTypesToImport as typeToImport>
import ${typeToImport}
</#list>

class ${modelClass}AssociationsJoinBuilder(
    val selectQuery: SelectQuery<*>,
    val tableOrAlias: ${jooqTableSimpleName} = ${jooqTableInstance}
) {
    constructor(selectQueryBuilder: ${modelClass}SelectQueryBuilder): this(
            selectQueryBuilder.selectQuery,
            selectQueryBuilder.tableOrAlias
    )

    <#list associationBeans as ab>
    <#if ab.associationType == "BELONGS_TO_POLYMORPHIC">
    class ${ab.capitalizedPropertyName}Joiner(
        val selectQuery: SelectQuery<*>,
        val tableOrAlias: ${jooqTableSimpleName} = ${jooqTableInstance}
    ) {
        constructor(selectQueryBuilder: ${modelClass}SelectQueryBuilder): this(
                selectQueryBuilder.selectQuery,
                selectQueryBuilder.tableOrAlias
        )
        <#list ab.associatedPolymorphicModelDataModels as associatedModelDataModel>
        fun ${associatedModelDataModel.modelClassDecapitalized}(){
            selectQuery.addJoin(${associatedModelDataModel.jooqTableInstance}, ${associatedModelDataModel.jooqTableInstance}.${ab.fieldOnThat.tableFieldName}.eq(tableOrAlias.${ab.fieldOnThis.tableFieldName}))
        }

        fun ${associatedModelDataModel.modelClassDecapitalized}(joinedTableAlias: ${associatedModelDataModel.jooqTableSimpleName}){
            selectQuery.addJoin(${associatedModelDataModel.jooqTableInstance}, joinedTableAlias.${ab.fieldOnThat.tableFieldName}.eq(tableOrAlias.${ab.fieldOnThis.tableFieldName}))
        }

        fun ${associatedModelDataModel.modelClassDecapitalized}(block: (${associatedModelDataModel.modelClass}AssociationsJoinBuilder)->Unit) {
            ${associatedModelDataModel.modelClassDecapitalized}()
            block.invoke(${associatedModelDataModel.modelClass}AssociationsJoinBuilder(selectQuery))
        }

        fun ${associatedModelDataModel.modelClassDecapitalized}(joinedTableAlias: ${associatedModelDataModel.jooqTableSimpleName}, block: (${associatedModelDataModel.modelClass}AssociationsJoinBuilder)->Unit) {
            ${associatedModelDataModel.modelClassDecapitalized}(joinedTableAlias)
            block.invoke(${associatedModelDataModel.modelClass}AssociationsJoinBuilder(selectQuery))
        }
        </#list>
    }

    fun ${ab.propertyName}(): ${ab.capitalizedPropertyName}Joiner{
        return ${ab.capitalizedPropertyName}Joiner(selectQuery, tableOrAlias)
    }
    <#elseif ab.associationType == "HAS_MANY_AS_POLYMORPHIC" || ab.associationType == "HAS_ONE_AS_POLYMORPHIC">
    fun ${ab.propertyName}() {
        selectQuery.addJoin(
            ${ab.associatedModelDataModel.jooqTableInstance},
            ${ab.associatedModelDataModel.jooqTableInstance}.${ab.fieldOnThat.tableFieldName}.eq(tableOrAlias.${ab.fieldOnThis.tableFieldName})
            .and(
                ${ab.associatedModelDataModel.jooqTableInstance}
                .${ab.polymorphicTypeField.tableFieldName}
                .eq("${modelClass}")
            )
        )
    }

    fun ${ab.propertyName}(joinedTableAlias: ${ab.associatedModelDataModel.jooqTableSimpleName}) {
        selectQuery.addJoin(
            joinedTableAlias,
            joinedTableAlias.${ab.fieldOnThat.tableFieldName}.eq(tableOrAlias.${ab.fieldOnThis.tableFieldName})
            .and(
                joinedTableAlias
                .${ab.polymorphicTypeField.tableFieldName}
                .eq("${modelClass}")
            )
        )
    }

    fun ${ab.propertyName}(block: (${ab.associatedModelDataModel.modelClass}AssociationsJoinBuilder)->Unit) {
        ${ab.propertyName}()
        block.invoke(${ab.associatedModelDataModel.modelClass}AssociationsJoinBuilder(selectQuery))
    }

    fun ${ab.propertyName}(joinedTableAlias: ${ab.associatedModelDataModel.jooqTableSimpleName}, block: (${ab.associatedModelDataModel.modelClass}AssociationsJoinBuilder)->Unit) {
        ${ab.propertyName}(joinedTableAlias)
        block.invoke(${ab.associatedModelDataModel.modelClass}AssociationsJoinBuilder(selectQuery))
    }
    <#else>
    fun ${ab.propertyName}() {
        selectQuery.addJoin(${ab.associatedModelDataModel.jooqTableInstance}, ${ab.associatedModelDataModel.jooqTableInstance}.${ab.fieldOnThat.tableFieldName}.eq(tableOrAlias.${ab.fieldOnThis.tableFieldName}))
    }

    fun ${ab.propertyName}(joinedTableAlias: ${ab.associatedModelDataModel.jooqTableSimpleName}) {
        selectQuery.addJoin(joinedTableAlias, joinedTableAlias.${ab.fieldOnThat.tableFieldName}.eq(tableOrAlias.${ab.fieldOnThis.tableFieldName}))
    }

    fun ${ab.propertyName}(block: (${ab.associatedModelDataModel.modelClass}AssociationsJoinBuilder)->Unit) {
        ${ab.propertyName}()
        block.invoke(${ab.associatedModelDataModel.modelClass}AssociationsJoinBuilder(selectQuery))
    }

    fun ${ab.propertyName}(joinedTableAlias: ${ab.associatedModelDataModel.jooqTableSimpleName}, block: (${ab.associatedModelDataModel.modelClass}AssociationsJoinBuilder)->Unit) {
        ${ab.propertyName}(joinedTableAlias)
        block.invoke(${ab.associatedModelDataModel.modelClass}AssociationsJoinBuilder(selectQuery))
    }
    </#if>
    </#list>

}
