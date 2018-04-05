package orm.services

import orm.dependencymanagement.OrmDependenciesManager
import orm.dependencymanagement.adapters.modeltojsonserialization.IArrayNode
import orm.dependencymanagement.adapters.modeltojsonserialization.IObjectNode

/**
 * Created by Муса on 04.04.2018.
 */
class EmptyToJsonSerializer {

    companion object {
        val objectMapper = OrmDependenciesManager.provider.jsonObjectMapper

        inline fun serialize(block: (EmptyToJsonSerializer)->Unit): IArrayNode {
            val root = objectMapper.createArrayNode()
            block(EmptyToJsonSerializer())
            return root
        }

    }

    val root = objectMapper.createObjectNode()

    fun serializeToNode(): IObjectNode {
        return root
    }

    fun serializeToString(): String {
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