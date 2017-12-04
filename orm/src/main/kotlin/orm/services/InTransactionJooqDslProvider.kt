package orm.services

import org.jooq.Configuration
import org.jooq.DSLContext
import org.jooq.impl.DSL

/**
 * Created by classypimp on 10/5/17.
 */
class InTransactionJooqDslProvider(val configuration: Configuration) {

    private val invokedDSLs = ArrayList<DSLContext>(10)

    val inTransactionDsl: DSLContext by lazy {
        val newDsl = DSL.using(configuration)
        invokedDSLs.add(newDsl)
        newDsl
    }

    fun produceDsl(): DSLContext {
        val newDsl = DSL.using(configuration)
        invokedDSLs.add(newDsl)
        return newDsl
    }

    fun closeInvokedDSLs() {
        for (dsl in invokedDSLs) {
            val connection = dsl.configuration().connectionProvider().acquire()
            if (!connection.isClosed) {
                connection.rollback()
                connection.close()
            }
        }
    }

}