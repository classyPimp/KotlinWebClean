package utils.requestparameters.json

import com.fasterxml.jackson.databind.JsonNode
import utils.requestparameters.IParam
import utils.requestparameters.ParamTypesEnum
import java.sql.Timestamp
import jdk.nashorn.internal.objects.NativeDate.getTime
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatterBuilder
import java.time.format.DateTimeParseException
import java.time.format.ResolverStyle


/**
 * Created by Муса on 14.11.2017.
 */
class JsonParam(val jsonNode: JsonNode): IParam {

    var listIsCached = false

    override var valueType = ParamTypesEnum.UNSET
    override var value: Any?
        get() {
            return jsonNode
        }
        set(value) {}

    override val intList: MutableList<Int>
        get() {
            if (listIsCached) {
                return this.value as MutableList<Int>
            } else {
                listIsCached = true
                val intList = mutableListOf<Int>()
                jsonNode.forEach {
                    intList.add(it.intValue())
                }
                return intList
            }
        }

    override val timestamp: Timestamp?
        get() {
            val textValue = jsonNode.textValue()
            textValue ?: return null
            try {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS")
                val parsedDate = dateFormat.parse(textValue)
                val timestamp = java.sql.Timestamp(parsedDate.time)
                return timestamp
            } catch (error: Exception) {
                println("catched when getting timestamp: ${error.message}")
                return null
            }
        }

    override val string: String?
        get() {
            return jsonNode.textValue()
        }

    override val boolean: Boolean?
        get() {
            return jsonNode.booleanValue()
        }

    override val long: Long?
        get() {
            if (jsonNode.isNull) {
                println("is null")
                return null
            }
            println("long is ${jsonNode.longValue()}")
            return jsonNode.longValue()
        }

    override val int: Int?
        get() {
            if (jsonNode.isNull) {
                return null
            }
            return jsonNode.intValue()
        }

    override fun int(): Int? {
        return jsonNode.intValue()
    }

    override fun long(): Long? {
        if (jsonNode.isNull) {
            return null
        }
        return jsonNode.longValue()
    }

    override val stringList: MutableList<String>
        get() {
            if (listIsCached) {
                return this.value as MutableList<String>
            } else {
                listIsCached = true
                mutableListOf<String>().let {
                    jsonNode.forEach {
                        node ->
                        it.add(node.textValue())
                    }
                    return it
                }
            }
        }

    override fun get(key: String): IParam? {
        jsonNode.get(key)?.let {
            return JsonParam(it)
        }
        return null
    }

    override fun paramList(): List<IParam>? {
        val listToReturn = mutableListOf<IParam>()
        jsonNode.toMutableList().forEach {
            listToReturn.add(JsonParam(it))
        }
        return listToReturn
    }

}