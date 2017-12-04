package utils.sessions

interface ISessionHandler {

    fun isLoggedIn(): Boolean

    fun logIn(sessionIdentifier: Long)

    fun logOut()

    fun remember(sessionIdentifier: Long)

    fun forget()

    fun addToSession(key: String, value: String?)

    fun addToSession(key: String, value: Long?)

    fun commit(maxAge: Int)

    fun commit()

    fun getString(key: String): String?

    fun getInt(key: String): Int?

    fun getStringList(key: String): MutableList<String>?

}

