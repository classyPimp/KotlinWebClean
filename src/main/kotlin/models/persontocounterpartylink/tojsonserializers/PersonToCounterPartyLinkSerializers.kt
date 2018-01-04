package models.persontocounterpartylink.tojsonserializers

import models.persontocounterpartylink.tojsonserializers.forcounterparties.ForCounterPartiesIndex

object PersonToCounterPartyLinkSerializers {

    object ForCounterParties {
        val index = ForCounterPartiesIndex
    }

    val create = Create

    val show = Show

    val index = Index

    val update = Update

    val edit = Edit

    val destroy = Destroy

}