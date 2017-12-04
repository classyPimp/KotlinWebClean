package hilfhund.models

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode

/**
 * Created by Муса on 22.11.2017.
 */
class Migration(val json: JsonNode? = null) {
    var name: String? = null
    var options: String? = null
    var message: String? = null
    var errors: MutableMap<String, MutableList<String>>? = null
    var migrations: MutableList<String>? = null

    init {
        json?.let {
            it.get("migration")?.let {
                name = it.get("name")?.asText()
                options = it.get("options")?.asText()
            }
        }
    }

    fun isValid(): Boolean {
        var noErrors = true
        name.let {
            if (it == null) {
                addError("name", "invalid")
                noErrors = false
            }
        }
        options.let {
            if (it == null) {
                addError("options", "invalid")
                noErrors = false
            }
        }
        return noErrors
    }

    fun addError(nameSpace: String, error: String) {
        errors?.let {
            it[nameSpace] = it[nameSpace]?.also {it.add(error)} ?: mutableListOf(error)
            return
        }
        errors = mutableMapOf(nameSpace to mutableListOf<String>(error))
        return
    }

    companion object ToJson {
        fun createErrors(migration: Migration): JsonNode{
            return createSuccess(migration).also {
                it.set("errors", errorsToNode(migration))
            }
        }

        fun createSuccess(migration: Migration): ObjectNode {
            return ObjectMapper().createObjectNode().also {
                it.put("name", migration.name)
                it.put("options", migration.options)

                migration.migrations?.let { _ ->
                    it.set(
                            "migrations",
                            ObjectMapper().createArrayNode().also {
                                migration.migrations?.forEach { migrationFileName ->
                                    it.add(migrationFileName)
                                }
                            }
                    )
                }
                migration.message?.let {msg ->
                    it.put("message", msg)
                }
            }
        }

        private fun errorsToNode(migration: Migration): ObjectNode {
            return ObjectMapper().createObjectNode().also {
                migration.errors!!.forEach { k, v ->
                    val errorsAr = ObjectMapper().createArrayNode()
                    v.forEach {
                        er ->
                        errorsAr.add(er)
                    }
                    it.set(k, errorsAr)
                }
            }
        }
    }

}