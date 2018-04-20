import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { HasOne } from '../../modelLayer/annotations/HasOne'
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { ModelCollection } from '../../modelLayer/ModelCollection'
import { RequestOptions, Route } from '../../modelLayer/annotations/ModelRoute'
import { User } from './User'
import { ApprovalRejection } from './ApprovalRejection'

export class ApprovalStepToApproverLink extends BaseModel {

    static className = "approvalStepToApproverLink"

    @Property
    id: number

    @Property
    userId: number

    @Property
    updatedAt: string

    @Property
    createdAt: string

    @Property
    isApproved: string

    @HasOne("User")
    user: User

    @Route("PUT", {url: "/api/approvalStepToApproverLink/ofContract/:id", defaultWilds: ["id"]})
    ofContractApprove: (options?: RequestOptions) => Promise<ApprovalStepToApproverLink>

    afterOfContractApproveRequest(options: RequestOptions) {
      this.afterUpdateRequest(options)
    }

}