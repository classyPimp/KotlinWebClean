package utils.fileutils

import org.apache.commons.lang3.RandomStringUtils
import java.util.*

/**
 * Created by Муса on 23.01.2018.
 */
object FileNamingUtils {

    fun generateUniqueFileName(): String {
        var filename = ""
        val millis = System.currentTimeMillis()
        var datetime = Date().toGMTString()
        datetime = datetime.replace(" ", "")
        datetime = datetime.replace(":", "")
        val rndchars = RandomStringUtils.randomAlphanumeric(16)
        filename = rndchars + "_" + datetime + "_" + millis
        return filename
    }

}