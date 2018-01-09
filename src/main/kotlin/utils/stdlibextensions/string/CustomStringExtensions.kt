package utils.stdlibextensions.string

import java.util.regex.Pattern

/**
 * Created by Муса on 20.12.2017.
 */

fun String.trimAndSquishWhiteSpace(): String {
    val pattern = Pattern.compile("\\s+")
    val matcher = pattern.matcher(this)
    matcher.find()
    val toReturn = matcher.replaceAll(" ")
    return toReturn.trim()
}

fun String.toNullIfBlank(): String? {
    if (this.isBlank()) {
        return null
    } else {
        return this
    }
}
