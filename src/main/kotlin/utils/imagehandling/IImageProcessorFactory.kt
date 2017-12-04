package utils.imagehandling

import java.io.File
import java.nio.file.Path

/**
 * Created by Муса on 17.11.2017.
 */
interface IImageProcessorFactory {

    fun create(file: File, destinationFile: File): AbstractImageProcessor

}