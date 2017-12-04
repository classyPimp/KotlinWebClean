package servletUtils

import router.src.ServletRequestContext
import java.io.File
import java.io.FileInputStream

/**
 * Created by Муса on 08.11.2017.
 */
class SimpleFileServer(
        val rootPathOfServedFile: String
) {

    fun serveFile(context: ServletRequestContext) {
        val file = File("${rootPathOfServedFile}${context.request.requestURI}")
        if (!file.exists()) {
            context.response.sendError(404)
            return
        }
        val outputStream = context.response.getOutputStream()
        val inputStream = FileInputStream(file)
        val buffer = ByteArray(4096)

        while(true) {
            val length = inputStream.read(buffer)
            if(length > 0) {
                outputStream.write(buffer, 0, length)
            } else {
                break
            }
        }

        inputStream.close()
        outputStream.flush()
    }


}