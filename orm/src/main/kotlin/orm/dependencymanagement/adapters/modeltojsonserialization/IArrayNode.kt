package orm.dependencymanagement.adapters.modeltojsonserialization

interface IArrayNode {

    abstract fun add(node: IObjectNode)

    abstract fun add(value: String?)

    override abstract fun toString(): String

}