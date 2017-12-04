package utils.imagehandling

import java.io.File
import java.nio.file.Path

/**
 * Created by Муса on 17.11.2017.
 */
abstract class AbstractImageProcessor(
        open val file: File?,
        open val destinationPath: File
) {

    abstract fun resize(widthInPx: Int, heightInPx: Int)

    abstract fun execute(): Boolean

}