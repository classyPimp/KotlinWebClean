package models.incorporationform.daos

import org.jooq.generated.tables.IncorporationForms
import orm.incorporationformgeneratedrepository.IncorporationFormRecord
import models.incorporationform.IncorporationForm

object IncorporationFormIndexDao {
    fun defaultIndex(): MutableList<IncorporationForm> {
        return IncorporationFormRecord.GET().execute()
    }

    fun forFormFeed(): MutableList<IncorporationForm> {
        return defaultIndex()
    }

}