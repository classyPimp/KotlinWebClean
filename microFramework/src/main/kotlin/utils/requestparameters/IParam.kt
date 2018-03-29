package utils.requestparameters

import org.apache.commons.fileupload.FileItem
import java.io.File
import java.io.InputStream
import java.sql.Timestamp

interface IParam {

    fun get(key: String): IParam? {
        if (this.valueType != ParamTypesEnum.NODE_ROOT) {
            throw IllegalStateException()
        }
        return (this.value as MutableMap<String, IParam>)[key]
    }

    val string: String?

    val timestamp: Timestamp?

    val boolean: Boolean?

    val intList: MutableList<Int>?

    val stringList: MutableList<String>?

    val long: Long?

    val int: Int?

    fun long(): Long? {
        return this.value as Long?
    }

    fun fileItem(): FileItem? {
        return this.value as FileItem?
    }

    fun int(): Int? {
        return this.value as Int
    }

    fun tempFile(): File? {
        return this.value as File?
    }


    fun fileStream(): InputStream? {
        return this.value as InputStream?
    }

    fun paramList(): List<IParam>? {
        return this.value as MutableList<IParam>
    }

    fun nodeList(): List<IParam>? {
        return this.value as MutableList<IParam>
    }

    fun anyList(): List<Any> {
        return this.value as MutableList<Any>
    }

    fun boxedValueList(): MutableList<IParam>{
        if (this.valueType != ParamTypesEnum.BOXED_VALUE_LIST) {
            throw IllegalStateException()
        }
        return this.value as MutableList<IParam>
    }

    var value: Any?
    var valueType: ParamTypesEnum

}



