package models.incorporationform

import org.jooq.generated.Tables.INCORPORATION_FORMS
import orm.incorporationformgeneratedrepository.IncorporationFormRecord
import orm.incorporationformgeneratedrepository.IncorporationFormValidatorTrait

class IncorporationFormValidator(model: IncorporationForm) : IncorporationFormValidatorTrait(model, model.record.validationManager) {

    fun createScenario(){
        validateName()
        validateNameShort()
    }

    fun updateScenario(){
        createScenario()
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

        IncorporationFormRecord.GET().where(
                INCORPORATION_FORMS.NAME.eq(name)
        ).execute().firstOrNull()?.let {
            model.record.validationManager.addNameError("already exists")
        }
    }

    fun validateNameShort() {
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

        IncorporationFormRecord.GET().where(
                INCORPORATION_FORMS.NAME_SHORT.eq(nameShort)
        ).execute().firstOrNull()?.let {
            model.record.validationManager.addNameError("already exists")
            return
        }
    }

}