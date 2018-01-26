package orm.modelUtils

import org.apache.commons.fileupload.FileItem
import org.apache.commons.fileupload.disk.DiskFileItem
import java.io.File
import java.io.IOException
import java.nio.file.Files

/**
 * Created by Муса on 26.01.2018.
 */
abstract class FileProperty {

    var operationType = OperationTypes.DO_NOTHING

    abstract val modelId: Long?
    abstract val maxAllowedSize: Long
    abstract var fileName: String?
    abstract var fileSize: Long?

    abstract val baseUploadPath: String
    abstract val modelName: String
    abstract val propertyName: String

    abstract fun validateFile(file: File): Boolean
    abstract fun onFileAssigned(file: File)
    abstract fun preprocessFile(file: File)
    abstract fun onFileDelete()

    var transactionDirForProcessedFiles: File? = null
    var transactionOriginalFile: File? = null

    enum class OperationTypes {
        ASSIGN,
        DELETE,
        DO_NOTHING
    }

    fun assign(fileItem: FileItem?) {
        if (fileItem == null) {
            return
        }

        fileSize = fileItem.size
        if (fileSize!! > maxAllowedSize){
            return
        }

        operationType = OperationTypes.ASSIGN

        createTransactionOriginalFile(fileItem)

        onFileAssigned(transactionOriginalFile!!)

        if (!validateFile(transactionOriginalFile!!)) {
            clearTransactionalFiles()
            operationType = OperationTypes.DO_NOTHING
            return
        }

        createTransactionDirForProcessedFiles()

        fileName = transactionOriginalFile!!.name

    }

    fun delete() {
        operationType = OperationTypes.DELETE

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
    }

    private fun finalizeDeleteOperation(){
        deletePereviousFilesIfExist()
    }


    private fun createTransactionOriginalFile(fileItem: FileItem) {
        if (fileItem.isInMemory) {
            try {
                val tempFile = Files.createTempFile("${System.currentTimeMillis()}-", "tmp").toFile()
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
    }

    private fun deletePereviousFilesIfExist(){
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
        val targetFile = File("${prepareRepositoryPath()}/original/${transactionOriginalFile!!.name}").also {
            if (!it.exists()) {
                it.mkdirs()
                it.createNewFile()
            }
        }
        transactionOriginalFile!!.renameTo(targetFile)
    }

    fun moveTransactionalProcessedFilesToTargetDir(){
        val targetFile = File("${prepareRepositoryPath()}/original/${transactionOriginalFile!!.name}").also {
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
            val fileToRenameTo = File(depth).also {
                if (!it.exists()) {
                    it.mkdirs()
                }
            }
            file.renameTo(File(depth + "/${file.name}"))
        }
    }

}

