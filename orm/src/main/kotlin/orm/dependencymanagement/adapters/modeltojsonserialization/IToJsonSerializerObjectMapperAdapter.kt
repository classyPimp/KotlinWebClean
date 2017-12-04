package orm.dependencymanagement.adapters.modeltojsonserialization

interface IToJsonSerializerObjectMapperAdapter {

    fun createArrayNode(): IArrayNode

    fun createObjectNode(): IObjectNode

}