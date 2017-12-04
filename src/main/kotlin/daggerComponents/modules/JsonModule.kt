package daggerComponents.modules

import com.fasterxml.jackson.databind.ObjectMapper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class JsonModule {

    @Singleton
    @Provides
    fun providesObjectMapper(): ObjectMapper {
        return ObjectMapper()
    }
}