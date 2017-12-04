package utils.sessions

import com.auth0.jwt.JWTCreator
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import utils.cookies.WrappedCookies
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtSessionHandler(
        val request: HttpServletRequest,
        val response: HttpServletResponse
): ISessionHandler {

    companion object {
        //@Inject
        lateinit var sessionIdentifyingKey: String
        //@Inject
        lateinit var clientsJwtPlucker: IClientsJwtPlucker

        lateinit var algorithm: Algorithm

        lateinit var verifier: JWTVerifier

    }

    val wrappedCookies: WrappedCookies by lazy { WrappedCookies(request, response) }

    var decodedJwt: DecodedJWT? = null
        get() {
            if (field != null) {
                return field
            } else {
                field = decodeJwt()
                return field
            }
        }

    var jwtBeingBuilt: JWTCreator.Builder? = null
        get() {
            if (field == null) {
                field = clientsJwtPlucker.initializeOrReuseToken(decodedJwt)
                return field
            } else {
                return field
            }
        }

    override fun isLoggedIn(): Boolean {
        decodedJwt?.let {
            it.getClaim(sessionIdentifyingKey).asLong()?.let {
                return true
            }
        }
        return false
    }

    override fun logIn(sessionIdentifier: Long) {
        val token = jwtBeingBuilt!!.withClaim(sessionIdentifyingKey, sessionIdentifier)
    }

    override fun addToSession(key: String, value: Long?) {
        jwtBeingBuilt!!.withClaim(key, value)
    }

    override fun addToSession(key: String, value: String?) {
        jwtBeingBuilt!!.withClaim(key, value)
    }


    override fun logOut() {
        wrappedCookies.delete("jwt")
    }

    override fun remember(sessionIdentifier: Long) {
        throw IllegalAccessException("session uses jwt: in order to use remeber functionality, call .commit(maxAge = rellyLongtTime)")
    }

    override fun forget() {
        logOut()
    }

    override fun commit(maxAge: Int) {
        wrappedCookies.get("jwt")?.let {
            it.value = jwtBeingBuilt!!.sign(algorithm)
            it.maxAge = maxAge
            it.path = "/"
            it.isHttpOnly = false
            wrappedCookies.addCookie(it)
            return
        }

        jwtBeingBuilt?.let {
            wrappedCookies.addCookie(
                Cookie(
                    "jwt",
                        it.sign(algorithm)
                    ).also {
                        it.maxAge = maxAge
                        it.path = "/"
                        it.isHttpOnly = false
                    }
            )
        }
    }

    override fun commit() {
        wrappedCookies.get("jwt")?.let {
            it.value = jwtBeingBuilt!!.sign(algorithm)
            wrappedCookies.addCookie(it)
            return
        }

        jwtBeingBuilt?.let {
            wrappedCookies.addCookie(
                    Cookie(
                            "jwt",
                            it.sign(algorithm)
                    ).also {
                        it.maxAge = -1
                        it.path = "/"
                        it.isHttpOnly = false
                    }
            )
        }
    }

    override fun getString(key: String): String? {
        decodedJwt?.let {
            return it.getClaim(key).asString()
        }
        return null
    }

    override fun getStringList(key: String): MutableList<String>? {
        decodedJwt?.let {
            return it.getClaim(key).asList(String::class.java)
        }
        return null
    }

    override fun getInt(key: String): Int? {
        decodedJwt?.let {
            return it.getClaim(key).asInt()
        }
        return null
    }


    private fun decodeJwt(): DecodedJWT? {
        val jwtToken = wrappedCookies.getString("jwt")
        if (jwtToken.isNullOrBlank()) {
            return null
        } else {
            return verifier.verify(jwtToken)
        }
    }


}