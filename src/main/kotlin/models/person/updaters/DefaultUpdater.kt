package models.person.updaters

import models.person.Person
import models.person.PersonRequestParametersWrapper


object DefaultUpdater {

    fun update(model: Person, params: PersonRequestParametersWrapper) {
        model.record.name = params.name
    }

}