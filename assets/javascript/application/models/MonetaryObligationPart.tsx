import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { HasOne } from '../../modelLayer/annotations/HasOne'
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { ModelCollection } from '../../modelLayer/ModelCollection'
import { RequestOptions, Route } from '../../modelLayer/annotations/ModelRoute'
import { MonetaryObligation } from './MonetaryObligation'


export class MonetaryObligationPart extends BaseModel {

    static className = "monetaryObligationPart"

    arbitrary: {[id: string]: any}

    @Property
    id: number

    @Property
    amount: number

    @Property
    monetaryObligationId: number

    @Property
    dueDate: string

    @Property
    fulfilledAmount: number

    @Property
    updatedAt: string

    @Property
    createdAt: string

    @HasOne("MonetaryObligation")
    monetaryObligation: MonetaryObligation

}