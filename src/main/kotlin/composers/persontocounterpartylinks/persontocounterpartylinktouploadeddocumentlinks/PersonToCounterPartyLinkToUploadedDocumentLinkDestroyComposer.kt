package composers.persontocounterpartylinks.persontocounterpartylinktouploadeddocumentlinks

import models.persontocounterpartylinktouploadeddocumentlink.PersonToCounterPartyLinkToUploadedDocumentLink
import models.persontocounterpartylinktouploadeddocumentlink.daos.PersonToCounterPartyLinkToUploadedDocumentLinkDaos
import orm.modelUtils.exceptions.ModelNotFoundError
import orm.utils.TransactionRunner
import utils.composer.ComposerBase
import utils.composer.composerexceptions.UnprocessableEntryError

class PersonToCounterPartyLinkToUploadedDocumentLinkDestroyComposer(
        val personToCounterPartyLinkId: Long?,
        val id: Long?
) : ComposerBase() {

    lateinit var onSuccess: (PersonToCounterPartyLinkToUploadedDocumentLink)->Unit
    lateinit var onError: (PersonToCounterPartyLinkToUploadedDocumentLink)->Unit

    lateinit var linkToBeDestroyed: PersonToCounterPartyLinkToUploadedDocumentLink

    override fun beforeCompose(){
        if (id == null || personToCounterPartyLinkId == null) {
            failImmediately(UnprocessableEntryError())
        }
        findAndSetLinkToBeDestroyed()
    }

    private fun findAndSetLinkToBeDestroyed() {
        PersonToCounterPartyLinkToUploadedDocumentLinkDaos.show.forDestroy(
                personToCounterPartyLinkId = personToCounterPartyLinkId!!,
                id = id!!
        )?.also {
            linkToBeDestroyed = it
        } ?: failImmediately(ModelNotFoundError())
    }

    override fun compose(){
        TransactionRunner.run {
            tx ->
            linkToBeDestroyed.record.delete(tx.inTransactionDsl)
            linkToBeDestroyed.uploadedDocument?.record?.let {
                it.delete(
                        before = {
                            it.model.file.delete()
                        },
                        after = {
                            it.model.file.finalizeOperation()
                        },
                        dslContext = tx.inTransactionDsl
                )
            }
        }
    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelNotFoundError -> {
                onError(
                        PersonToCounterPartyLinkToUploadedDocumentLink().also {
                            it.record.validationManager.addGeneralError("no such link")
                        }
                )
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(linkToBeDestroyed)
    }

}

