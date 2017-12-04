package clientimplementedutils.security

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTCreator
import com.auth0.jwt.interfaces.Claim
import com.auth0.jwt.interfaces.DecodedJWT
import utils.sessions.IClientsJwtPlucker

class JwtPlucker : IClientsJwtPlucker {

    override fun initializeOrReuseToken(decodedJWT: DecodedJWT?): JWTCreator.Builder {
        val newJwt = JWT.create()

        decodedJWT?.let {
            it.claims.forEach {
                name, claim ->
                setClaim(name, claim, newJwt)
            }
        }

        return newJwt
    }

    private fun setClaim(key: String, claim: Claim, newJwt: JWTCreator.Builder){
        when (key) {
            "userId" -> {
                newJwt.withClaim("userId", claim.asLong())
            }
            else -> {
                newJwt.withClaim(key, claim.asString())
            }
        }
    }

}