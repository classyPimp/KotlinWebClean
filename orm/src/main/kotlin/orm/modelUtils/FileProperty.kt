package orm.modelUtils

import org.apache.commons.fileupload.FileItem
import org.apache.commons.fileupload.disk.DiskFileItem
import java.io.*
import java.nio.file.Files

/**
 * Created by Муса on 26.01.2018.
 */
abstract class FileProperty {

    private var operationType = OperationTypes.DO_NOTHING
    private var targetFileName: String? = null
    private var fileItemBeingProcessed: FileItem? = null

    abstract val modelId: Long?
    abstract var maxAllowedSize: Long

    abstract val baseUploadPath: String
    abstract val modelName: String
    abstract val propertyName: String

    abstract val fileNameOnModel: String?

    abstract fun validateFile(file: File): Boolean
    abstract fun onFileAssigned(file: File, targetFileName: String?)
    abstract fun preprocessFile(file: File)
    abstract fun onFileDelete()

    var transactionDirForProcessedFiles: File? = null
    var transactionOriginalFile: File? = null

    enum class OperationTypes {
        ASSIGN,
        DELETE,
        DO_NOTHING
    }

    fun getFileItself(namespace: String = "original"): File? {
        if (fileNameOnModel == null) {
            return null
        }
        val fileToReturn = File(prepareRepositoryPath() + "/${namespace}/" + fileNameOnModel)
        if (fileToReturn.exists()) {
            return fileToReturn
        } else {
            return null
        }
    }

    fun assign(fileItem: FileItem?) {
        if (fileItem == null) {
            return
        }

        val fileSize = fileItem.size
        if (fileSize > maxAllowedSize){
            return
        }

        operationType = OperationTypes.ASSIGN

        targetFileName = fileItem.name
        fileItemBeingProcessed = fileItem

        createTransactionOriginalFile(fileItem)

        onFileAssigned(transactionOriginalFile!!, targetFileName)

        if (!validateFile(transactionOriginalFile!!)) {
            clearTransactionalFiles()
            operationType = OperationTypes.DO_NOTHING
            return
        }

        createTransactionDirForProcessedFiles()

        transactionOriginalFile!!.name
    }

    fun assign(file: File?) {
        if (file == null) {
            return
        }

        val fileSize = file.length()
        if (fileSize > maxAllowedSize){
            return
        }

        operationType = OperationTypes.ASSIGN

        targetFileName = file.name

        createTransactionOriginalFile(file)

        onFileAssigned(transactionOriginalFile!!, targetFileName)

        if (!validateFile(transactionOriginalFile!!)) {
            clearTransactionalFiles()
            operationType = OperationTypes.DO_NOTHING
            return
        }

        createTransactionDirForProcessedFiles()

        transactionOriginalFile!!.name
    }

    fun delete() {
        operationType = OperationTypes.DELETE
        onFileDelete()
    }


    fun finalizeOperation() {
        when (operationType) {
            OperationTypes.ASSIGN -> {
                finalizeAssignOperation()
            }
            OperationTypes.DELETE -> {
                finalizeDeleteOperation()
            }
            OperationTypes.DO_NOTHING -> {

            }
        }
    }

    private fun finalizeAssignOperation() {
        if (modelId == null) {
            clearTransactionalFiles()
            return
        }
        preprocessFile(transactionOriginalFile!!)
        deletePereviousFilesIfExist()
        createTargetDirWhereFilesWillBeStored()
        moveTransactionalProcessedFilesToTargetDir()
        moveOriginalTransactionFileToTargetDir()
        clearTransactionalFiles()
    }

    private fun finalizeDeleteOperation(){
        deletePereviousFilesIfExist()
    }


    private fun createTransactionOriginalFile(fileItem: FileItem) {
        if (fileItem.isInMemory) {
            try {
                val tempFile = Files.createTempFile("${System.currentTimeMillis()}-", ".tmp").toFile()
                fileItem.write(tempFile)
                transactionOriginalFile = tempFile

            } catch (error: Exception) {
                transactionOriginalFile?.delete()
            } finally {
                fileItem.delete()
            }
        } else {
            val diskFileItem = fileItem as DiskFileItem
            transactionOriginalFile = diskFileItem.storeLocation
        }
    }

    private fun createTransactionOriginalFile(file: File) {
        val tempFile = Files.createTempFile("${System.currentTimeMillis()}-", ".tmp").toFile()
        val inputStream = FileInputStream(file)
        val outputStream = FileOutputStream(tempFile)
        copyInputStreamToOutputStream(inputStream, outputStream)
    }

    private fun createTransactionDirForProcessedFiles() {
        transactionDirForProcessedFiles = Files.createTempDirectory("${System.currentTimeMillis()}-").toFile()
    }

    fun createEmptyFileWherePreprocessedFileWillBeStored(namespace: String): File {
        val fileName = transactionOriginalFile!!.name
        val fileToReturn = File(transactionDirForProcessedFiles, "/$namespace/${fileName}")
        if (!fileToReturn.parentFile.exists()) {
            fileToReturn.parentFile.mkdirs()
        }
        if (!fileToReturn.exists()) {
            fileToReturn.createNewFile()
        }
        return fileToReturn
    }

    fun createTargetDirWhereFilesWillBeStored(){
        File(prepareRepositoryPath())?.let {
            if (!it.exists()) {
                it.mkdirs()
            }
        }
    }

    fun clearTransactionalFiles() {
        transactionOriginalFile?.let {
            it.delete()
        }
        transactionDirForProcessedFiles?.let {
            if (it.exists()) {
                it.deleteRecursively()
            }
        }
        fileItemBeingProcessed?.let {
            it.delete()
        }
    }

    private fun deletePereviousFilesIfExist(){
        val folder = File(prepareRepositoryPath())
        folder.listFiles()?.forEach {
            if (it.isDirectory) {
                it.deleteRecursively()
            } else if (it.isFile) {
                it.delete()
            }
        }
    }

    private fun prepareRepositoryPath(): String{
        return "${prepareBasePath()}/${prepareIdPath()}"
    }

    private fun prepareBasePath(): String {
        return "$baseUploadPath/$modelName/$propertyName"
    }

    private fun prepareIdPath(): String {
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

    fun moveOriginalTransactionFileToTargetDir() {
        val targetFile = File("${prepareRepositoryPath()}/original/${targetFileName}")
        val parent = targetFile.parentFile
        if (!parent.exists()) {
            parent.mkdirs()
        }
        if (!targetFile.exists()) {
            //targetFile.createNewFile()
        }
        println("moving")
        println(transactionOriginalFile!!.absolutePath)
        transactionOriginalFile!!.renameTo(targetFile)
    }

    fun moveTransactionalProcessedFilesToTargetDir(){
        val targetFile = File(prepareRepositoryPath()).also {
            if (!it.exists()) {
               it.mkdirs()
            }
        }.toString()
        transactionDirForProcessedFiles!!.listFiles()?.forEach {
            moveFile(targetFile, it)
        }
    }

    fun moveFile(depth: String, file: File) {
        if (file.isDirectory) {
            file.listFiles().forEach {
                moveFile(depth + "/${file.name}", it)
            }
        } else if (file.isFile) {
            File(depth).also {
                if (!it.exists()) {
                    it.mkdirs()
                }
            }
            val fileToMoveTo = File(depth + "/${file.name}").also {
                it.createNewFile()
            }
            file.renameTo(fileToMoveTo)
        }
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
