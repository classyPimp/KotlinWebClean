package utils.security

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec



class EncryptionUtils {

    val aesKey: SecretKeySpec = SecretKeySpec(
            App.component.securityConfig().secretKey.toByteArray(), "AES"
    )

    fun encrypt(valueToEncrypt: String): String {
        val cipher = getAesCipher()
        cipher.init(Cipher.ENCRYPT_MODE, aesKey)
        val encrypted = cipher.doFinal(valueToEncrypt.toByteArray())
        return encrypted.toString()
    }

    fun decrypt(valueToDecrypt: String): String {
        val cipher = getAesCipher()
        cipher.init(Cipher.DECRYPT_MODE, aesKey)
        val decrypted = String(cipher.doFinal(valueToDecrypt.toByteArray()))
        return decrypted
    }

    fun getAesCipher(): Cipher {
        return Cipher.getInstance("AES")
    }

}
