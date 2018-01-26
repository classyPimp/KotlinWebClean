package utils.fileutils

import java.io.*


/**
 * Created by Муса on 26.01.2018.
 */
object FileCopyer {

    @Throws(IOException::class)
    fun copyFileToFile(src: File, dest: File) {
        copyInputStreamToFile(FileInputStream(src), dest)
        dest.setLastModified(src.lastModified())
    }

    @Throws(IOException::class)
    private fun copyInputStreamToFile(`in`: InputStream, dest: File) {
        copyInputStreamToOutputStream(`in`, FileOutputStream(dest))
    }


    @Throws(IOException::class)
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