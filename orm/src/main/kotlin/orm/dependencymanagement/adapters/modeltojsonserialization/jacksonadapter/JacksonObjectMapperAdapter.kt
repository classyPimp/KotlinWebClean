package orm.dependencymanagement.adapters.modeltojsonserialization.jacksonadapter

import com.fasterxml.jackson.databind.ObjectMapper
import orm.dependencymanagement.adapters.modeltojsonserialization.IArrayNode
import orm.dependencymanagement.adapters.modeltojsonserialization.IObjectNode
import orm.dependencymanagement.adapters.modeltojsonserialization.IToJsonSerializerObjectMapperAdapter

/**
 * Created by Муса on 21.11.2017.
 */
class JacksonObjectMapperAdapter : IToJsonSerializerObjectMapperAdapter {
    companion object {
        val objectMapper = ObjectMapper()
    }

    override fun createArrayNode(): IArrayNode {
        return JacksonArrayNodeAdapter(objectMapper.createArrayNode())
    }

    override fun createObjectNode(): IObjectNode {
        return JacksonObjectNodeAdapter(objectMapper.createObjectNode())
    }
}

