package orm.utils

import orm.services.InTransactionJooqDslProvider
import orm.dependencymanagement.OrmDependenciesManager

object TransactionRunner {

    inline fun run(crossinline block: (InTransactionJooqDslProvider)->Unit){
        OrmDependenciesManager.provider.defaultDslContext.transaction { configuration ->
            block.invoke(
                    InTransactionJooqDslProvider(
                            configuration
                    )
            )
        }
    }

}