package composers.persontocounterpartylinktouploadeddoclinkreasons

import utils.requestparameters.IParam

/**
 * Created by Муса on 10.01.2018.
 */
object PersonToCounterPartyLinkToUploadedDocLinkReasonComposer {

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