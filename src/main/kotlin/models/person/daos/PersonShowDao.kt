package models.person.daos

import org.jooq.generated.tables.People
import orm.persongeneratedrepository.PersonRecord
import models.person.Person
import org.jooq.generated.Tables.PEOPLE

object PersonShowDao {

    val table = PEOPLE

    fun minimalInfo(id: Long): Person? {
        return PersonRecord.GET()
                .where(table.ID.eq(id))
                .limit(1)
                .execute()
                .firstOrNull()
    }

}