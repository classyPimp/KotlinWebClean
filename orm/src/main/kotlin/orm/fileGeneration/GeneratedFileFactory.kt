package orm.fileGeneration

import orm.fileGeneration.services.KotlinGeneratedDirPathAccessor
import java.io.File

/**
 * Created by classypimp on 9/8/17.
 */
object GeneratedFileFactory {

    fun createDefault(packageName: String, fileName: String): File {
        val completeFileName = "${fileName}.kt"
        val path = "${KotlinGeneratedDirPathAccessor.get()}/${packageName.split('.').joinToString("/")}"

        val directory = File(path)
        if (! directory.exists()){
            directory.mkdirs()
        }

        return File(
                path,
                completeFileName
        )
    }

}