package orm.templateDataModels.model

/**
 * Created by classypimp on 9/17/17.
 */
object FieldTypeImportPathSetter {

    fun getPathToType(typeName: String): MutableList<String> {
        return when (typeName) {
            "Timestamp?", "Timestamp" -> mutableListOf<String>(
                    "java.sql.Timestamp",
                    "java.time.Instant"
            )
            else -> mutableListOf()

        }
    }

}