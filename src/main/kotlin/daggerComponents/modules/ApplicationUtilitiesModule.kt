package daggerComponents.modules

import config.IConfigLoader
import config.YamlConfigLoader
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by classypimp on 11/1/17.
 */

@Module
class ApplicationUtilitiesModule {
    @Singleton
    @Provides
    fun provideConfigLoader(): IConfigLoader {
        return YamlConfigLoader()
    }
}