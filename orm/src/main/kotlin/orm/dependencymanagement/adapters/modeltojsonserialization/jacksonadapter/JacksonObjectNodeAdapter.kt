package orm.dependencymanagement.adapters.modeltojsonserialization.jacksonadapter

import com.fasterxml.jackson.databind.node.ObjectNode
import orm.dependencymanagement.adapters.modeltojsonserialization.IArrayNode
import orm.dependencymanagement.adapters.modeltojsonserialization.IObjectNode

class JacksonObjectNodeAdapter(val objectNode: ObjectNode): IObjectNode
{
    override fun set(key: String, node: IObjectNode) {
        objectNode.set(key, (node as JacksonObjectNodeAdapter).objectNode)
    }

    override fun set(key: String, arrayNode: IArrayNode) {
        objectNode.set(key, (arrayNode as JacksonArrayNodeAdapter).arrayNode)
    }

    override fun set(key: String, value: Int?) {
        objectNode.put(key, value)
    }

    override fun set(key: String, value: Long?) {
        objectNode.put(key, value)
    }

    override fun set(key: String, value: Nothing?) {
        objectNode.set(key, null)
    }

    override fun set(key: String, value: Boolean?) {
        objectNode.put(key, value)
    }

    override fun set(key: String, value: String?) {
        objectNode.put(key, value)
    }

    override fun toString(): String {
        return objectNode.toString()
    }
}