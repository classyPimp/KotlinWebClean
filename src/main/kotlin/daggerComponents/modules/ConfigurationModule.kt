package daggerComponents.modules

import config.IConfigLoader
import config.filesAndPaths.PublicFolderConfig
import config.security.SecurityConfig
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Муса on 02.11.2017.
 */
@Module
class ConfigurationModule {
    @Singleton
    @Provides
    fun providePublicFolderConfig(configLoader: IConfigLoader) : PublicFolderConfig {
        val configMap = configLoader.getPropertiesMapForEnvironment("development", "configuration/assets.yml")
        return  PublicFolderConfig(configMap)
    }

    @Singleton
    @Provides
    fun provideSecurityConfig(configLoader: IConfigLoader): SecurityConfig {
        val configMap = configLoader.getPropertiesMapForEnvironment("development", "configuration/security.yml")
        return SecurityConfig(configMap)
    }

}