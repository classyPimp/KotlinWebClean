package composers.persontocounterpartylinks

import composers.persontocounterpartylinks.persontocounterpartylinktouploadeddocumentlinks.PersonToCounterPartyLinkToUploadedDocumentLinkCreateComposer
import composers.persontocounterpartylinks.persontocounterpartylinktouploadeddocumentlinks.PersonToCounterPartyLinkToUploadedDocumentLinkDestroyComposer
import composers.persontocounterpartylinks.persontocounterpartylinktouploadeddocumentlinks.PersonToCounterPartyLinkToUploadedDocumentLinkUpdateComposer
import utils.requestparameters.IParam

/**
 * Created by Муса on 04.01.2018.
 */
object PersonToCounterPartyLinksComposers {

    object PersonToCounterPartyLinkToUploadedDocumentLinks {
        fun create(params: IParam, personToCounterPartyLinkId: Long?): PersonToCounterPartyLinkToUploadedDocumentLinkCreateComposer {
            return PersonToCounterPartyLinkToUploadedDocumentLinkCreateComposer(params, personToCounterPartyLinkId)
        }

        fun destroy(personToCounterPartyLinkId: Long?, id: Long?): PersonToCounterPartyLinkToUploadedDocumentLinkDestroyComposer {
            return PersonToCounterPartyLinkToUploadedDocumentLinkDestroyComposer(personToCounterPartyLinkId, id)
        }

        fun update(params: IParam, personToCounterPartyLinkId: Long?, id: Long?): PersonToCounterPartyLinkToUploadedDocumentLinkUpdateComposer {
            return PersonToCounterPartyLinkToUploadedDocumentLinkUpdateComposer(params, personToCounterPartyLinkId, id)
        }
    }

    fun create(params: IParam): Create {
        return Create(params)
    }

    fun update(params: IParam, id: Long?): Update {
        return Update(params, id)
    }

    fun destroy(id: Long?): Destroy {
        return Destroy(id)
    }

}