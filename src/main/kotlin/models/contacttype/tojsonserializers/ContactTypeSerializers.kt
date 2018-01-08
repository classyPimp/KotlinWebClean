package models.contacttype.tojsonserializers

object ContactTypeSerializers {

    val create = Create

    val index = Index

    val update = Create

    val destroy = Create

    object InputFeeds {
        val person = Index
        val counterParty = Index
    }



}