import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { HasOne } from '../../modelLayer/annotations/HasOne'
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { ModelCollection } from '../../modelLayer/ModelCollection'
import { RequestOptions, Route } from '../../modelLayer/annotations/ModelRoute'
import { Contract } from './Contract'

export class ContractStatus extends BaseModel {

    static className = "contractStatus"

    @Property
    id: number

    @Property
    isCommited: boolean

    @Property
    isSypplement: boolean

    @Property   
    parentContractId: number

    @Property
    rootContractId: number

    @Property
    isProject: boolean

    @Property
    isCancelled: boolean

    @Property
    validSince: string

    @Property
    validTo: string

    @Property
    isCompleted: boolean    

    @Property
    updatedAt: string

    @Property
    createdAt: string

    @HasOne("Contract")
    contract: Contract

    @HasOne("Contract")
    parentContract: Contract

    @HasOne("Contract")
    rootContrac: Contract

}