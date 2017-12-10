package models.${classNameLowerCase}

import orm.${classNameLowerCase}generatedrepository.${className}ValidatorTrait

class ${className}Validator(model: ${className}) : ${className}ValidatorTrait(model, model.record.validationManager) {

    fun createScenario(){
        //
    }

}