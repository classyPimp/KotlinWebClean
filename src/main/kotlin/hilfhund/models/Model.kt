package hilfhund.models

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper

/**
 * Created by Муса on 23.11.2017.
 */
class Model(json: JsonNode? = null) {
    var className: String? = null
    var decapitalizedClassName: String? = null
    var pluralClassName: String? = null
    var classNameLowerCase: String? = null

    var tableFields: MutableList<TableField> = mutableListOf()
    var associatedModels: MutableList<Associated> = mutableListOf()
    var errors: MutableMap<String, MutableList<String>>? = null

    var factoryName: String? = null
    var factoryPackage: String? = null

    var toJsonSerializerName: String? = null
    var jsonSerializerPackage: String? = null

    var reactComponentName: String? = null
    var reactComponentUriPartFromComponentsFolder: String? = null
    var cdToJsApplicationRootUriPart: String? = null

    var composerName: String? = null
    var composerPackage: String? = null
    var composerRoot: String? = null

    init {
        json?.let {
            className = it.get("className")?.asText()
            decapitalizedClassName = className?.let {
                if (it.length > 0) {
                    it[0].toLowerCase() + it.substring(1)
                } else {
                    ""
                }
            }
            classNameLowerCase = className?.toLowerCase()

            if (it.get("pluralClassName") != null) {
                pluralClassName = it.get("pluralClassName")?.asText()
            } else {
                pluralClassName = className + "s"
            }

            it.get("tableFields")?.toList()?.forEach {
                tableFields.add(TableField(it))
            }

            it.get("associatedModels")?.let {
                it.toList().forEach {
                    associatedModels.add(Associated(it))
                }
            }

            it.get("factoryName")?.asText()?.let {
                it.split('/').let {
                    if (it.size > 1) {
                        factoryPackage = "." + it.subList(0, it.size - 1).joinToString(".").toLowerCase()
                    }
                    factoryName = it.last()
                }
            }

            it.get("toJsonSerializerName")?.asText()?.let {
                it.split('/').let {
                    if (it.size > 1) {
                        jsonSerializerPackage = "." + it.subList(0, it.size - 1).joinToString(".").toLowerCase()
                    }
                    toJsonSerializerName = it.last()
                }
            }

            it.get("reactComponentName")?.asText()?.let {
                it.split('/').let {
                    reactComponentName = it.last()
                    reactComponentUriPartFromComponentsFolder = it.joinToString("/")
                    cdToJsApplicationRootUriPart = "../".repeat(it.size + 1)
                }
            }

            it.get("composerName")?.asText()?.let {
                it.split('/').let {
                    composerName = it.last()

                    if (it.size > 1) {
                        composerPackage = "." + it.subList(0, it.size - 1).joinToString(".").toLowerCase()
                    }

                    composerRoot = it.first()
                }
            }
        }
    }

    fun addError(field: String, error: String) {
        errors?.let {
            it[field] = it[field]?.also { it.add(error)} ?: mutableListOf(error)
            return
        }
        errors = mutableMapOf(field to mutableListOf(error))
        return
    }

    fun validate(){
        val errors = mutableListOf<String>()
        className ?: errors.add("name invalid")
        if (errors.isNotEmpty()) {
            this.errors = mutableMapOf("general" to errors)
        }
    }

    fun toJson(): String {
        ObjectMapper().createObjectNode().let {
            it.put("name", className)
            return it.toString()
        }
    }

    fun toJsonWithErrors():String {
        ObjectMapper().createObjectNode().let {
            it.set("errors", ObjectMapper().createObjectNode().also {
                errors?.forEach {
                    k,v ->
                    it.set(k, ObjectMapper().createArrayNode().also {
                        v.forEach {
                            errMessage->
                            it.add(errMessage)
                        }
                    })
                }
            })
            return it.toString()
        }

    }

}

class TableField(json: JsonNode? = null) {
    var name: String? = null
    var isPrimaryKey: Boolean? = false
    var type: String? = null
    var errors: MutableMap<String, MutableList<String>>? = null

    init {
        json?.let {
            name = it.get("name")?.asText()
            println("isPrimaryKey: ")
            println(it.get("isPrimaryKey")?.asBoolean())
            isPrimaryKey = it.get("isPrimaryKey")?.asBoolean()
            type = it.get("type")?.asText()
        }
    }

    fun validate(){
        val errors = mutableListOf<String>()
        name ?: errors.add("name invalid")
        type ?: errors.add("type invalid")
        if (errors.isNotEmpty()) {
            this.errors = mutableMapOf("general" to errors)
        }
    }
}

class Associated(json: JsonNode? = null) {

    companion object {
        val allowedAssociationNames = mutableListOf("HasOne", "BelongsTo", "HasMany")
    }

    var className: String? = null
    var decapitalizedClassName: String? = null
    var lowerCaseClassName: String? = null
    var associationType: String? = null
    var fieldOnThis: String? = null
    var fieldOnThat: String? = null
    var errors: MutableMap<String, MutableList<String>>? = null
    var pluralClassName: String? = null
    var property: String? = null

    init {
        json?.let {
            className = it.get("className")?.asText()
            decapitalizedClassName = className?.let {
                it[0].toLowerCase() + it.substring(1)
            }
            lowerCaseClassName = className?.toLowerCase()
            associationType = it.get("associationType")?.asText()
            fieldOnThis = it.get("fieldOnThis")?.asText()
            fieldOnThat = it.get("fieldOnThat")?.asText()
            if (it.get("pluralClassName") != null) {
                pluralClassName = it.get("pluralClassName")?.asText()
            } else {
                pluralClassName = className + "s"
            }
            property = it.get("property")?.asText()
        }
    }

    fun validate(){
        val errors = mutableListOf<String>()
        className ?: errors.add("class name invalid")
        associationType ?: errors.add("association type invalid")
        associationType?.let {
            if (!allowedAssociationNames.contains(it)) {
                errors.add("general: ${it} is not allowedAssociation name")
            }
        }
        fieldOnThat ?: errors.add("field on that invalid")
        fieldOnThis ?: errors.add("field on this invalid")
        if (errors.isNotEmpty()) {
            this.errors = mutableMapOf("general" to errors)
        }

    }
}