package models.counterpartytocontactlink

import orm.counterpartytocontactlinkgeneratedrepository.CounterPartyToContactLinkValidatorTrait

class CounterPartyToContactLinkValidator(model: CounterPartyToContactLink) : CounterPartyToContactLinkValidatorTrait(model, model.record.validationManager) {

    fun createScenario(){
        //
    }

}