import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { HasOne } from '../../modelLayer/annotations/HasOne'
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { ModelCollection } from '../../modelLayer/ModelCollection'
import { RequestOptions, Route } from '../../modelLayer/annotations/ModelRoute'
import { CounterParty } from './CounterParty'
import { Contract } from './Contract'

export class ContractToCounterPartyLink extends BaseModel {

    static className = "contractToCounterPartyLink"

    @Property
    id: number

    @Property
    counterPartyId: number

    @Property
    contractId: number  

    @Property
    roleAccordingToContract: string  

    @Property
    updatedAt: string

    @Property
    createdAt: string

    @HasOne("CounterParty")
    counterParty: CounterParty

    @HasOne("Contract")
    contract: Contract

}