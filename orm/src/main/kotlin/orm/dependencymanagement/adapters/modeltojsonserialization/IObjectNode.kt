package orm.dependencymanagement.adapters.modeltojsonserialization

interface IObjectNode {

    fun set(key: String, arrayNode: IArrayNode)

    fun set(key: String, node: IObjectNode)

    fun set(key: String, value: String?)

    fun set(key: String, value: Boolean?)

    fun set(key: String, value: Int?)

    fun set(key: String, value: Long?)

    fun set(key: String, value: Nothing?)

    override fun toString(): String

}


