package models.incorporationform.tojsonserializers

import models.incorporationform.tojsonserializers.formfeeds.FormFeedsIndex

object IncorporationFormSerializers {

    val create = Create

    val index = Index

    val edit = Create

    val show = Create

    val update = Create

    val destroy = Create

    object formFeeds {
        val index = FormFeedsIndex
    }
}