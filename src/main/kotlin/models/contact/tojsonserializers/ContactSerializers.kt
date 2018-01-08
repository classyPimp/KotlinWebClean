package models.contact.tojsonserializers


object ContactSerializers {

    val personCreate = PersonCreate
    val personDestroy = PersonDestroy
    val personUpdate = PersonCreate

    object ForCounterParties {
        val create = ForCounterPartyCreate
        val show = ForCounterPartyShow
        val update = ForCounterPartyUpdate
        val destroy = ForCounterPartyDestroy
        val index = ForCounterPartyIndex
        val edit = ForCounterPartyEdit
    }
}