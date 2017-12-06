import { BaseModel } from '../../modelLayer/BaseModel';
import { Property } from '../../modelLayer/annotations/Property';
import { HasOne } from '../../modelLayer/annotations/HasOne';
import { IncorporationForm } from './IncorporationForm';
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { CounterPartyContact } from './CounterPartyContact'
import { ModelCollection } from '../../modelLayer/ModelCollection'


export class CounterParty extends BaseModel {

    static className = "company"

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

    @HasMany(CounterPartyContact)
    counterPartyContacts: ModelCollection<CounterPartyContact>

}