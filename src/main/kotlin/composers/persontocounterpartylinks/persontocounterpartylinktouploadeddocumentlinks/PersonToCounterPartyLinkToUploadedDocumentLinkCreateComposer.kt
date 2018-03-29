package composers.persontocounterpartylinks.persontocounterpartylinktouploadeddocumentlinks

import models.persontocounterpartylink.PersonToCounterPartyLink
import models.persontocounterpartylink.daos.PersonToCounterPartyLinkDaos
import models.persontocounterpartylinktouploadeddocumentlink.PersonToCounterPartyLinkToUploadedDocumentLink
import models.persontocounterpartylinktouploadeddocumentlink.PersonToCounterPartyLinkToUploadedDocumentLinkRequestParametersWrapper
import models.persontocounterpartylinktouploadeddocumentlink.PersonToCounterPartyLinkToUploadedDocumentLinkValidator
import models.persontocounterpartylinktouploadeddocumentlink.factories.PersonToCounterPartyLinkToUploadedDocumentLinkFactories
import orm.modelUtils.exceptions.ModelNotFoundError
import orm.services.ModelInvalidError
import orm.utils.TransactionRunner
import utils.composer.ComposerBase
import utils.composer.composerexceptions.BadRequestError
import utils.requestparameters.IParam

class PersonToCounterPartyLinkToUploadedDocumentLinkCreateComposer(val params: IParam, val personToCounterPartyLinkId: Long?) : ComposerBase() {

    lateinit var onSuccess: (PersonToCounterPartyLinkToUploadedDocumentLink)->Unit
    lateinit var onError: (PersonToCounterPartyLinkToUploadedDocumentLink)->Unit

    lateinit var personToCounterPartyLink: PersonToCounterPartyLink
    lateinit var linkBeingCreated: PersonToCounterPartyLinkToUploadedDocumentLink
    lateinit var wrappedParams: PersonToCounterPartyLinkToUploadedDocumentLinkRequestParametersWrapper

    override fun beforeCompose(){
        personToCounterPartyLinkId ?: failImmediately(BadRequestError())
        findAndSetPersonToCounterPartyLink()
        wrapParams()
        build()
        validate()
    }

    private fun findAndSetPersonToCounterPartyLink() {
        PersonToCounterPartyLinkDaos.show.forPersonToCounterPartyLinkToUploadedDocumentLinkCreateById(personToCounterPartyLinkId!!)?.let {
            personToCounterPartyLink = it
        } ?: failImmediately(ModelNotFoundError())
    }

    private fun wrapParams() {
        params.get("personToCounterPartyLinkToUploadedDocumentLink")?.let {
            wrappedParams = PersonToCounterPartyLinkToUploadedDocumentLinkRequestParametersWrapper(it)
        } ?: failImmediately(BadRequestError())
    }

    private fun build(){
        linkBeingCreated = PersonToCounterPartyLinkToUploadedDocumentLinkFactories.defaultCreate.create(wrappedParams).also {
            it.personToCounterPartyLinkId = personToCounterPartyLink.id
            it.personToCounterPartyLink = personToCounterPartyLink
        }
    }

    private fun validate() {
        PersonToCounterPartyLinkToUploadedDocumentLinkValidator(linkBeingCreated).createScenario()
        if (!linkBeingCreated.record.validationManager.isValid()) {
            failImmediately(ModelInvalidError())
        }
    }

    override fun compose(){
        val uploadedDocument = linkBeingCreated.uploadedDocument!!.also {
            it.personToCounterPartyLinkToUploadedDocumentLinks = mutableListOf(linkBeingCreated)
        }

        TransactionRunner.run {
            uploadedDocument.record.saveCascade(
                before = {
                },
                after = {
                    record ->
                    record.file.finalizeOperation()
                },
                   dslContext = it.inTransactionDsl
            )
            println(uploadedDocument.personToCounterPartyLinkToUploadedDocumentLinks?.forEach {
                println("ptcptudl.id: ${it.id} - ${it.uploadedDocumentId}")
            })
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
            is ModelInvalidError -> {
                onError(
                        linkBeingCreated
                )
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(linkBeingCreated)
    }

}

