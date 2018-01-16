package utils.fileutils

import java.io.File
import java.io.InputStream

/**
 * Created by Муса on 17.11.2017.
 */
interface IMimeDetector {

    fun detect(file: File): String?

    fun detect(inputStream: InputStream): String?

}