package utils.fileutils

import java.io.File

/**
 * Created by Муса on 17.11.2017.
 */
interface IMimeDetector {

    fun detect(file: File): String?

}