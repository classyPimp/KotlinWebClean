package composers.contract.monetaryobligation

import models.monetaryobligation.MonetaryObligation
import models.monetaryobligation.MonetaryObligationRequestParametersWrapper
import models.monetaryobligation.MonetaryObligationValidator
import models.monetaryobligation.daos.MonetaryObligationDaos
import models.monetaryobligation.updaters.MonetaryObligationUpdaters
import models.monetaryobligationpart.MonetaryObligationPart
import models.monetaryobligationpart.MonetaryObligationPartRequestParametersWrapper
import models.monetaryobligationpart.factories.MonetaryObligationPartFactories
import models.monetaryobligationpart.updaters.MonetaryObligationPartUpdaters
import orm.modelUtils.exceptions.ModelNotFoundError
import orm.services.ModelInvalidException
import orm.utils.TransactionRunner
import utils.composer.ComposerBase
import utils.composer.composerexceptions.UnprocessableEntryError
import utils.requestparameters.IParam

class ContractMonetaryObligationUpdateComposer(
        val id: Long?,
        val params: IParam
) : ComposerBase() {

    class MonetaryObligationAndParamsPair {
        var model: MonetaryObligationPart? = null
        var params: MonetaryObligationPartRequestParametersWrapper? = null
    }

    lateinit var onSuccess: (MonetaryObligation)->Unit
    lateinit var onError: (MonetaryObligation)->Unit

    lateinit var monetaryObligationToUpdate: MonetaryObligation
    lateinit var wrappedParams: MonetaryObligationRequestParametersWrapper
    val monetaryObligationPartIdToModelAndParamsMap = mutableMapOf<Long, MonetaryObligationAndParamsPair>()

    override fun beforeCompose(){
        id ?: failImmediately(UnprocessableEntryError())
        findAndSetMonetaryObligationToUpdate()
        wrapParams()
        mapMonetaryObligationPartsAndCorrespondingParamsById()
        udpateMonetaryObligation()
        updateMonetaryObligationParts()
        initializeUnpersistedMonetaryObligations()
        validate()
    }

    private fun findAndSetMonetaryObligationToUpdate() {
        MonetaryObligationDaos.show.forUpdate(id!!)?.let {
            monetaryObligationToUpdate = it
        } ?: failImmediately(ModelNotFoundError())
    }

    private fun wrapParams() {
        params.get("monetaryObligation")?.let {
            wrappedParams = MonetaryObligationRequestParametersWrapper(it)
        }
    }

    private fun mapMonetaryObligationPartsAndCorrespondingParamsById() {
        wrappedParams.monetaryObligationParts?.forEach {
            monetaryObligationPartParams ->
            val id = monetaryObligationPartParams.id
            if (id != null) {
                val pairAtMap = monetaryObligationPartIdToModelAndParamsMap.get(id)
                if (pairAtMap != null) {
                    pairAtMap.params = monetaryObligationPartParams
                } else {
                    monetaryObligationPartIdToModelAndParamsMap[id] = MonetaryObligationAndParamsPair().also {
                        it.params = monetaryObligationPartParams
                    }
                }
            }
        }

        monetaryObligationToUpdate.monetaryObligationParts?.forEach {
            monetaryObligation ->
            val id = monetaryObligation.id
            if (id != null) {
                val pairAtMap = monetaryObligationPartIdToModelAndParamsMap.get(id)
                if (pairAtMap != null) {
                    pairAtMap.model = monetaryObligation
                } else {
                    monetaryObligationPartIdToModelAndParamsMap[id] = MonetaryObligationAndParamsPair().also {
                        it.model = monetaryObligation
                    }
                }
            }
        }
        monetaryObligationPartIdToModelAndParamsMap.values.forEach {
            println("${it.model} ${it.params}: [${it.params!!.id}]")
        }
    }

    private fun udpateMonetaryObligation() {
        MonetaryObligationUpdaters.default.update(monetaryObligationToUpdate, wrappedParams)
    }

    private fun updateMonetaryObligationParts() {
        monetaryObligationPartIdToModelAndParamsMap.values.forEach {
            pair ->
            MonetaryObligationPartUpdaters.default.update(pair.model!!, pair.params!!)
        }
    }

    private fun initializeUnpersistedMonetaryObligations() {
        wrappedParams.monetaryObligationParts?.filter {
            it.id == null
        }?.forEach {
            val newMonetaryObligationPart = MonetaryObligationPartFactories.whenMonetaryObligationUpdated.create(it, monetaryObligationToUpdate.id!!)
            monetaryObligationToUpdate.monetaryObligationParts!!.add(newMonetaryObligationPart)
        }
    }

    private fun validate() {
        MonetaryObligationValidator(monetaryObligationToUpdate).updateScenario()
        if (!monetaryObligationToUpdate.record.validationManager.isValid()) {
            failImmediately(ModelInvalidException())
        }
    }

    override fun compose(){
        val monetaryObligationPartsToSpare = mutableListOf<MonetaryObligationPart>()
        val monetaryObligationPartsToDestroy = mutableListOf<MonetaryObligationPart>()
        monetaryObligationToUpdate.monetaryObligationParts?.forEach {
            if (it.markedForDestruction != null && it.markedForDestruction!!) {
                monetaryObligationPartsToDestroy.add(it)
            } else {
                monetaryObligationPartsToSpare.add(it)
            }
        }

        monetaryObligationToUpdate.monetaryObligationParts = monetaryObligationPartsToSpare
        TransactionRunner.run {
            val tx = it.inTransactionDsl
            monetaryObligationToUpdate.record.saveCascade(tx)
            if (monetaryObligationPartsToDestroy.isNotEmpty()) {
                monetaryObligationPartsToDestroy.forEach {
                    it.record.delete(tx)
                }
            }
        }
    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelInvalidException -> {
                onError(
                        monetaryObligationToUpdate
                )
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(monetaryObligationToUpdate)
    }

}

