import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { HasOne } from '../../modelLayer/annotations/HasOne'
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { ModelCollection } from '../../modelLayer/ModelCollection'
import { IncorporationForm } from './IncorporationForm'
import { CounterPartyToContactLink } from './CounterPartyToContactLink'
import { PersonToCounterPartyLink } from './PersonToCounterPartyLink'


export class CounterParty extends BaseModel {

    static className = "counterParty"

    @Property
    id: number

    @Property
    incorporationFormId: number

    @Property
    name: string

    @Property
    nameShort: string

    @Property
    updatedAt: string

    @Property
    createdAt: string

    @HasOne(IncorporationForm)
    incorporationForm: IncorporationForm

    @HasMany(CounterPartyToContactLink)
    counterPartyContacts: ModelCollection<CounterPartyToContactLink>

    @HasMany(PersonToCounterPartyLink)
    personToCounterPartyLinks: ModelCollection<PersonToCounterPartyLink>

}