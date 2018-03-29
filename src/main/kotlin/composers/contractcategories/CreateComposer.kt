package composers.contractcategories

import models.contractcategory.ContractCategory
import models.contractcategory.ContractCategoryRequestParametersWrapper
import models.contractcategory.ContractCategoryValidator
import models.contractcategory.factories.ContractCategoryFactories
import orm.services.ModelInvalidError
import utils.composer.ComposerBase
import utils.composer.composerexceptions.BadRequestError
import utils.requestparameters.IParam

class CreateComposer(val params: IParam) : ComposerBase() {

    lateinit var onSuccess: (ContractCategory)->Unit
    lateinit var onError: (ContractCategory)->Unit

    lateinit var wrappedParams: ContractCategoryRequestParametersWrapper
    lateinit var contractCategoryToCreate: ContractCategory

    override fun beforeCompose(){
        wrapParams()
        build()
        validate()
    }

    private fun wrapParams() {
        params.get("contractCategory")?.let {
            wrappedParams = ContractCategoryRequestParametersWrapper(it)
        } ?: failImmediately(BadRequestError())
    }

    private fun build() {
        contractCategoryToCreate = ContractCategoryFactories.default.create(wrappedParams)
    }

    private fun validate() {
        ContractCategoryValidator(contractCategoryToCreate).createScenario()
        if (!contractCategoryToCreate.record.validationManager.isValid()) {
            failImmediately(ModelInvalidError())
        }
    }

    override fun compose(){
        contractCategoryToCreate.record.save()
    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelInvalidError -> {
                onError(contractCategoryToCreate)
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(contractCategoryToCreate)
    }

}

