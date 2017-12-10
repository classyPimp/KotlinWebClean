package models.contacttype.factories

import models.contacttype.ContactType
import utils.requestparameters.IParam
import java.util.regex.Pattern

object Create {

    fun create(params: IParam): ContactType {
        return ContactType().also {
            val nameToAssign = prepareName(params.get("name")?.string)
            it.name = nameToAssign
        }
    }

    private fun prepareName(string: String?): String? {
        string?.let {
            val pattern = Pattern.compile("\\s+")
            val matcher = pattern.matcher(string)
            val check = matcher.find()
            val toReturn =  matcher.replaceAll(" ")
            return toReturn.trim()
        } ?: return null
    }

}