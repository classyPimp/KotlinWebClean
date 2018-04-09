package ${packagesBean.baseGenerated}

import orm.services.ModelInvalidError
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
import orm.dependencymanagement.OrmDependenciesManager


class ${modelClass}Record(val model: ${modelClass}) {
    var propertiesChangeTracker = ${modelClass}PropertiesChangeTracker(model)
    val validationManager: ${modelClass}ValidationManager by lazy { ${modelClass}ValidationManager() }

    <#list fieldBeans as fieldBean>
    var ${fieldBean.property}: ${fieldBean.type}
        get() {
            return model.${fieldBean.property}
        }
        set(value) {
            propertiesChangeTracker.record${fieldBean.capitalizedProperty}Change(value)
            model.${fieldBean.property} = value
        }
    </#list>


    <#list associationBeans as ab>

    fun load${ab.capitalizedPropertyName}() {
        ${modelClass}DefaultAssociationsManager.load${ab.capitalizedPropertyName}(model)
    }

    <#if ab.associationType == "BELONGS_TO_POLYMORPHIC">
    fun load${ab.capitalizedPropertyName}(blockYieldingQueryBuilder: (${modelClass}DefaultAssociationsManager.${ab.capitalizedPropertyName}SelectQueryBuilders)->Unit) {
        ${modelClass}DefaultAssociationsManager.load${ab.capitalizedPropertyName}(model, blockYieldingQueryBuilder)
    }
    <#else>
    fun load${ab.capitalizedPropertyName}(blockYieldingQueryBuilder: (${ab.associatedModelDataModel.modelClass}SelectQueryBuilder)->Unit) {
        ${modelClass}DefaultAssociationsManager.load${ab.capitalizedPropertyName}(model, blockYieldingQueryBuilder)
    }
    </#if>

    var ${ab.propertyName}: ${ab.completeReturnType}
        get() {
            return model.${ab.propertyName}
            //if (model.${ab.propertyName} != null) {
            //    return model.${ab.propertyName}
            //} else {
            //    load${ab.capitalizedPropertyName}()
            //    return model.${ab.propertyName}
            //}
        }
        set(value) {
            model.${ab.propertyName} = value
            <#if ab.associationType == "HAS_ONE">
            if (value != null) {
                value.record.${ab.fieldOnThat.property} = model.${ab.fieldOnThis.property}
            }
            <#elseif ab.associationType == "HAS_ONE_AS_POLYMORPHIC">
            value?.let {
              it.record.${ab.fieldOnThat.property} = model.${ab.fieldOnThis.property}
              it.record.${ab.polymorphicTypeField.property} = "${modelClass}"
            }
            <#elseif ab.associationType == "HAS_ONE_AS_POLYMORPHIC">
            value?.forEach {
              it.record.${ab.fieldOnThat.property} = model.${ab.fieldOnThis.property}
              it.record.${ab.polymorphicTypeField.property} = "${modelClass}"
            }
            <#elseif ab.associationType == "HAS_MANY">
            value?.forEach {
                it.record.${ab.fieldOnThat.property} = model.${ab.fieldOnThis.property}
            }
            <#elseif ab.associationType == "BELONGS_TO">
            model.record.${ab.fieldOnThis.property} = value?.${ab.fieldOnThat.property}
            <#elseif ab.associationType == "BELONGS_TO_POLYMORPHIC">
            value?.let {
                when(it) {
                  <#list ab.associatedPolymorphicModelDataModels as associatedModelDataModel>
                  is ${associatedModelDataModel.modelClass} -> {
                      model.record.${ab.fieldOnThis.property} = it.record.${ab.fieldOnThat.property}
                      model.record.${ab.polymorphicTypeField.property} = "${associatedModelDataModel.modelClass}"
                  }
                  </#list>
                }
            }
            </#if>
        }
    </#list>

    fun save(dslContext: DSLContext = OrmDependenciesManager.provider.defaultDslContext){
        if (!validationManager.isValid()) {
            throw ModelInvalidError("failed saving ${r"${model}"} of ${modelClass}: validation failed")
        }
        if (isPersited()) {
            updateChanged(dslContext)
        } else {
            ${modelClass}InsertQueryBuilder(model, dslContext).execute()
        }
    }

    fun saveCascade(dslContext: DSLContext = OrmDependenciesManager.provider.defaultDslContext){
        if (!validationManager.isValid()) {
            throw ModelInvalidError("failed saving ${r"${model}"} of ${modelClass}: validation failed")
        }
        if (isPersited()) {
            updateChanged(dslContext)
            saveCascadeDependentOnUpdate(dslContext)
        } else {
            ${modelClass}InsertQueryBuilder(model, dslContext).execute()
            saveCascadeDependentOnCreate(dslContext)
        }
    }

    inline fun save(before: (${modelClass}Record)->Unit, after: (${modelClass}Record)->Unit, dslContext: DSLContext = OrmDependenciesManager.provider.defaultDslContext) {
        before.invoke(this)
        save(dslContext)
        after.invoke(this)
    }

    inline fun saveCascade(before: (${modelClass}Record)->Unit, after: (${modelClass}Record)->Unit, dslContext: DSLContext = OrmDependenciesManager.provider.defaultDslContext) {
        before.invoke(this)
        saveCascade(dslContext)
        after.invoke(this)
    }

    private fun saveCascadeDependentOnUpdate(dslContext: DSLContext = OrmDependenciesManager.provider.defaultDslContext) {
        <#list associationBeans as ab>
        <#if ab.associationType == "HAS_ONE">
        model.${ab.propertyName}?.let {
            it.record.saveCascade(dslContext)
        }
        <#elseif ab.associationType == "HAS_ONE_AS_POLYMORPHIC">
        model.${ab.propertyName}?.let {
            it.record.saveCascade(dslContext)
        }
        <#elseif ab.associationType == "HAS_MANY">
        model.${ab.propertyName}?.forEach {
            it.record.saveCascade(dslContext)
        }
        <#elseif ab.associationType == "HAS_MANY_AS_POLYMORPHIC">
        model.${ab.propertyName}?.forEach {
            it.record.saveCascade(dslContext)
        }
        <#elseif ab.associationType == "BELONGS_TO">
        model.${ab.propertyName}?.let {
            it.record.saveCascade(dslContext)
        }
        <#elseif ab.associationType == "BELONGS_TO_POLYMORPHIC">
        model.${ab.propertyName}?.let {
            when(it) {
                <#list ab.associatedPolymorphicModelDataModels as associatedModelDataModel>
                is ${associatedModelDataModel.modelClass} -> {
                    it.record.saveCascade(dslContext)
                }
                </#list>
            }
        }
        </#if>
         </#list>
    }

    private fun saveCascadeDependentOnCreate(dslContext: DSLContext = OrmDependenciesManager.provider.defaultDslContext) {
        <#list associationBeans as ab>
        <#if ab.associationType == "HAS_ONE">
        model.${ab.propertyName}?.let {
            it.record.${ab.fieldOnThat.property} = model.${ab.fieldOnThis.property}
            it.record.saveCascade(dslContext)
        }
        <#elseif ab.associationType == "HAS_ONE_AS_POLYMORPHIC">
        model.${ab.propertyName}?.let {
            it.record.${ab.fieldOnThat.property} = model.${ab.fieldOnThis.property}
            it.record.${ab.polymorphicTypeField.property} = "${modelClass}"
            it.record.saveCascade(dslContext)
        }
        <#elseif ab.associationType == "HAS_MANY">
        model.${ab.propertyName}?.forEach {
            it.record.${ab.fieldOnThat.property} = model.${ab.fieldOnThis.property}
            it.record.saveCascade(dslContext)
        }
        <#elseif ab.associationType == "HAS_MANY_AS_POLYMORPHIC">
        model.${ab.propertyName}?.forEach {
            it.record.${ab.fieldOnThat.property} = model.${ab.fieldOnThis.property}
            it.record.saveCascade(dslContext)
        }
        <#elseif ab.associationType == "BELONGS_TO">
        model.${ab.propertyName}?.let {
            it.record.saveCascade(dslContext)
        }
        <#elseif ab.associationType == "BELONGS_TO_POLYMORPHIC">
        model.${ab.propertyName}?.let {
            when(it) {
                <#list ab.associatedPolymorphicModelDataModels as associatedModelDataModel>
                is ${associatedModelDataModel.modelClass} -> {
                    it.record.saveCascade(dslContext)
                }
                </#list>
            }
        }
        </#if>
        </#list>
    }

    fun delete(dslContext: DSLContext = OrmDependenciesManager.provider.defaultDslContext){
        if (!validationManager.isValid()) {
            throw ModelInvalidError("failed deleting ${r"${model}"} of ${modelClass}: validation failed")
        }
        if (!isPersited()) {
            throw Exception("delete failed not persisted TODO: more informative error")
        }
        <#list associationBeans as ab>
        <#if ab.associationType == "HAS_ONE">
        ${ab.propertyName}?.let {
            it.record.delete(dslContext)
        }
        <#elseif ab.associationType == "HAS_ONE_AS_POLYMORPHIC">
        ${ab.propertyName}?.let {
            it.record.delete(dslContext)
        }
        <#elseif ab.associationType == "HAS_MANY">
        model.${ab.propertyName}?.forEach {
            it.record.delete(dslContext)
        }
        <#elseif ab.associationType == "HAS_MANY_AS_POLYMORPHIC">
        model.${ab.propertyName}?.forEach {
            it.record.delete(dslContext)
        }
        </#if>
        </#list>
        ${modelClass}DeleteQueryBuilder(model, dslContext).execute()
    }

    inline fun delete(before: (${modelClass}Record)->Unit, after: (${modelClass}Record)->Unit, dslContext: DSLContext = OrmDependenciesManager.provider.defaultDslContext) {
        before.invoke(this)
        delete(dslContext)
        after.invoke(this)
    }

    fun updateChanged(dslContext: DSLContext = OrmDependenciesManager.provider.defaultDslContext) {
        if (propertiesChangeTracker.hasChanges) {
            val updateQueryBuilder = ${modelClass}UpdateQueryBuilder(model, dslContext)
            setValuesOnUpdateQueryUsingChangeTracker(updateQueryBuilder)
            updateQueryBuilder.execute()
        }
    }

    private fun setValuesOnUpdateQueryUsingChangeTracker(updateQueryBuilder: ${modelClass}UpdateQueryBuilder) {
        propertiesChangeTracker.let {
            <#list fieldBeans as fieldBean>
            if (it.${fieldBean.property}IsChanged) {
                updateQueryBuilder.set${fieldBean.capitalizedProperty}()
            }
            </#list>
            if (it.updatedAtIsChanged) {
                updateQueryBuilder.setUpdatedAt()
            } else {
                updateQueryBuilder.setTimestamps()
            }
        }
    }

    fun isPersited(): Boolean {
        model.${primaryKey.property}?.let {
            return true
        }
        return false
    }

    //:::::::::::::::::::::DELEGATES TO MODEL:::::::::::::::


    <#list delegateFromRecordProperties as delegate>
    <#if delegate.delegatesType == "GETTER">
    val ${delegate.property}: ${delegate.type}
        get(){
            return this.model.${delegate.property}
        }
    <#elseif delegate.delegatesType == "PROPERTY">
    var ${delegate.property}: ${delegate.type}
        get(){
            return this.model.${delegate.property}
        }
        set(value: ${delegate.type}) {
            this.model.${delegate.property} = value
        }
    <#elseif delegate.delegatesType == "UNSUPPORTED FUNCTION">
    fun ${delegate.property}(
        <#list delegate.parameters?keys as param>
        ${param}: ${delegate.parameters[param]}<#sep>,
        </#list>
    ): ${delegate.type} {
       return this.model.${delegate.property}(
       <#list delegate.parameters?keys as param>
           ${param}<#sep>,
       </#list>
       )
    }
    </#if>
    </#list>
    //END::::::::::::::::DELEGATES TO MODEL:::::::::::::::END

    companion object {
        fun GET(): ${modelClass}SelectQueryBuilder {
            return ${modelClass}SelectQueryBuilder()
        }
    }


}
