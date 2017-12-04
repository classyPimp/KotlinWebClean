package daggerComponents.modules

import dagger.Module
import dagger.Provides
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.inject.Singleton
import App
/**
 * Created by classypimp on 9/13/17.
 */
@Module
class LoggingModule {

    @Singleton
    @Provides fun provideLogger(): Logger {
        return LoggerFactory.getLogger(App::class.java)
    }

}