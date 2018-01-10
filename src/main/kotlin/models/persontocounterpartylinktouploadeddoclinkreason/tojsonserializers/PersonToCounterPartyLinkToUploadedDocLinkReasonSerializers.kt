package models.persontocounterpartylinktouploadeddoclinkreason.tojsonserializers

import models.persontocounterpartylinktouploadeddoclinkreason.tojsonserializers.formfeeds.FormFeedsIndex

object PersonToCounterPartyLinkToUploadedDocLinkReasonSerializers {

    object FormFeeds {
        val index = FormFeedsIndex
    }

    val create = CreateSerializer
    val destroy = DestroySerializer
    val edit = EditSerializer
    val index = IndexSerializer
    val show = ShowSerializer
    val update = UpdateSerializer

}