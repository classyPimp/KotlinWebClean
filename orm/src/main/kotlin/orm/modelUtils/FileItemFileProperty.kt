package orm.modelUtils

import org.apache.commons.fileupload.FileItem
import java.io.*
import java.nio.file.Files
import java.nio.file.Paths

/**
 * Created by Муса on 15.11.2017.
 */
abstract class FileItemFileProperty {



    abstract val baseUploadPath: String
        get
    abstract val modelName: String
        get
    abstract val propertyName: String
        get

    abstract val fileNameOnProperty: String?
        get

    abstract val modelId: Long?
        get

    enum class OperationType {
        DELETE,
        ASSIGN,
        ASSIGN_MODEL_NOT_PERSISTED,
        DELETE_MODEL_NOT_PERSISTED,
        UNSET
    }

    enum class OperatesOnType {
        FILE_ITEM,
        FILE
    }

    var fileItem: FileItem? = null
    var file: File? = null
    var transactionDir: File? = null
    var transactionOriginalFile: File? = null
    var operationType: OperationType = OperationType.UNSET
    var operatesOn: OperatesOnType = OperatesOnType.FILE_ITEM


    abstract fun validateFile(uploadedFile: File): Boolean

    abstract fun handlePropertiesOnAssign(fileItem: FileItem)

    abstract fun handlePropertiesOnAssign(file: File)

    abstract fun preprocessFile(file: File)

    abstract fun handlePropertiesOnDelete()

    private fun reset(){
        transactionDir = null
        operationType = OperationType.UNSET
        fileItem = null
    }

    fun assign(file: File?) {
        operatesOn = OperatesOnType.FILE
        if (file == null) {
            return
        }
        if (modelId != null) {
            operationType = OperationType.ASSIGN
            assignWhenIdAvailable(file)
        } else {
            this.file = file
            operationType = OperationType.ASSIGN_MODEL_NOT_PERSISTED
        }
        //handlePropertiesOnAssign(file)
    }

    fun assign(fileItem: FileItem?) {
        if (fileItem == null) {
            return
        }
        if (modelId != null) {
            operationType = OperationType.ASSIGN
            assignWhenIdAvailable(fileItem)
        } else {
            this.fileItem = fileItem
            operationType = OperationType.ASSIGN_MODEL_NOT_PERSISTED
        }

        handlePropertiesOnAssign(fileItem)
    }

    fun assignWhenIdAvailable(file: File) {
        val fileName = file.name

        this.transactionDir = createTransactionDir()
        this.transactionOriginalFile = createTransactionOriginalFile(fileName)

        copyInputStreamToOutputStream(FileInputStream(file), FileOutputStream(transactionOriginalFile))

        try {
            if (!validateFile(transactionOriginalFile!!)) {
                destroyTransactionDir()
                operationType = OperationType.UNSET
                return
            }
        } catch (error: java.lang.Exception) {
            destroyTransactionDir()
            operationType = OperationType.UNSET
            throw error
        }

        try {
            preprocessFile(transactionOriginalFile!!)
        } catch(error: Exception) {
            destroyTransactionDir()
            throw error
        }

    }

    fun assignWhenIdAvailable(fileItem: FileItem){
        val fileName = constructFileNameOrDefault(fileItem)

        this.transactionDir = createTransactionDir()
        this.transactionOriginalFile = createTransactionOriginalFile(fileName)

        try {
            fileItem.write(this.transactionOriginalFile!!)
        } finally {
            fileItem.delete()
        }

        try {
            if (!validateFile(transactionOriginalFile!!)) {
                destroyTransactionDir()
                operationType = OperationType.UNSET
                return
            }
        } catch (error: java.lang.Exception) {
            destroyTransactionDir()
            operationType = OperationType.UNSET
            throw error
        }

        try {
            preprocessFile(transactionOriginalFile!!)
        } catch(error: Exception) {
            destroyTransactionDir()
            throw error
        }

    }

    fun delete(){
        if (modelId == null || fileNameOnProperty == null) {
            return
        }
        this.operationType = OperationType.DELETE
        handlePropertiesOnDelete()
    }

    fun createTransactionDir(): File {
        File("${prepareRepositoryPath()}/transaction").let {
            if (it.exists()) {
                it.deleteRecursively()
            }
            it.mkdirs()
            return it
        }
    }

    fun createEmptyFileWherePreprocessedFileWillBeStored(namespace: String): File {
        val fileName = transactionOriginalFile!!.name
        val fileToReturn = File(transactionDir, "/$namespace/${fileName}")
        if (!fileToReturn.parentFile.exists()) {
            fileToReturn.parentFile.mkdirs()
        }
        if (!fileToReturn.exists()) {
            fileToReturn.createNewFile()
        }
        return fileToReturn
    }

    private fun constructFileNameOrDefault(fileItem: FileItem): String {
        return fileItem.name ?: "noname"
    }

    private fun destroyTransactionDir() {
        transactionDir?.let {
            if (it.exists()) {
                it.deleteRecursively()
            }
        }
    }

    private fun createTransactionOriginalFile(fileName: String): File {

        val original = File(this.transactionDir!!, "/original")
        if (!original.exists()) {
            original.mkdirs()
        }

        return File(original, fileName).also {
            it.createNewFile()
        }
    }

    fun getFile(nameSpace: String = "original"): File? {
        if (fileNameOnProperty == null) {
            return null
        }
        val file = File("${prepareRepositoryPath()}/$nameSpace/$fileNameOnProperty")
        if (file.exists()) {
            return file
        } else {
            return null
        }
    }

    fun finalizeOperation() {
        when (operationType) {
            OperationType.ASSIGN -> {
                finalizeAssignment()
            }
            OperationType.DELETE -> {
                finalizeDeletion()
            }
            OperationType.ASSIGN_MODEL_NOT_PERSISTED -> {
                when (operatesOn) {
                    OperatesOnType.FILE_ITEM -> {
                        this.fileItem?.let {
                            assignWhenIdAvailable(it)
                            afterAssignedToUnpersitedModelAndItsSaved(transactionOriginalFile!!)
                            finalizeAssignment()
                        } ?: throw IllegalStateException("cant finalize file assignment")
                    }
                    OperatesOnType.FILE -> {
                        this.file?.let {
                            assignWhenIdAvailable(it)
                            afterAssignedToUnpersitedModelAndItsSaved(transactionOriginalFile!!)
                            finalizeAssignment()
                        } ?: throw IllegalStateException("cant finalize file assignment")
                    }
                }

            }
            OperationType.UNSET -> {

            }
            else -> {

            }
        }
    }

    abstract fun afterAssignedToUnpersitedModelAndItsSaved(file: File)

    fun finalizeDeletion() {
        deletePereviousFilesIfExist()
    }

    fun finalizeAssignment() {
        transactionOriginalFile?.let {
            deletePereviousFilesIfExist()
            moveTransactionDirToParentDir()
            destroyTransactionDir()
        }
    }

    private fun moveTransactionDirToParentDir() {
        this.transactionDir?.let {
            val destination = it.parentFile.toString()
            it.listFiles().forEach {
                move(destination, it)
            }
        }
    }

    fun move(depth: String, file: File) {
        if (file.isDirectory) {
            file.listFiles().forEach {
                move(depth + "/${file.name}", it)
            }
        } else if (file.isFile) {
            File(depth).also {
                if (!it.exists()) {
                    it.mkdirs()
                }
            }
            file.renameTo(File(depth + "/${file.name}"))
        }
    }

    fun deletePereviousFilesIfExist(){
        val folder = File(prepareRepositoryPath())
        folder.listFiles()?.forEach {
            if (it.isDirectory) {
                if (it.name != "transaction") {
                    if (!it.deleteRecursively()) {
                        throw IOException("when finalizing file transaction, could not delete old ones")
                    }
                }
            }
        }
    }

    fun prepareRepositoryPath(): String{
        return "${prepareBasePath()}/${prepareIdPath()}"
    }

    fun prepareBasePath(): String {
        return "$baseUploadPath/$modelName/$propertyName"
    }

    fun prepareIdPath(): String {
        val length = "$modelId".length
        val firstMissingCharsSize = 9 - length

        val firstChars = CharArray(firstMissingCharsSize) {
            '0'
        }
        val stringWithoutDelimeters = "${String(firstChars)}${modelId}"

        val finalString = StringBuilder()

        for (index in 0 until 9) {
            finalString.append(stringWithoutDelimeters[index])
            if ((index + 1) % 3 == 0 && (index + 1 != 9)) {
                finalString.append('/')
                continue
            }
        }
        return finalString.toString()
    }

    private fun copyInputStreamToOutputStream(`in`: InputStream,
                                              out: OutputStream) {
        try {
            try {
                val buffer = ByteArray(1024)
                var n: Int

                n = `in`.read(buffer)
                while (n != -1) {
                    out.write(buffer, 0, n)
                    n = `in`.read(buffer)
                }

            } finally {
                out.close()
            }
        } finally {
            `in`.close()
        }
    }

}