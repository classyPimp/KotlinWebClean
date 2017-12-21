package models.person

import utils.requestparameters.IParam
import utils.stdlibextensions.string.trimAndSquishWhiteSpace

class PersonRequestParametersWrapper(val requestParameters: IParam) {

    var name: String? = prepareName()

    fun prepareName(): String? {
        return requestParameters.get("name")?.string?.trimAndSquishWhiteSpace()
    }

}