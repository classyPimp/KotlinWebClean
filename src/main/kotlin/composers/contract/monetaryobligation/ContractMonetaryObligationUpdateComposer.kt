package composers.contract.monetaryobligation

import utils.composer.ComposerBase
import utils.requestparameters.IParam

class ContractMonetaryObligationUpdateComposer(
        val contractId: Long?,
        val id: Long?,
        val params: IParam
) : ComposerBase() {

    lateinit var onSuccess: ()->Unit
    lateinit var onError: ()->Unit

    override fun beforeCompose(){

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
        onSuccess.invoke()
    }

}

