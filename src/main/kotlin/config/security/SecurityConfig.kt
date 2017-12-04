package config.security

import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SecurityConfig
@Inject
constructor(
        val map: MutableMap<String, Any?>
) {

    val secretKey: String by map

}