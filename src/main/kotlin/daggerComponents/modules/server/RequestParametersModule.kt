package daggerComponents.modules.server

import com.fasterxml.jackson.databind.ObjectMapper
import dagger.Module
import dagger.Provides
import utils.requestparameters.ServletRequestParamtersWrapper
import utils.requestparameters.json.JsonParametersParser
import utils.requestparameters.multipartformdata.MultipartFormDataParametersParser
import utils.requestparameters.querystring.QueryStringParametersWrapper
import javax.inject.Singleton

/**
 * Created by Муса on 14.11.2017.
 */
@Module
class RequestParametersModule {

    @Singleton
    @Provides
    fun provideServletRequestParamtersWrapper(objectMapper: ObjectMapper) : ServletRequestParamtersWrapper {
          return ServletRequestParamtersWrapper(
                  jsonParametersParser = JsonParametersParser(objectMapper),
                  multiPartFormDataParametersParser = MultipartFormDataParametersParser()
          )
    }

    @Singleton
    @Provides
    fun provideQueryStringParametersWrapper(): QueryStringParametersWrapper {
        return QueryStringParametersWrapper()
    }

}