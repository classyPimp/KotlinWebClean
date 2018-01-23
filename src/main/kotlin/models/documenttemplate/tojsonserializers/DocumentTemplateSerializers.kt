package models.documenttemplate.tojsonserializers

import models.documenttemplate.tojsonserializers.prevalidations.PrevalidationCreateSerializer

object DocumentTemplateSerializers {

    object Prevalidations {
        val create = PrevalidationCreateSerializer
    }

    val create = CreateSerializer

    val show = ShowSerializer

    val index = IndexSerializer

    val edit = EditSerializer

    val update = UpdateSerializer

    val destroy = DestroySerializer

}