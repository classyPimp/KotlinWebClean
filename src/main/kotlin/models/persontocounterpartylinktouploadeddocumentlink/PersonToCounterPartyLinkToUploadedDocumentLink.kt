package models.persontocounterpartylinktouploadeddocumentlink

import models.persontocounterpartylink.PersonToCounterPartyLink
import models.persontocounterpartylinktouploadeddoclinkreason.PersonToCounterPartyLinkToUploadedDocLinkReason
import models.uploadeddocument.UploadedDocument
import org.jooq.generated.tables.PersonToCounterPartyLinkToUploadedDocumentLinks
import orm.annotations.BelongsTo
import orm.annotations.IsModel
import orm.annotations.IsPrimaryKey
import orm.annotations.TableField
import orm.persontocounterpartylinktouploadeddocumentlinkgeneratedrepository.PersonToCounterPartyLinkToUploadedDocumentLinkRecord
import java.sql.Timestamp

/**
 * Created by Муса on 05.12.2017.
 */
@IsModel(jooqTable = PersonToCounterPartyLinkToUploadedDocumentLinks::class)
class PersonToCounterPartyLinkToUploadedDocumentLink {

    val record: PersonToCounterPartyLinkToUploadedDocumentLinkRecord by lazy { PersonToCounterPartyLinkToUploadedDocumentLinkRecord(this) }

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "PERSON_TO_COUNTER_PARTY_LINK_ID")
    var personToCounterPartyLinkId: Long? = null

    @TableField(name = "UPLOADED_DOCUMENT_ID")
    var uploadedDocumentId: Long? = null

    @TableField(name = "PERSON_TO_COUNTER_PARTY_LINK_TO_UPLOADED_DOC_LINK_REASON_ID")
    var personToCounterPartyLinkToUploadedDocLinkReasonId: Long? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    @BelongsTo(model = PersonToCounterPartyLink::class, fieldOnThis = "PERSON_TO_COUNTER_PARTY_LINK_ID", fieldOnThat = "ID")
    var personToCounterPartyLink: PersonToCounterPartyLink? = null

    @BelongsTo(model = UploadedDocument::class, fieldOnThat = "ID", fieldOnThis = "UPLOADED_DOCUMENT_ID")
    var uploadedDocument: UploadedDocument? = null

    @BelongsTo(model = PersonToCounterPartyLinkToUploadedDocLinkReason::class, fieldOnThat = "ID", fieldOnThis = "PERSON_TO_COUNTER_PARTY_LINK_TO_UPLOADED_DOC_LINK_REASON_ID")
    var personToCounterPartyLinkToUploadedDocLinkReason: PersonToCounterPartyLinkToUploadedDocLinkReason? = null

}