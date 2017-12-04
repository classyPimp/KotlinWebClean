import org.jooq.DSLContext
import orm.dependencymanagement.IDependenciesProvider
import orm.dependencymanagement.adapters.modeltojsonserialization.IToJsonSerializerObjectMapperAdapter
import orm.dependencymanagement.adapters.modeltojsonserialization.jacksonadapter.JacksonObjectMapperAdapter

/**
 * Created by Муса on 21.11.2017.
 */
object OrmDependenciesProvider : IDependenciesProvider {

    override val defaultDslContext: DSLContext = App.component.baseJooqDSLContext()

    override val jsonObjectMapper: IToJsonSerializerObjectMapperAdapter = JacksonObjectMapperAdapter()

}