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
    
    @Route("DELETE", {url: "/api/contracts/:contractId/contractToCounterPartyLinks/:id", defaultWilds: ["contractId", "id"]})
    destroy: (options?: RequestOptions) => Promise<ContractToCounterPartyLink>

    @Route("PUT", {url: "/api/contracts/:contractId/contractToCounterPartyLinks/:id/replaceWith/:counterPartyIdToReplaceWith", defaultWilds: ["contractId", "id"]})
    replace: (options?: RequestOptions) => Promise<ContractToCounterPartyLink>

    afterReplaceRequest(options: RequestOptions) {
      this.afterCreateRequest(options)
    }

    @Route("POST", {url: "/api/contracts/:contractId/contractToCounterPartyLinks", defaultWilds: ["contractId"]})
    create: (options?: RequestOptions) => Promise<ContractToCounterPartyLink>

    @Route("PUT", {url: "/api/contracts/:contractId/contractToCounterPartyLinks/:id", defaultWilds: ["contractId", "id"]})
    forContractManageUpdate: (options?: RequestOptions) => Promise<ContractToCounterPartyLink>

    beforeForContractManageUpdateRequest(options: RequestOptions) {
      this.beforeUpdateRequest(options)
    }

    afterForContractManageUpdateRequest(options: RequestOptions) {
      this.afterUpdateRequest(options)
    }

    @Route("GET", {url: "/api/contracts/:contractId/contractToCounterPartyLinks"})
    static forContractIndex: (options?: RequestOptions) => Promise<ContractToCounterPartyLink>

    static afterForContractIndex(options: RequestOptions) {
      this.afterIndexRequest(options)
    }

    @Route("GET", {url: "/api/contracts/:contractId/contractToCounterPartyLinks/edit"})
    static forContractIndexEdit: (options?: RequestOptions) => Promise<ModelCollection<ContractToCounterPartyLink>>

    static afterForContractIndexEditRequest(options: RequestOptions) {
      this.afterIndexRequest(options)
    }

}   