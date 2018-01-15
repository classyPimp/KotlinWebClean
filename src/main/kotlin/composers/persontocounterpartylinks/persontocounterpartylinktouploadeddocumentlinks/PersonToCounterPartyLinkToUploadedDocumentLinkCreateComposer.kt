package composers.persontocounterpartylinks.persontocounterpartylinktouploadeddocumentlinks

import models.person.Person
import models.persontocounterpartylink.PersonToCounterPartyLink
import models.persontocounterpartylink.daos.PersonToCounterPartyLinkDaos
import models.persontocounterpartylinktouploadeddocumentlink.PersonToCounterPartyLinkToUploadedDocumentLink
import models.persontocounterpartylinktouploadeddocumentlink.PersonToCounterPartyLinkToUploadedDocumentLinkRequestParametersWrapper
import orm.modelUtils.exceptions.ModelNotFoundError
import utils.composer.ComposerBase
import utils.composer.composerexceptions.UnprocessableEntryError
import utils.requestparameters.IParam

class PersonToCounterPartyLinkToUploadedDocumentLinkCreateComposer(val params: IParam, val personToCounterPartyLinkId: Long?) : ComposerBase() {

    lateinit var onSuccess: (PersonToCounterPartyLinkToUploadedDocumentLink)->Unit
    lateinit var onError: (PersonToCounterPartyLinkToUploadedDocumentLink)->Unit

    lateinit var personToCounterPartyLink: PersonToCounterPartyLink
    lateinit var linkBeingCreated: PersonToCounterPartyLinkToUploadedDocumentLink
    lateinit var wrappedParams: PersonToCounterPartyLinkToUploadedDocumentLinkRequestParametersWrapper

    override fun beforeCompose(){
        personToCounterPartyLinkId ?: failImmediately(UnprocessableEntryError())
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
        } ?: failImmediately(UnprocessableEntryError())
    }

    private fun build(){

    }

    private fun validate() {

    }

    override fun compose(){

    }

    override fun fail(error: Throwable) {
        when(error) {

            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(linkBeingCreated)
    }

}

