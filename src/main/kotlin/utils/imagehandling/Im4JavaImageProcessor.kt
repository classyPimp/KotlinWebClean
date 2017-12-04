package utils.imagehandling

import org.im4java.core.IMOperation
import java.io.File
import java.lang.Exception


/**
 * Created by Муса on 17.11.2017.
 */
class Im4JavaImageProcessor(
        override val file: File,
        override val destinationPath: File

): AbstractImageProcessor(file, destinationPath) {

    val convertCommand = App.component.imageMagicConvertCommand()
    val iMOperation = IMOperation()

    override fun resize(widthInPx: Int, heightInPx: Int) {
        iMOperation.addImage(file.absolutePath.toString())
        iMOperation.resize(600, 600)
        iMOperation.addImage(destinationPath.absolutePath.toString())
    }

    override fun execute(): Boolean {
        try {
            convertCommand.run(iMOperation)
        } catch (error: Exception) {
            println(convertCommand.errorText)
            throw(error)
        }
        return true
    }

}