package utils.requestparameters.json

import com.fasterxml.jackson.databind.JsonNode
import utils.requestparameters.IParam
import utils.requestparameters.ParamTypesEnum

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

    override val string: String?
        get() {
            return jsonNode.textValue()
        }

    override fun int(): Int? {
        return jsonNode.intValue()
    }

    override fun long(): Long? {
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

}