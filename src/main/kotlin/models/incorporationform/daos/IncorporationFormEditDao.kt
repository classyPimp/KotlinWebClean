package models.incorporationform.daos

import org.jooq.generated.tables.IncorporationForms.INCORPORATION_FORMS
import orm.incorporationformgeneratedrepository.IncorporationFormRecord
import models.incorporationform.IncorporationForm

object IncorporationFormEditDao {
    fun defaultForEdit(id: Long): IncorporationForm? {
        return IncorporationFormRecord.GET().where(
            INCORPORATION_FORMS.ID.eq(id)
        ).limit(1).execute().firstOrNull()
    }


}