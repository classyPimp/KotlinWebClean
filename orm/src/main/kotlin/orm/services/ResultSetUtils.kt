package orm.services

import java.sql.ResultSet

/**
 * Created by classypimp on 9/13/17.
 */
object ResultSetUtils {

    fun buildColumnNameList(resultSet: ResultSet): MutableList<String> {
        val listToReturn: MutableList<String> = mutableListOf()
        val metaData = resultSet.metaData
        val columnCount = metaData.columnCount
        for (i in 1..columnCount) {
            listToReturn.add(metaData.getColumnName(i))
        }
        return listToReturn
    }

}