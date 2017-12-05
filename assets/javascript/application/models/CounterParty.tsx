import { BaseModel } from '../../modelLayer/BaseModel';
import { Property } from '../../modelLayer/annotations/Property';
import { HasOne } from '../../modelLayer/annotations/HasOne';
import { IncorporationForm } from './IncorporationForm';


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

    @HasOne(IncorporationForm)
    incorporationForm: IncorporationForm

}