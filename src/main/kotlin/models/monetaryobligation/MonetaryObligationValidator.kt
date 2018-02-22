package models.monetaryobligation

import models.contract.daos.ContractDaos
import models.monetaryobligationpart.MonetaryObligationPart
import models.monetaryobligationpart.MonetaryObligationPartValidator
import orm.monetaryobligationgeneratedrepository.MonetaryObligationValidatorTrait

class MonetaryObligationValidator(model: MonetaryObligation) : MonetaryObligationValidatorTrait(model, model.record.validationManager) {

    fun createScenario(){
        validateMonetaryObligationParts()
        validateTotalAmount()
        validateContractId()
    }

    fun validateMonetaryObligationParts() {
        val monetaryObligationParts = model.monetaryObligationParts
        if (monetaryObligationParts == null) {
            validationManager.addMonetaryObligationPartsError("at least one payment should be specified")
            return
        }
        var hasNestedErrors = false
        monetaryObligationParts.forEach {
            MonetaryObligationPartValidator(it).createScenario()
            if (!it.record.validationManager.isValid()) {
                hasNestedErrors = true
            }
        }
        if (hasNestedErrors) {
            validationManager.addError("nestedErrors", "nested errors")
        }
    }

    fun validateTotalAmount() {
        val totalAmount = model.totalAmount
        if (totalAmount == null) {
            validationManager.addTotalAmountError("total amount should be specified or it is invalid")
            return
        }
        var monetaryObligationPartsAmountSum: Long = 0
        val monetaryObligationParts = model.monetaryObligationParts
        if (monetaryObligationParts == null) {
            return
        }
        monetaryObligationParts.forEach {
            val amount = it.amount
            if (amount != null) {
                monetaryObligationPartsAmountSum += amount
            }
        }
        if (monetaryObligationPartsAmountSum != totalAmount) {
            validationManager.addTotalAmountError(
                    "amount is invalid, sum of provided payments is not correct. " +
                            "total amount = ${totalAmount}" +
                    " and sum of payments is ${monetaryObligationPartsAmountSum}" +
                            " difference is = ${totalAmount - monetaryObligationPartsAmountSum}"
            )
            return
        }
    }

    private fun validateContractId() {
        val contractId = model.contractId
        if (contractId == null) {
            throw IllegalStateException()
        }
        val contractExists = ContractDaos.show.exists(contractId)
        if (!contractExists) {
            validationManager.addGeneralError("no such contract")
        }
    }

}