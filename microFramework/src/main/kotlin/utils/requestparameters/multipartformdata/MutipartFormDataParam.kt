package utils.requestparameters.multipartformdata

import org.apache.commons.fileupload.FileItem
import utils.requestparameters.IParam
import utils.requestparameters.ParamInParseModeTrait
import utils.requestparameters.ParamTypesEnum

class MultipartFormDataParam : IParam, ParamInParseModeTrait {

    override var value: Any? = null
    override var valueType: ParamTypesEnum = ParamTypesEnum.UNSET
    var listIsCached = false

    override val string: String?
        get() {
            return this.value as String?
        }

    override val boolean: Boolean?
        get() {
            val stringValue = this.value as String?
            if (stringValue != null) {
                return (stringValue == "true")
            } else {
                return null
            }
        }

    override val intList: MutableList<Int>?
        get() {
            if (this.valueType != ParamTypesEnum.ANY_LIST) {
                throw IllegalStateException("param does not contain list as value")
            }
            if (listIsCached) {
                return this.value as MutableList<Int>
            } else {
                val intList = mutableListOf<Int>()
                (this.value as List<String>).forEach {
                    intList.add(Integer.parseInt(it))
                }
                this.listIsCached = true
                this.value = intList
                return intList
            }
        }

    override val stringList: MutableList<String>?
        get() {
            if (this.valueType != ParamTypesEnum.ANY_LIST) {
                throw IllegalStateException("param does not contain list as value")
            }
            return this.value as MutableList<String>
        }


    override fun getOrNewUnsetParam(key: String): ParamInParseModeTrait {
        val map = getOrInitializeParamMap()
        map[key]?.let {
            return it
        }
        MultipartFormDataParam().let {
            map[key] = it
            return it
        }
    }

    override fun getNodeFromNodeListOrNewAt(index: Int): IParam {
        return this.getMultiPartFormDataParamNodeFromNodeListOrAddNew(index)
    }

    fun getOrNewUnsetMultipartFormDataParam(key: String): MultipartFormDataParam {
        return this.getOrNewUnsetParam(key) as MultipartFormDataParam
    }

    fun addToAnyListFileItemUnboxingItIfNeccessary(fileItem: FileItem) {
        if (fileItem.isFormField) {
            addToAnyListOrInitializeAndAdd(fileItem.string)
        } else {
            addToAnyListOrInitializeAndAdd(fileItem)
        }
    }

    fun getMultiPartFormDataParamNodeFromNodeListOrAddNew(index: Int): MultipartFormDataParam {
        this.nodeListOrInitialize<MultipartFormDataParam>().let {
            it.elementAtOrNull(index)?.let {
                return it
            }
            MultipartFormDataParam.Companion.createNode().let {
                param ->
                it.add(param)
                return param
            }
        }
    }

    fun getOrNewUnsetMultiPartFormDataParam(key: String): MultipartFormDataParam {
        getOrInitializeParamMap().let {
            it[key]?.let {
                return it as MultipartFormDataParam
            }

            MultipartFormDataParam().let {
                param ->
                it[key] = param
                return param
            }
        }
    }

    fun setBoxedValue(fileItem: FileItem) {
        if (fileItem.isFormField) {
            this.valueType = ParamTypesEnum.STRING
            this.value = fileItem.string
        } else {
            this.valueType = ParamTypesEnum.BOXED_VALUE
            this.value = fileItem
        }
    }

    companion object {
        fun createNode(): MultipartFormDataParam {
            MultipartFormDataParam().let {
                it.getOrInitializeParamMap()
                return it
            }
        }
        fun createUnset(): MultipartFormDataParam {
            return MultipartFormDataParam()
        }
    }


}