package daggerComponents.modules

import com.zaxxer.hikari.HikariDataSource
import config.IConfigLoader
import config.YamlConfigLoader
import config.db.properties.DbConnectionProperties
import config.db.properties.AppHikariConfig
import config.db.properties.JooqProperties
import dagger.Module
import dagger.Provides
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.jooq.impl.DefaultConfiguration
import org.jooq.impl.DefaultExecuteListenerProvider
import utils.jooqexecutionlisteners.JooqExecutionsLogger
import javax.inject.Singleton
import javax.sql.DataSource

/**
 * Created by classypimp on 9/12/17.
 */
@Module
class DatabaseModule {

    @Provides fun provideJooqProperties(): JooqProperties {
        return JooqProperties
    }

    @Singleton
    @Provides fun provideDataSource(
            dbConnectionProperties: DbConnectionProperties
    ): DataSource {
        val hikariConfig = AppHikariConfig.get(dbConnectionProperties)
        return  HikariDataSource(hikariConfig)
    }

    @Singleton
    @Provides fun provideDBConnectionProperties(
            iConfigLoader: IConfigLoader
    ): DbConnectionProperties {
        val configMap = iConfigLoader.getPropertiesMapForEnvironment("development", "configuration/database.yml")
        return  DbConnectionProperties(configMap)
    }

    @Singleton
    @Provides fun provideJooqDSLContext(
            jooqProperties: JooqProperties,
            dataSource: DataSource
    ): DSLContext {
        val config = DefaultConfiguration().also {
            it.set(dataSource)
            it.set(jooqProperties.dialect)
            it.set(DefaultExecuteListenerProvider(JooqExecutionsLogger()))
        }
        return DSL.using(config)
    }

}






