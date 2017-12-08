import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { HasOne } from '../../modelLayer/annotations/HasOne'
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { ModelCollection } from '../../modelLayer/ModelCollection'
import { CounterParty } from './CounterParty'

export class IncorporationForm extends BaseModel {

    static className = "incorporationForm"

    @Property
    id: number

    @Property
    name: string

    @Property
    nameShort: string

    @Property
    updatedAt: string

    @Property
    createdAt: string

    @HasMany(CounterParty)
    counterParties: ModelCollection<CounterParty>

}