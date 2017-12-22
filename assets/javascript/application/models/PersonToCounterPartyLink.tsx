import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { HasOne } from '../../modelLayer/annotations/HasOne'
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { Person } from './Person'
import { CounterParty } from './CounterParty'
import { PersonToCounterPartyLinkReason } from './PersonToCounterPartyLinkReason'
import  { ModelRegistry } from '../../modelLayer/ModelRegistry' 


export class PersonToCounterPartyLink extends BaseModel {

    static className = "personToCounterPartyLink"

    @Property
    id: number

    @Property
    personId: number

    @Property
    counterPartyId: number

    @Property
    personToCounterPartyLinkReasonId: number

    @Property
    specificDetails: string

    @Property
    updatedAt: string

    @Property
    createdAt: string

    @HasOne("Person")
    person: Person

    @HasOne("CounterParty")
    counterParty: CounterParty

    @HasOne("PersonToCounterPartyLinkReason")
    personToCounterPartyLinkReason: PersonToCounterPartyLinkReason

}

ModelRegistry.register("PersonToCounterPartyLink", PersonToCounterPartyLink)