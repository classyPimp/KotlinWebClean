import { CounterParty } from './CounterParty';
import { ModelCollection } from '../../modelLayer/ModelCollection';
import { BaseModel } from '../../modelLayer/BaseModel';
import { Property } from '../../modelLayer/annotations/Property';
import { HasMany } from '../../modelLayer/annotations/HasMany';


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