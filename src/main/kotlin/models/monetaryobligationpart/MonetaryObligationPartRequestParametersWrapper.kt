package models.monetaryobligationpart

import models.monetaryobligationpart.MonetaryObligationPart
import utils.requestparameters.IParam
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class MonetaryObligationPartRequestParametersWrapper(val requestParameters: IParam) {

    val amount: Long? by lazy {
        requestParameters.get("amount")?.long()?.let {
            it / 100
        }
    }
    val monetaryObligationId: Long? by lazy {
        requestParameters.get("monetaryObligationId")?.long()
    }

    val dueDate: Timestamp? by lazy {
        val valueAtParams = requestParameters.get("dueDate")?.string
        var localDateTime: LocalDateTime? = null
        val dateTimeFormatter = DateTimeFormatter.ofPattern("d.M.y")
        if (valueAtParams != null) {
            try {
                localDateTime = LocalDateTime.parse(valueAtParams, dateTimeFormatter).withHour(0).withMinute(0)
            } catch (error: DateTimeParseException) {
                localDateTime = null
            }
        }
        if (localDateTime != null) {
            Timestamp.valueOf(localDateTime)
        } else {
            null
        }
    }

}