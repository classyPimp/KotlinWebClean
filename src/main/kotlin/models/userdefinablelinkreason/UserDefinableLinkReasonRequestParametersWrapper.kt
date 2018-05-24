package models.userdefinablelinkreason

import utils.requestparameters.IParam

import java.sql.Timestamp


class UserDefinableLinkReasonRequestParametersWrapper(val requestParameters: IParam) {

    val id: Long? by lazy { requestParameters.get("id")?.long }
    val createdAt: Timestamp? by lazy { requestParameters.get("createdAt")?.timestamp }
    val updatedAt: Timestamp? by lazy { requestParameters.get("updatedAt")?.timestamp }
    val reasonName: String? by lazy { requestParameters.get("reasonName")?.string }
    val forType: String? by lazy { requestParameters.get("forType")?.string }
    val subIdentifier: String? by lazy { requestParameters.get("subIdentifier")?.string }
    val reasonDescription: String? by lazy { requestParameters.get("reasonDescription")?.string }


}