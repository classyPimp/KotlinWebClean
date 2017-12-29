package models.incorporationform

import models.incorporationform.IncorporationForm
import utils.requestparameters.IParam

class IncorporationFormRequestParametersWrapper(val requestParameters: IParam) {

    val id: Long? = requestParameters.get("id")?.long()

    val name: String? = requestParameters.get("name")?.string

    val nameShort: String? = requestParameters.get("nameShort")?.string

}