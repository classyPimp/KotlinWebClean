import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { HasOne } from '../../modelLayer/annotations/HasOne'
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { ModelCollection } from '../../modelLayer/ModelCollection'
import { RequestOptions, Route } from '../../modelLayer/annotations/ModelRoute'
import { ApprovalStepToApproverLink } from './ApprovalStepToApproverLink'
import { ApprovalRejectionToUploadedDocumentLink } from './ApprovalRejectionToUploadedDocumentLink'
import { Discussion } from './Discussion'
import { Approval } from './Approval'
import { User } from './User'

export class ApprovalRejection extends BaseModel {

    static className = "approvalRejection"

    @Property
    id: number

    @Property
    updatedAt: string

    @Property
    createdAt: string

    @Property
    approvalId: number

    @Property
    userId: number

    @Property
    reasonText: string

    @Property
    isFullfilled: string


    @HasOne("Approval")
    approval: Approval

    @HasMany("ApprovalRejectionToUploadedDocumentLink")
    approvalRejectionToUploadedDocumentLinks: ModelCollection<ApprovalRejectionToUploadedDocumentLink>

    @HasOne("User")
    user: User

    @HasOne("Discussion")
    discussion: Discussion

    @Route("POST", {url: "/api/approval/ofContract/:approvalId/approvalRejection"})
    ofContractCreate: (options?: RequestOptions) => Promise<ApprovalRejection>

    beforeOfContractCreateRequest(options: RequestOptions) {
      this.beforeCreateRequest(options)
    }

    afterOfContractCreateRequest(options: RequestOptions) {
      this.afterCreateRequest(options)
    }

}