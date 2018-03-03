package models.persontocounterpartylink.daos

import models.person.daos.PersonIndexDao
import org.jooq.generated.tables.PersonToCounterPartyLinks
import orm.persontocounterpartylinkgeneratedrepository.PersonToCounterPartyLinkRecord
import models.persontocounterpartylink.PersonToCounterPartyLink
import org.jooq.generated.Tables.PEOPLE
import org.jooq.generated.Tables.PERSON_TO_COUNTER_PARTY_LINKS
import org.jooq.impl.DSL

object PersonToCounterPartyLinkIndexDao {
    fun forIndex(): MutableList<PersonToCounterPartyLink> {
        return PersonToCounterPartyLinkRecord.GET().execute()
    }

    fun byCounterPartyId(id: Long): MutableList<PersonToCounterPartyLink> {
        return PersonToCounterPartyLinkRecord.GET()
                .where(
                        PERSON_TO_COUNTER_PARTY_LINKS.COUNTER_PARTY_ID.eq(id)
                )
                .preload {
                    it.person()
                    it.personToCounterPartyLinkReason()
                    it.personToCounterPartyLinkToUploadedDocumentLinks() {
                        it.preload {
                            it.uploadedDocument()
                            it.personToCounterPartyLinkToUploadedDocLinkReason()
                        }
                    }
                }
                .execute()
    }

    fun forPerson(personId: Long): MutableList<PersonToCounterPartyLink> {
        return PersonToCounterPartyLinkRecord.GET()
                .where(
                        PERSON_TO_COUNTER_PARTY_LINKS.PERSON_ID.eq(personId)
                )
                .preload {
                    it.counterParty() {
                        it.preload {
                            it.incorporationForm()
                        }
                    }
                    it.personToCounterPartyLinkReason()
                    it.personToCounterPartyLinkToUploadedDocumentLinks() {
                        it.preload {
                            it.personToCounterPartyLinkToUploadedDocLinkReason()
                            it.uploadedDocument()
                        }
                    }
                }
                .execute()
    }

    fun forCounterParty(counterPartyId: Long, query: String?): MutableList<PersonToCounterPartyLink> {
        if (query == null) {
            return PersonToCounterPartyLinkRecord.GET()
                    .preload {
                        it.person()
                        it.personToCounterPartyLinkReason()
                    }
                    .where(PERSON_TO_COUNTER_PARTY_LINKS.COUNTER_PARTY_ID.eq(counterPartyId))
                    .execute()
        }
        return PersonToCounterPartyLinkRecord.GET()
                .preload {
                    it.person()
                    it.personToCounterPartyLinkReason()
                }
                .join {
                    it.person()
                }
                .where(
                        PERSON_TO_COUNTER_PARTY_LINKS.COUNTER_PARTY_ID.eq(counterPartyId)
                                .and("{0} ~* {1}", PEOPLE.NAME, DSL.`val`(query)))
                .execute()
    }


}