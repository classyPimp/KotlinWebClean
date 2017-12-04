package orm.templateDataModels.model

/**
 * Created by classypimp on 9/19/17.
 */
class PackagesBean
(
        val baseGenerated: String,
        val jooqGeneratedTable: String,
        val model: String,
        val fieldTypes: MutableSet<String>,
        val jooqTablesRoot: String
)
{
    lateinit var associatedModelTypesToImport: MutableSet<String>
    val jooqGeneratedtableSimpleName = jooqGeneratedTable.split('.').last()

}