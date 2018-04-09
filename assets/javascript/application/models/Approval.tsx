import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { HasOne } from '../../modelLayer/annotations/HasOne'
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { ModelCollection } from '../../modelLayer/ModelCollection'
import { RequestOptions, Route } from '../../modelLayer/annotations/ModelRoute'
import { ApprovalToApproverLink } from './ApprovalToApproverLink'
import { ApprovalStep } from './ApprovalStep'
import { ApprovalRejection } from './ApprovalRejection'
import { Contract } from './Contract'

export class Approval extends BaseModel {

    static className = "approval"

    @Property
    id: number

    @Property
    updatedAt: string

    @Property
    createdAt: string

    @Property
    approvableId: string

    @Property
    approvableType: string

    @Property
    lastStageId: number

    @HasMany("ApprovalToApproverLink")
    approvalToApproverLinks: ModelCollection<ApprovalToApproverLink>

    @HasMany("ApprovalStep")
    approvalSteps: ModelCollection<ApprovalStep>

    @HasOne("Contract")
    contract: Contract

    @HasMany("ApprovalRejection")
    approvalRejections: ModelCollection<ApprovalRejection>

    @Route("POST", {url: "/api/contracts/:contractId/approval"})
    ofContractCreate: (options?: RequestOptions) => Promise<Approval>

    beforeOfContractCreateRequest(options: RequestOptions) {
      this.beforeCreateRequest(options)
    }

    afterOfContractCreateRequest(options: RequestOptions) {
      this.afterCreateRequest(options)
    }

    @Route("GET", {url: "/api/contracts/:contractId/approval"})
    static ofContractShow: (options?: RequestOptions) => Promise<Approval>

    static afterOfContractShowRequest(options: RequestOptions) {
      console.log("calling afetAr")
      try {
        this.afterShowRequest(options)
      } catch(error) {
        console.log(error)
      }
    }

}