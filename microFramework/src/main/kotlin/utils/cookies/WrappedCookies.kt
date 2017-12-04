package utils.cookies

import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class WrappedCookies(
        val request: HttpServletRequest,
        val response: HttpServletResponse
) {

    private val wrappedCookies: MutableMap<String,Cookie> = mutableMapOf()

    init {
        request.cookies?.forEach {
            wrappedCookies[it.name] = it
        }
    }

    operator fun get(key: String): Cookie? {
        return wrappedCookies[key]
    }

    operator fun set(key: String, value: String?) {
        wrappedCookies[key]?.let {
            it.value = value
            response.addCookie(it)
        }
        Cookie(key, value).let {
            it.domain = ""
            it.path = "/"
            it.maxAge = -1
            it.isHttpOnly = false
            response.addCookie(it)
        }
    }

    fun addCookie(value: Cookie) {
        response.addCookie(value)
    }


    fun getString(key: String): String? {
        return wrappedCookies[key]?.value
    }

    fun getInt(key: String): Int? {
        wrappedCookies[key]?.let {
            if (it.value.isNotBlank()) {
                return Integer.parseInt(it.value)
            }
        }
        return null
    }

    fun delete(key: String) {
        wrappedCookies[key]?.let {
            it.maxAge = 0
            it.value = ""
            it.path = "/"
            response.addCookie(it)
        }
    }

}