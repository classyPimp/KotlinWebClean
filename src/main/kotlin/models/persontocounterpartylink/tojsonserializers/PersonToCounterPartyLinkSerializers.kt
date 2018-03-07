package models.persontocounterpartylink.tojsonserializers

import models.persontocounterpartylink.tojsonserializers.forcounterparties.ForCounterPartiesIndex
import models.persontocounterpartylink.tojsonserializers.forcounterparties.ForCounterPartyEdit
import models.persontocounterpartylink.tojsonserializers.forcounterparties.ForCounterPartyShow

object PersonToCounterPartyLinkSerializers {

    object ForCounterParties {
        val index = ForCounterPartiesIndex
        val show = ForCounterPartyShow
        val edit = ForCounterPartyEdit
    }

    val create = Create

    val show = Show

    val index = Index

    val update = Update

    val edit = Edit

    val destroy = Destroy

    val indexForPerson = IndexForPerson

    val forPersonEditGeneralInfo = PersonToCounterPartyLinkForPersonEditGeneralInfoJsonSerializer

}