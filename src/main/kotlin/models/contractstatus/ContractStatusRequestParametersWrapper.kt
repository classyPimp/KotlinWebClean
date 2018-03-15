package models.contractstatus

import models.contractstatus.ContractStatus
import utils.requestparameters.IParam
import java.sql.Timestamp

class ContractStatusRequestParametersWrapper(val requestParameters: IParam) {

    val id: Long? by lazy { requestParameters.get("id")?.long() }
    val isCommitted: Boolean? by lazy { requestParameters.get("isCommitted")?.boolean }
    val isSupplement: Boolean? by lazy { requestParameters.get("isSupplement")?.boolean }
    val parentContractId: Long? by lazy { requestParameters.get("parentContractId")?.long() }
    val rootContractId: Long? by lazy { requestParameters.get("rootContractId")?.long() }
    val isSupplemented: Boolean? by lazy { requestParameters.get("isSupplemented")?.boolean }
    val isProject: Boolean? by lazy { requestParameters.get("isProject")?.boolean }
    val isCancelled: Boolean? by lazy { requestParameters.get("isCancelled")?.boolean }
    val isCompleted: Boolean? by lazy { requestParameters.get("isCompleted")?.boolean }
    val internalNumber: String? by lazy { requestParameters.get("internalNumber")?.string }
    val assignedNumber: String? by lazy { requestParameters.get("assignedNumber")?.string }
    val formalDate: Timestamp? by lazy { requestParameters.get("formalDate")?.timestamp }

}