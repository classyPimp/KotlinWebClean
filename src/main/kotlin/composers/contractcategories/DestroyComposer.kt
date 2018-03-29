package composers.contractcategories

import models.contractcategory.ContractCategory
import models.contractcategory.daos.ContractCategoryDaos
import orm.modelUtils.exceptions.ModelNotFoundError
import utils.composer.ComposerBase
import utils.composer.composerexceptions.BadRequestError

class DestroyComposer(val id: Long?) : ComposerBase() {

    lateinit var onSuccess: (ContractCategory)->Unit
    lateinit var onError: (ContractCategory)->Unit

    lateinit var contractCategoryToDestroy: ContractCategory

    override fun beforeCompose(){
        id ?: failImmediately(BadRequestError())
        findAndSetContractCategoryToDestroy()
    }

    private fun findAndSetContractCategoryToDestroy() {
        ContractCategoryDaos.show.forDestroy(id!!)?.let {
            contractCategoryToDestroy = it
        } ?: failImmediately(ModelNotFoundError())
    }

    override fun compose(){
        contractCategoryToDestroy.record.delete()
    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelNotFoundError -> {
                onError(
                        ContractCategory().also {
                            it.record.validationManager.addGeneralError("can't delete: no such category")
                        }
                )
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(contractCategoryToDestroy)
    }

}

