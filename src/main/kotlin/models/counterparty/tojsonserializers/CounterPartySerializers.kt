package models.counterparty.tojsonserializers

import models.counterparty.tojsonserializers.formfeeds.FormFeedsIndex

object CounterPartySerializers {

    object FormFeeds {
        val index = FormFeedsIndex
    }

    val create = Create

    val index = Index

    val show = Show

    val destroy = Create

    val update = Create

}