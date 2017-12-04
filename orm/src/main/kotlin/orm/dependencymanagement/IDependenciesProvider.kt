package orm.dependencymanagement

import org.jooq.DSLContext
import orm.dependencymanagement.adapters.modeltojsonserialization.IToJsonSerializerObjectMapperAdapter

/**
 * Created by Муса on 21.11.2017.
 */
interface IDependenciesProvider {

    val defaultDslContext: DSLContext

    val jsonObjectMapper: IToJsonSerializerObjectMapperAdapter

}

