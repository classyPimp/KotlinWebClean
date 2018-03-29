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
import orm.dependencymanagement.adapters.modeltojsonserialization.IArrayNode
import orm.dependencymanagement.adapters.modeltojsonserialization.IObjectNode

class ${modelClass}ToJsonSerializer(val model: ${modelClass}) {
    companion object {
        val objectMapper = OrmDependenciesManager.provider.jsonObjectMapper
    
        fun serialize(models: MutableList<${modelClass}>): IArrayNode {
            val root = objectMapper.createArrayNode()
            models.forEach {
                root.add(${modelClass}ToJsonSerializer(it).serializeToNode())
            }
            return root
        }

        inline fun serialize(models: MutableList<${modelClass}>, block: (${modelClass}ToJsonSerializer)->Unit): IArrayNode {
            val root = objectMapper.createArrayNode()
            models.forEach {
                val serializer = ${modelClass}ToJsonSerializer(it)
                block.invoke(serializer)
                root.add(serializer.serializeToNode())
            }
            return root
        }

    }

    enum class SerializablePropertiesEnum {
        <#list fieldBeans as fieldBean>
        ${fieldBean.tableFieldName}<#sep>, </#sep>
        </#list>
    }

    //needed for more convenient exclusions
    object Properties {
        <#list fieldBeans as fieldBean>
        val ${fieldBean.property} = SerializablePropertiesEnum.${fieldBean.tableFieldName}
        </#list>
    }

    var whichPropertiesToSerialize: MutableMap<SerializablePropertiesEnum, Boolean> = mutableMapOf(
        <#list fieldBeans as fieldBean>
        SerializablePropertiesEnum.${fieldBean.tableFieldName} to true<#sep>, </#sep>
        </#list>
    )

    val root = objectMapper.createObjectNode()

    inline fun except(block: (Properties)->Array<SerializablePropertiesEnum>): ${modelClass}ToJsonSerializer {
        block.invoke(Properties).forEach {
            whichPropertiesToSerialize[it] = false
        }
        return this
    }

    inline fun only(block: (Properties)->Array<SerializablePropertiesEnum>): ${modelClass}ToJsonSerializer {
        whichPropertiesToSerialize = mutableMapOf(
            <#list fieldBeans as fieldBean>
            SerializablePropertiesEnum.${fieldBean.tableFieldName} to false<#sep>, </#sep>
            </#list>
        )
        block.invoke(Properties).forEach {
            whichPropertiesToSerialize[it] = true
        }
        return this
    }

    fun includeErrors(): ${modelClass}ToJsonSerializer {
        val node = objectMapper.createObjectNode()
        model.record.validationManager.nonNullableErrors().forEach {
            key, value ->
            val arrayNode = objectMapper.createArrayNode()
            value.forEach {
                arrayNode.add(it)
            }
            node.set(key, arrayNode)
        }
        root.set("errors", node)
        return this
    }


    <#list associationBeans as ab>

    <#if ab.associationType == "HAS_ONE" || ab.associationType == "BELONGS_TO" || ab.associationType == "HAS_ONE_AS_POLYMORPHIC">
    fun include${ab.capitalizedPropertyName}(): ${modelClass}ToJsonSerializer {
        model.${ab.propertyName}?.let {
            root.set("${ab.propertyName}", ${ab.associatedModelDataModel.modelClass}ToJsonSerializer(it).serializeToNode())
            return this
        }
        root.set("${ab.propertyName}", null)
        return this
    }

    inline fun include${ab.capitalizedPropertyName}(block: (${ab.associatedModelDataModel.modelClass}ToJsonSerializer)->Unit): ${modelClass}ToJsonSerializer {
        model.${ab.propertyName}?.let {
            val thatModelSerializer = ${ab.associatedModelDataModel.modelClass}ToJsonSerializer(it)
            block.invoke(thatModelSerializer)
            root.set("${ab.propertyName}", thatModelSerializer.serializeToNode())
            return this
        }
        root.set("${ab.propertyName}", null)
        return this
    }
    </#if>
    <#if ab.associationType == "BELONGS_TO_POLYMORPHIC">
    fun include${ab.capitalizedPropertyName}(): ${modelClass}ToJsonSerializer {
        model.${ab.propertyName}?.let {
            when(it) {
                <#list ab.associatedPolymorphicModelDataModels as associatedModelDataModel>
                is ${associatedModelDataModel.modelClass} -> {
                    root.set("${ab.propertyName}", ${associatedModelDataModel.modelClass}ToJsonSerializer(it).serializeToNode())
                    return this
                }
                </#list>
                else -> {
                    root.set("${ab.propertyName}", null)
                    return this
                }
            }
        }
        root.set("${ab.propertyName}", null)
        return this
    }

    class ${ab.capitalizedPropertyName}PolymorphicIncludeYielderProxy() {
        <#list ab.associatedPolymorphicModelDataModels as associatedModelDataModel>
        var ${associatedModelDataModel.modelClassDecapitalized}: ${associatedModelDataModel.modelClass}ToJsonSerializer? = null
        </#list>
    }

    inline fun include${ab.capitalizedPropertyName}(block: (${ab.capitalizedPropertyName}PolymorphicIncludeYielderProxy)->Unit): ${modelClass}ToJsonSerializer {
        model.${ab.propertyName}?.let {
            when(it) {
                <#list ab.associatedPolymorphicModelDataModels as associatedModelDataModel>
                is ${associatedModelDataModel.modelClass} -> {
                    val proxyYielder = ${ab.capitalizedPropertyName}PolymorphicIncludeYielderProxy()
                    val thatModelSerializer = ${associatedModelDataModel.modelClass}ToJsonSerializer(it)
                    proxyYielder.${associatedModelDataModel.modelClassDecapitalized} = thatModelSerializer
                    block.invoke(proxyYielder)
                    root.set("${ab.propertyName}", thatModelSerializer.serializeToNode())
                    return this
                }
                </#list>
                else -> {
                    root.set("${ab.propertyName}", null)
                    return this
                }
            }
        }
        root.set("${ab.propertyName}", null)
        return this
    }
    </#if>
    <#if ab.associationType == "HAS_MANY" || ab.associationType == "HAS_MANY_AS_POLYMORPHIC">
    fun include${ab.capitalizedPropertyName}(): ${modelClass}ToJsonSerializer {
        model.${ab.propertyName}?.let {
            val arrayNode = objectMapper.createArrayNode()
            it.forEach { thatModel ->
                arrayNode.add(${ab.associatedModelDataModel.modelClass}ToJsonSerializer(thatModel).serializeToNode())
            }
            root.set("${ab.propertyName}", arrayNode)
            return this
        }
        root.set("${ab.propertyName}", null)
        return this
    }

    inline fun include${ab.capitalizedPropertyName}(block: (${ab.associatedModelDataModel.modelClass}ToJsonSerializer)->Unit): ${modelClass}ToJsonSerializer {
        model.${ab.propertyName}?.let {
            val arrayNode = objectMapper.createArrayNode()
            it.forEach { thatModel ->
                val thatModelSerializer = ${ab.associatedModelDataModel.modelClass}ToJsonSerializer(thatModel)
                block.invoke(thatModelSerializer)
                arrayNode.add(thatModelSerializer.serializeToNode())
            }
            root.set("${ab.propertyName}", arrayNode)
            return this
        }
        root.set("${ab.propertyName}", null)
        return this
    }
    </#if>
    </#list>

    fun setApplicablePropertiesToNode(){
        whichPropertiesToSerialize.let {
            <#list fieldBeans as fieldBean>
            if (it.getValue(Properties.${fieldBean.property})) {
                <#if fieldBean.property == "createdAt" || fieldBean.property == "updatedAt" || fieldBean.nonNullableType == "Timestamp">
                root.set("${fieldBean.property}", model.${fieldBean.property}?.toString())
                <#else>
                root.set("${fieldBean.property}", model.${fieldBean.property})
                </#if>
            }
            </#list>
        }
    }

    fun serializeToNode(): IObjectNode{
        setApplicablePropertiesToNode()
        return root
    }

    fun serializeToString(): String {
        setApplicablePropertiesToNode()
        return root.toString()
    }

    fun set(key: String, value: String?) {
        root.set(key, value)
    }

    fun set(key: String, value: Int?) {
        root.set(key, value)
    }

    fun set(key: String, value: Long?) {
        root.set(key, value)
    }

    fun set(key: String, value: Boolean) {
        root.set(key, value)
    }

}
