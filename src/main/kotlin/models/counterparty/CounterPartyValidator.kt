package models.counterparty

import org.jooq.generated.Tables.INCORPORATION_FORMS
import orm.counterpartygeneratedrepository.CounterPartyValidatorTrait
import orm.incorporationformgeneratedrepository.IncorporationFormRecord

class CounterPartyValidator(model: CounterParty) : CounterPartyValidatorTrait(model, model.record.validationManager) {

    fun createScenario(){
        validateName()
        validateNameShort()
        validateIncorporationFormId()
    }

    fun updateScenario() {
        model.record.propertiesChangeTracker.let {
            if (it.nameIsChanged) {
                validateName()
            }
            if (it.nameShortIsChanged) {
                validateNameShort()
            }
            if (it.incorporationFormIdIsChanged) {
                validateIncorporationFormId()
            }
        }
    }

    fun validateName(){
        val name = model.name
        val tester = nameTester()

        if (name.isNullOrBlank() || name == null) {
            model.record.validationManager.addNameError("cannot be empty")
            return
        }

        tester.let {
            if(!it.shouldBeLongerThan(name, 1)) {
                return
            }
        }
    }

    fun validateNameShort(){
        val nameShort = model.nameShort
        val tester = nameShortTester()

        if (nameShort.isNullOrBlank() || nameShort == null) {
            model.record.validationManager.addNameShortError("cannot be null")
            return
        }

        tester.let {
            if (!it.shouldBeLongerThan(nameShort, 1)) {
                return
            }
        }
    }

    fun validateIncorporationFormId(){
        val incorporationFormId = model.incorporationFormId

        if (incorporationFormId == null) {
            model.record.validationManager.addIncorporationFormIdError("should be assigned")
            return
        }

        val incorporationForm = IncorporationFormRecord.GET().where(
                INCORPORATION_FORMS.ID.eq(incorporationFormId)
        ).execute().firstOrNull()

        if (incorporationForm == null) {
            model.record.validationManager.addIncorporationFormIdError("no such incorporation form")
            return
        }
    }

}