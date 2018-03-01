package models.person.daos

import org.jooq.generated.tables.People
import orm.persongeneratedrepository.PersonRecord
import models.person.Person
import org.jooq.generated.Tables.PEOPLE
import org.jooq.impl.DSL

object PersonIndexDao {

    val table = PEOPLE

    fun searchByName(query: String?): MutableList<Person> {
        if (query == null || query.isBlank()) {
            return PersonRecord.GET().execute()
        }
        return PersonRecord.GET()
                .where(DSL.trueCondition().and("{0} ~* {1}", table.NAME, DSL.`val`(query)))
                .execute()
    }

}