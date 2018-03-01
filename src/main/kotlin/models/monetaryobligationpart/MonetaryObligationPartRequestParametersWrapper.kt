package models.monetaryobligationpart

import models.monetaryobligationpart.MonetaryObligationPart
import utils.requestparameters.IParam
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.format.DateTimeParseException
import java.time.format.ResolverStyle

class MonetaryObligationPartRequestParametersWrapper(val requestParameters: IParam) {

    val id: Long? by lazy {
        requestParameters.get("id")?.long()
    }

    val markedForDestruction: Boolean? by lazy {
        requestParameters.get("markedForDestruction")?.boolean
    }

    val amount: Long? by lazy {
        requestParameters.get("amount")?.long()
    }

    val monetaryObligationId: Long? by lazy {
        requestParameters.get("monetaryObligationId")?.long()
    }

    val dueDate: Timestamp? by lazy {
        val valueAtParams = requestParameters.get("dueDate")?.string
        var localDate: LocalDate? = null

        val dateTimeFormatter = DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd")
                .toFormatter()
                .withResolverStyle(ResolverStyle.SMART)

        if (valueAtParams != null) {
            try {
                valueAtParams.split(" ").let {
                    if (it.size == 2) {
                        val hourPart = it.get(1)
                        localDate = LocalDate.parse(it.get(0), dateTimeFormatter)
                    } else {
                        localDate = LocalDate.parse(valueAtParams)
                    }
                }
            } catch (error: DateTimeParseException) {
                localDate = null
            }
        }
        if (localDate != null) {
            Timestamp.valueOf(localDate!!.atStartOfDay())
        } else {
            null
        }

    }

}