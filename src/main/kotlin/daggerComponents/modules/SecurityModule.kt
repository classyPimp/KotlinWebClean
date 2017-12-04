package daggerComponents.modules

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import config.security.SecurityConfig
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SecurityModule {

    @Singleton
    @Provides
    fun providesJwtHmacsAlgorithm(securityConfig: SecurityConfig): Algorithm {
        return  Algorithm.HMAC256(securityConfig.secretKey)
    }

    @Singleton
    @Provides
    fun providesJwtVerifier(algorithm: Algorithm): JWTVerifier {
        return JWT.require(algorithm)
                .build()
    }


}