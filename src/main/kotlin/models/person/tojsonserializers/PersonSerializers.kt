package models.person.tojsonserializers

import models.person.tojsonserializers.formfeeds.FormFeedsIndex

object PersonSerializers {

    object FormFeeds {
        val index = FormFeedsIndex
    }

    val create: Create = Create

    val get: Create = Create

    val index: Index = Index

    val udpate: Update = Update

    val destroy: Create = Create
}