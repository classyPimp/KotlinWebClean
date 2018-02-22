package models.monetaryobligationpart

import orm.monetaryobligationpartgeneratedrepository.MonetaryObligationPartValidatorTrait

class MonetaryObligationPartValidator(model: MonetaryObligationPart) : MonetaryObligationPartValidatorTrait(model, model.record.validationManager) {

    fun createScenario(){
        validateDueDate()
        validateAmount()
    }

    private fun validateDueDate() {
        val dueDate = model.dueDate
        if (dueDate == null) {
            validationManager.addDueDateError("date should be provided or it's format is invalid")
            return
        }
    }

    private fun validateAmount() {
        val amount = model.amount
        if (amount == null) {
            validationManager.addAmountError("should be provided")
            return
        }
    }

}