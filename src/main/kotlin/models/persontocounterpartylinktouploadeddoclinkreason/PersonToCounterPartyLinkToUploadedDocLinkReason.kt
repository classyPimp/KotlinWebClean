package models.persontocounterpartylinktouploadeddoclinkreason

import models.persontocounterpartylinktouploadeddocumentlink.PersonToCounterPartyLinkToUploadedDocumentLink
import org.jooq.generated.tables.PersonToCounterPartyLinkToUploadedDocLinkReasons
import orm.annotations.HasMany
import orm.annotations.IsModel
import orm.annotations.IsPrimaryKey
import orm.annotations.TableField
import orm.persontocounterpartylinktouploadeddoclinkreasongeneratedrepository.PersonToCounterPartyLinkToUploadedDocLinkReasonRecord
import java.sql.Timestamp

/**
 * Created by Муса on 06.12.2017.
 */
@IsModel(jooqTable = PersonToCounterPartyLinkToUploadedDocLinkReasons::class)
class PersonToCounterPartyLinkToUploadedDocLinkReason {

    val record: PersonToCounterPartyLinkToUploadedDocLinkReasonRecord by lazy { PersonToCounterPartyLinkToUploadedDocLinkReasonRecord(this) }

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    @TableField(name = "REASON_NAME")
    var reasonName: String? = null

    @HasMany(model = PersonToCounterPartyLinkToUploadedDocumentLink::class, fieldOnThis = "ID", fieldOnThat = "PERSON_TO_COUNTER_PARTY_LINK_TO_UPLOADED_DOC_LINK_REASON_ID")
    var personToCounterPartyLinkToUploadedDocumentLinks: MutableList<PersonToCounterPartyLinkToUploadedDocumentLink>? = null

}