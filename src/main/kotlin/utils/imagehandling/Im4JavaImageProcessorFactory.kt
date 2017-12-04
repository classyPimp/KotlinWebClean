package utils.imagehandling

import java.io.File

/**
 * Created by Муса on 17.11.2017.
 */
class Im4JavaImageProcessorFactory: IImageProcessorFactory {

    override fun create(file: File, destinationFile: File): AbstractImageProcessor {
        return Im4JavaImageProcessor(file, destinationFile)
    }

}