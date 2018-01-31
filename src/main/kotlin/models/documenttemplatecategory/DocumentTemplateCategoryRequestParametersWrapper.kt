package models.documenttemplatecategory

import models.documenttemplatecategory.DocumentTemplateCategory
import utils.requestparameters.IParam
import utils.stdlibextensions.string.trimAndSquishWhiteSpace

class DocumentTemplateCategoryRequestParametersWrapper(val requestParameters: IParam) {

    val id = requestParameters.get("id")?.long()
    val name = requestParameters.get("name")?.string?.trimAndSquishWhiteSpace()
    val description = requestParameters.get("description")?.string?.trimAndSquishWhiteSpace()

}