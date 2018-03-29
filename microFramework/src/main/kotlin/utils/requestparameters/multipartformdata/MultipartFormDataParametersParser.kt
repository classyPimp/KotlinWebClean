package utils.requestparameters.multipartformdata

import org.apache.commons.fileupload.FileItem
import org.apache.commons.fileupload.disk.DiskFileItemFactory
import org.apache.commons.fileupload.servlet.FileCleanerCleanup
import org.apache.commons.fileupload.servlet.ServletFileUpload
import utils.requestparameters.IParam
import utils.requestparameters.IRequestParametersParser
import java.io.File
import javax.servlet.http.HttpServletRequest

/**
 * Created by Муса on 10.11.2017.
 */
//parses multipart/formData whose fieldNames follow hash format naming
//(the way it understand Ruby, PHP, .NET)
class MultipartFormDataParametersParser: IRequestParametersParser {

    val multipartDiskFileRepository: File = File(System.getProperty("java.io.tmpdir"))
    var defaultFileFactory: DiskFileItemFactory? = null
    var defaultServletFileUpload: ServletFileUpload? = null

    override fun parse(request: HttpServletRequest): IParam {
        throw Exception("Unsupported")
    }

    override fun parse(request: HttpServletRequest, maxContentLength: Long?): IParam {
        val fileUpload: ServletFileUpload
        if (maxContentLength != null) {
            fileUpload = createServletFileUpload(maxContentLength, request)
        } else {
            fileUpload = defaultServletFileUploadOrInitialize(request)
        }
        fileUpload.parseRequest(request).let {
            return this.parse(it)
        }
    }

    fun defaultServletFileUploadOrInitialize(request: HttpServletRequest): ServletFileUpload {
        this.defaultServletFileUpload?.let {
            return it
        }
        createServletFileUpload(1024 * 1024 * 10, request).let {
            defaultServletFileUpload = it
            return it
        }
    }
    //TODO: move to static
    fun getDefaultFileItemFactoryOrInitialize(request: HttpServletRequest): DiskFileItemFactory {
        this.defaultFileFactory?.let {
            return it
        }
        DiskFileItemFactory().let {
            it.fileCleaningTracker = FileCleanerCleanup.getFileCleaningTracker(request.session.servletContext)
            it.repository = multipartDiskFileRepository
            it.sizeThreshold = 1024 * 5
            return it
        }
    }

    private fun createServletFileUpload(maxFileSize: Long, request: HttpServletRequest): ServletFileUpload {
        getDefaultFileItemFactoryOrInitialize(request).let {
            return ServletFileUpload(it).apply {
                sizeMax = maxFileSize
            }
        }
    }

    //entry point, returns rootNode
    fun parse(listToParse: List<FileItem>): IParam {
        val rootNode = MultipartFormDataParam.Companion.createNode()
        listToParse.forEach {
            parseFileItem(0, it, rootNode)
        }
        return rootNode
    }

    private fun parseFileItem(
            startIndex: Int,
            fileItem: FileItem,
            node: MultipartFormDataParam
    ) {
        val fieldName = fileItem.fieldName
        var startIndex = rewindIndexForwardSkippingBracketsIfNecessary(startIndex, fieldName)

        for (index in startIndex until fieldName.length) {
            //if met, first fieldNamePart is hash (map) entry
            if (fieldName[index] == '[') {
                addNestedUnsetParamParsingRemainingWithIt(startIndex, index, fileItem, node)
                return
            }
            //if met, current field name part shows that it's nested to previous
            if (fieldName[index] == ']') {
                //if met, (if the first char is digit) shows that this field name part shows that
                //it is array entry
                if (fieldNamePartRepresentsListIndex(startIndex, fieldName)) { //Character.isDigit(fieldName[startIndex])
                    //if met, ongoing conditions will decide which type to assign the entries
                    if (hasNextFieldNamePart(index, fieldName)) {
                        //if met, means that entry is multidimensional array, will be set as node of any
                        if (nextFieldNamePartIsArrayKey(index,fieldName)) {
                            val newNode = MultipartFormDataParam()
                            parseFileItem(rewindToNextFieldNamePart(index, fieldName), fileItem, newNode)
                            node.addToBoxedValueListOrInitializeAndAdd(
                                    newNode
                            )
                            return
                        }
                        //means that it is a nestable node
                        addToNodeAsNodeListItemParsingRemaining(startIndex, index, fileItem, node)
                        return

                    }
                    //adding as final value to array
                    addToThisNodeAsListItem(startIndex, index, fileItem, node)
                    return
                }
                if (hasNextFieldNamePart(index, fieldName)) {
                    //has next should add the node as ${fieldName.substring(startIndex until index)} and continue"
                    addNestedUnsetParamParsingRemainingWithIt(startIndex, index, fileItem, node)
                    return
                }
                //should add as final node ${fieldName.substring(startIndex until index)} as node
                addToNodeAsNestedBoxedValue(startIndex, index, fileItem, node)
                return
            }
        }
        //setting plain ${fieldName} to ${fileItem}
        node.getOrNewUnsetParam(fieldName).let {
            if (fileItem.isFormField) {
                it.setString(fileItem.string)
            } else {
                it.setFileItem(fileItem)
            }
        }
        return
    }

    private fun fieldNamePartRepresentsListIndex(index: Int, stringToTest: String): Boolean {
        return (Character.isDigit(stringToTest[index]))
    }

    private fun getIndexOfFisrtMetChar(char: Char, startIndex: Int, fieldName: String): Int {
        for (index in startIndex until fieldName.length) {
            if (fieldName[index] == char) {
                return index
            }
        }
        throw IllegalStateException()
    }

    private fun addToNodeAsNodeListItemParsingRemaining(startIndex: Int, index: Int, fileItem: FileItem, node: MultipartFormDataParam) {
        val listIndex = Integer.parseInt(
                fileItem.fieldName.substring(startIndex, getIndexOfFisrtMetChar(']', index, fileItem.fieldName))
        )
        val newNode = node.getMultiPartFormDataParamNodeFromNodeListOrAddNew(listIndex)
        val fieldName = fileItem.fieldName
        parseFileItem(rewindToNextFieldNamePart(index, fieldName), fileItem, newNode)
    }

    private fun addToThisNodeAsListItem(startIndex: Int, index: Int, fileItem: FileItem, node: MultipartFormDataParam) {
        node.addToAnyListFileItemUnboxingItIfNeccessary(fileItem)
    }

    private fun addToNodeAsNestedBoxedValue(startIndex: Int, index: Int, fileItem: FileItem, node: MultipartFormDataParam) {
        val newNode = node.getOrNewUnsetMultiPartFormDataParam(fileItem.fieldName.substring(startIndex, index))
        newNode.setBoxedValue(fileItem)
    }

    private fun addNestedUnsetParamParsingRemainingWithIt(startIndex: Int, index: Int, fileItem: FileItem, node: MultipartFormDataParam) {
        val newNode = node.getOrNewUnsetMultiPartFormDataParam(fileItem.fieldName.substring(startIndex, index))
        parseFileItem(index, fileItem, newNode)
    }

    private fun rewindIndexForwardSkippingBracketsIfNecessary(startIndex: Int, fieldName: String): Int {
        if (fieldName[startIndex] == '[') {
            return startIndex + 1
        }
        if (fieldName[startIndex] == ']') {
            return startIndex + 2
        }
        return startIndex
    }

    private fun rewindToNextFieldNamePart(currentIndex: Int, fieldName: String): Int {
        for (index in currentIndex..fieldName.length - 1) {
            if (fieldName[index] == '[') {
                return index + 1
            }
        }
        throw IllegalStateException()
    }


    private fun hasNextFieldNamePart(startIndex: Int, string: String): Boolean {
        if (startIndex >= string.length) {
            return false
        } else {
            for (index in startIndex until string.length) {
                if (string[index] == '[') {
                    return true
                }
            }
            return false
        }
    }

    private fun nextFieldNamePartIsArrayKey(startIndex: Int, string: String): Boolean {
        for (index in startIndex..string.length - 1) {
            if (string[index] == '[') {
                return (Character.isDigit(string[index + 1]))
            }
        }
        return false
    }

}