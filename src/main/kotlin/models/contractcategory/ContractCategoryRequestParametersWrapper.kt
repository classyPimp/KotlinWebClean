package models.contractcategory

import models.contractcategory.ContractCategory
import utils.requestparameters.IParam
import utils.stdlibextensions.string.trimAndSquishWhiteSpace

class ContractCategoryRequestParametersWrapper(val requestParameters: IParam) {

    val id: Long? by lazy { requestParameters.get("id")?.long() }
    val name: String? by lazy { requestParameters.get("name")?.string?.trimAndSquishWhiteSpace() }
    val description: String? by lazy { requestParameters.get("description")?.string?.trimAndSquishWhiteSpace() }

}