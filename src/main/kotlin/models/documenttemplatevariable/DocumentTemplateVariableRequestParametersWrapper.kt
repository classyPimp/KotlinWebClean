package models.documenttemplatevariable

import models.documenttemplatevariable.DocumentTemplateVariable
import utils.requestparameters.IParam

class DocumentTemplateVariableRequestParametersWrapper(val requestParameters: IParam) {

    var id: Long? = requestParameters.get("id")?.long()

    var defaultValue: String? = requestParameters.get("defaultValue")?.string

    var humanizedName: String? = requestParameters.get("humanizedName")?.string

    var name: String? = requestParameters.get("name")?.string

    var isPreprogrammed: Boolean? = requestParameters.get("isPreprogrammed")?.boolean

    var description: String? = requestParameters.get("description")?.string
}