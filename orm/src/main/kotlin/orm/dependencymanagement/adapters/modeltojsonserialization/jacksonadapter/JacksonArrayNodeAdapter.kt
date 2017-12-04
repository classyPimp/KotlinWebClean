package orm.dependencymanagement.adapters.modeltojsonserialization.jacksonadapter

import com.fasterxml.jackson.databind.node.ArrayNode
import orm.dependencymanagement.adapters.modeltojsonserialization.IArrayNode
import orm.dependencymanagement.adapters.modeltojsonserialization.IObjectNode

class JacksonArrayNodeAdapter(val arrayNode: ArrayNode): IArrayNode {

    override fun add(node: IObjectNode) {
        arrayNode.add((node as JacksonObjectNodeAdapter).objectNode)
    }

    override fun add(value: String?) {
        arrayNode.add(value)
    }

    override fun toString(): String {
        return arrayNode.toString()
    }

}