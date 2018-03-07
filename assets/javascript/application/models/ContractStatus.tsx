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
    isSupplement: boolean

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


    @Route("GET", {url: "/api/contracts/:contractId/contractStatuses"})
    static forContractShow: (options?: RequestOptions) => Promise<ContractStatus>

    static afterForContractShowRequest(options: RequestOptions) {
      this.afterShowRequest(options)
    }    

    @Route("GET", {url: "/api/contracts/:contractId/contractStatuses/edit"})
    static editForContract: (options?: RequestOptions) => Promise<ContractStatus>

    static afterEditForContractRequest(options: RequestOptions) {
      this.afterEditRequest(options)
    }

    @Route("PUT", {url: "/api/contracts/:contractId/contractStatuses/:id", defaultWilds: ["id", "contractId"]})
    forContractUpdate: (options?: RequestOptions) => Promise<ContractStatus>

    beforeForContractUpdateRequest(options: RequestOptions) {
      this.beforeUpdateRequest(options)
    }

    afterForContractUpdateRequest(options: RequestOptions) {
      this.afterUpdateRequest(options)
    }

}