package utils.security

import org.mindrot.jbcrypt.BCrypt
import java.security.SecureRandom
import sun.text.normalizer.UTF16.append



class PasswrodHashingService {

    val alphanumericCharSet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
    val secureRandomInstance = SecureRandom()
    val rememberMeTokenLength = 32

    fun hashPassword(password: String): String {
        if (password.length < 2) {
            throw IllegalStateException("string: ${password} was passed for hashing: it's too short")
        }
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }

    fun checkPassword(passwordToTest: String, hashedPassword: String): Boolean {
        return BCrypt.checkpw(passwordToTest, hashedPassword)
    }

    fun generateRememberMeToken(): String {
        val stringBuilder = StringBuilder(rememberMeTokenLength)
        for (i in 0..rememberMeTokenLength) {
            stringBuilder.append(
                    alphanumericCharSet[ secureRandomInstance.nextInt(alphanumericCharSet.length) ]
            )
        }
        return stringBuilder.toString()
    }

}