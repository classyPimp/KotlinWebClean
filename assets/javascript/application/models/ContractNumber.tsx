import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { HasOne } from '../../modelLayer/annotations/HasOne'
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { ModelCollection } from '../../modelLayer/ModelCollection'
import { RequestOptions, Route } from '../../modelLayer/annotations/ModelRoute'
import { Contract } from './Contract'

export class ContractNumber extends BaseModel {

    static className = "contractNumber"

    @Property
    id: number

    @Property
    internalNumber: number

    @Property
    numberAssignedByCounterParty: number

    @Property
    assignedNumber: number

    @Property
    updatedAt: string

    @Property
    createdAt: string

    @HasOne("Contract")
    contract: Contract

}