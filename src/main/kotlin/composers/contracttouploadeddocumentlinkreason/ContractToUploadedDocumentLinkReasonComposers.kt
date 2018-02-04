package composers.contracttouploadeddocumentlinkreason

import utils.requestparameters.IParam

object ContractToUploadedDocumentLinkReasonComposers {

    fun create(params: IParam): CreateComposer {
        return CreateComposer(params)
    }

    fun update(params: IParam, id: Long?): UpdateComposer {
        return UpdateComposer(params, id)
    }

    fun destroy(id: Long?): DestroyComposer {
        return DestroyComposer(id)
    }

}