import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { HasOne } from '../../modelLayer/annotations/HasOne'
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { Contact} from './Contact'
import { CounterParty } from './CounterParty'
import  { ModelRegistry } from '../../modelLayer/ModelRegistry' 

export class CounterPartyToContactLink extends BaseModel {

    static className = "counterPartyToContactLink"

    @Property
    id: number

    @Property
    counterPartyId: number

    @Property
    contactId: number    

    @Property
    updatedAt: string

    @Property
    createdAt: string

    @HasOne("Contact")
    contact: Contact

    @HasOne("CounterParty")
    counterParty: CounterParty

}

ModelRegistry.register("CounterPartyToContactLink", CounterPartyToContactLink)