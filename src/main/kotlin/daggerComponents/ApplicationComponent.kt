package daggerComponents

import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.databind.ObjectMapper
import config.db.properties.JooqProperties
import config.filesAndPaths.PublicFolderConfig
import config.security.SecurityConfig
import dagger.Component
import daggerComponents.modules.*
import daggerComponents.modules.JsonModule
import daggerComponents.modules.server.AssetsModule
import daggerComponents.modules.server.RequestParametersModule
import org.im4java.core.ConvertCmd
import org.jooq.DSLContext
import org.slf4j.Logger
import utils.requestparameters.ServletRequestParamtersWrapper
import utils.requestparameters.querystring.QueryStringParametersWrapper
import assetsmanagement.AssetsPathProvider
import dependencymanagement.templating.ITemplateProcessor
import utils.fileutils.IMimeDetector
import utils.imagehandling.IImageProcessorFactory
import javax.inject.Singleton
import javax.sql.DataSource

/**
 * Created by classypimp on 9/12/17.
 */
@Component(
    modules = arrayOf(
            DatabaseModule::class,
            LoggingModule::class,
            ApplicationUtilitiesModule::class,
            TemplatingModule::class,
            ConfigurationModule::class,
            RequestParametersModule::class,
            JsonModule::class,
            AssetsModule::class,
            FileUtilsModule::class,
            ImageProcessingModule::class,
            SecurityModule::class
    )
)
@Singleton
interface ApplicationComponent {

    fun jooqProperties(): JooqProperties

    fun dataSource(): DataSource

    fun defaultLogger(): Logger

    fun baseJooqDSLContext(): DSLContext

    fun templateProcessor(): ITemplateProcessor

    fun publicFolderConfig(): PublicFolderConfig

    fun servletRequestParametersWrapper(): ServletRequestParamtersWrapper

    fun assetsPathProvider(): AssetsPathProvider

    fun queryStringParametersWrapper(): QueryStringParametersWrapper

    fun mimeDetector(): IMimeDetector

    fun imageProcessorFactory(): IImageProcessorFactory

    fun imageMagicConvertCommand(): ConvertCmd

    fun securityConfig(): SecurityConfig

    fun jwtVerifier(): JWTVerifier

    fun jwtHmacsAlgorithm(): Algorithm

    fun jacksonObjectMapper(): ObjectMapper
}
