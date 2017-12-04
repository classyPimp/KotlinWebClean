package utils.sessions

import com.auth0.jwt.JWTCreator
import com.auth0.jwt.interfaces.DecodedJWT

interface IClientsJwtPlucker {

    fun initializeOrReuseToken(decodedJWT: DecodedJWT?): JWTCreator.Builder

}