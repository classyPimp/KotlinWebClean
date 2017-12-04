package orm.modelUtils

import java.sql.ResultSet

/**
 * Created by classypimp on 9/14/17.
 */
object RepositoryDbUtils {

    inline fun executeThisBlockAndCloseResultSet(resultSet: ResultSet, block: ()->Unit) {
        try {
            block.invoke()
        } finally {
            if (!resultSet.isClosed()) {
                resultSet.close()
            }
        }
    }

}