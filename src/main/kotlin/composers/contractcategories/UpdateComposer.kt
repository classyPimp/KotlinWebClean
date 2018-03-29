package composers.contractcategories

import models.contractcategory.ContractCategory
import models.contractcategory.ContractCategoryRequestParametersWrapper
import models.contractcategory.ContractCategoryValidator
import models.contractcategory.daos.ContractCategoryDaos
import models.contractcategory.updaters.ContractCategoryUpdaters
import orm.modelUtils.exceptions.ModelNotFoundError
import orm.services.ModelInvalidError
import utils.composer.ComposerBase
import utils.composer.composerexceptions.BadRequestError
import utils.requestparameters.IParam

class UpdateComposer(val params: IParam, val id: Long?) : ComposerBase() {

    lateinit var onSuccess: (ContractCategory)->Unit
    lateinit var onError: (ContractCategory)->Unit

    lateinit var contractCategoryToUpdate: ContractCategory
    lateinit var wrappedParams: ContractCategoryRequestParametersWrapper

    override fun beforeCompose(){
        id ?: failImmediately(BadRequestError())
        wrapParams()
        findAndSetContractCategoryToUpdate()
        update()
        validate()
    }

    private fun wrapParams() {
        params.get("contractCategory")?.let {
            wrappedParams = ContractCategoryRequestParametersWrapper(it)
        } ?: failImmediately(BadRequestError())
    }

    private fun findAndSetContractCategoryToUpdate() {
        ContractCategoryDaos.show.forUpdate(id!!)?.let {
            contractCategoryToUpdate = it
        } ?: failImmediately(ModelNotFoundError())
    }

    private fun update() {
        ContractCategoryUpdaters.default.update(contractCategoryToUpdate, wrappedParams)
    }

    private fun validate() {
        ContractCategoryValidator(contractCategoryToUpdate).updateScenario()

    }

    override fun compose(){
        contractCategoryToUpdate.record.save()
    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelNotFoundError -> {
                onError(
                        ContractCategory().also {
                            it.record.validationManager.addGeneralError("can't update no such category")
                        }
                )
            }
            is ModelInvalidError -> {
                onError(
                        contractCategoryToUpdate
                )
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(contractCategoryToUpdate)
    }

}

