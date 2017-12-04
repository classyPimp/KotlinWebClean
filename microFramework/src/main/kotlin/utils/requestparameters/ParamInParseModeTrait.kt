package utils.requestparameters

import org.apache.commons.fileupload.FileItem
import java.io.File
import java.io.InputStream

/**
 * Created by Муса on 14.11.2017.
 */
interface ParamInParseModeTrait : IParam {

    fun setString(value: Any?) {
        this.value = value as String?
    }

    fun setFileItem(fileItem: FileItem) {
        this.value = fileItem
    }

    fun setLong(value: Any?) {
        this.value = value as Long?
    }

    fun setInt(value: Any?) {
        this.value = value as Int
    }

    fun setTempFile(value: Any?) {
        this.value = value as File?
    }

    fun setFileStream(value: Any?) {
        this.value = value as InputStream
    }

    fun getOrInitializeParamMap(): MutableMap<String, ParamInParseModeTrait> {
        when (this.valueType) {
            ParamTypesEnum.UNSET -> {
                this.valueType = ParamTypesEnum.NODE_ROOT
                mutableMapOf<String, ParamInParseModeTrait>().let {
                    this.value = it
                    return it
                }
            }
            ParamTypesEnum.NODE_ROOT -> {
                return this.value as MutableMap<String, ParamInParseModeTrait>
            }
            else -> {
                throw IllegalStateException()
            }
        }
    }

    fun set(key: String, value: ParamInParseModeTrait) {
        if (this.valueType != ParamTypesEnum.NODE_ROOT) {
            throw IllegalStateException()
        }
        this.value?.let {
            this.map()[key] = value
            return
        }
        this.value = mutableMapOf(key to value)
    }

    fun setAnyValue(value: Any?) {
        this.value = value
    }

    fun addToAnyListOrInitializeAndAdd(value: Any) {
        when (this.valueType) {
            ParamTypesEnum.UNSET ->{
                this.valueType = ParamTypesEnum.ANY_LIST
                mutableListOf(value).let {
                    this.value = it
                }
            }
            ParamTypesEnum.ANY_LIST -> {
                (this.value as MutableList<Any>).add(value)
            }
            else -> {
                throw IllegalStateException()
            }
        }
    }

    fun addToBoxedValueListOrInitializeAndAdd(value: IParam) {
        when (this.valueType) {
            ParamTypesEnum.UNSET ->{
                this.valueType = ParamTypesEnum.BOXED_VALUE_LIST
                mutableListOf(value).let {
                    this.value = it
                }
            }
            ParamTypesEnum.BOXED_VALUE_LIST -> {
                (this.value as MutableList<IParam>).add(value)
            }
            else -> {
                throw IllegalStateException()
            }
        }
    }

    fun <T : ParamInParseModeTrait>nodeListOrInitialize(): MutableList<T> {
        when (this.valueType) {
            ParamTypesEnum.NODE_LIST -> {
                return this.value as MutableList<T>
            }
            ParamTypesEnum.UNSET -> {
                this.valueType = ParamTypesEnum.NODE_LIST
                mutableListOf<T>().let {
                    this.value = it
                    return it
                }
            }
            else -> {
                throw IllegalStateException("${this.valueType}")
            }
        }
    }

    abstract fun getNodeFromNodeListOrNewAt(index: Int): IParam

    abstract fun getOrNewUnsetParam(key: String): IParam

    fun map(): MutableMap<String, ParamInParseModeTrait> {
        this.value?.let {
            return it as MutableMap<String, ParamInParseModeTrait>
        } ?: throw NullPointerException()
    }

}