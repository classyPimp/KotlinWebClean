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
        validateDescription()
    }

    fun updateScenario() {
        model.record.propertiesChangeTracker.let {
            if (it.descriptionIsChanged) {
                validateDescription()
            }
        }
        validateTotalAmountIntegrityOnUpdate()
        validateMonetaryObligationPartsOnUpdate()
    }

    private fun validateTotalAmountIntegrityOnUpdate() {
        var sum: Long = 0
        model.monetaryObligationParts?.forEach {
            println("part id ${it.id} markedForDestruction: ${it.markedForDestruction}")
        }
        model.monetaryObligationParts?.forEach {
            if (it.markedForDestruction == null || it.markedForDestruction == false) {
                it.amount?.let {
                    sum += it
                }
            }
        }
        val totalAmount = model.totalAmount
        if (totalAmount != null) {
            if (totalAmount != sum) {
                validationManager.addGeneralError("undistributed amount: ${totalAmount - sum}")
            }
        } else {
            validationManager.addTotalAmountError("should be provided")
        }
    }

    private fun validateMonetaryObligationPartsOnUpdate() {
        val monetaryObligationParts = model.monetaryObligationParts
        if (monetaryObligationParts == null) {
            throw IllegalStateException("no monetaryObligationParts on update")
        }
        var nestedErrors = false
        monetaryObligationParts.forEach {
            MonetaryObligationPartValidator(it).forMonetaryObligationUpdateScenario()
            if (!it.record.validationManager.isValid()) {
                nestedErrors = true
            }
        }
        if (nestedErrors) {
            validationManager.markAsHasNestedErrors()
        }
    }

    private fun validateMonetaryObligationParts() {
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
            validationManager.markAsHasNestedErrors()
        }
    }

    private fun validateTotalAmount() {
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

    private fun validateDescription() {
        val description = model.description
        if (description == null || description.isBlank()) {
            validationManager.addDescriptionError("should be provided")
        }
    }

}