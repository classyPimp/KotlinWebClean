import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { HasOne } from '../../modelLayer/annotations/HasOne'
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { ModelCollection } from '../../modelLayer/ModelCollection'
import { RequestOptions, Route } from '../../modelLayer/annotations/ModelRoute'
import { ApprovalStepToApproverLink } from './ApprovalStepToApproverLink'
import { ApprovalRejectionToUploadedDocumentLink } from './ApprovalRejectionToUploadedDocumentLink'
import { Discussion } from './Discussion'

export class ApprovalRejection extends BaseModel {

    static className = "approvalRejection"

    @Property
    id: number

    @Property
    updatedAt: string

    @Property
    createdAt: string

    @Property
    approvalStepToApproverLinkId: number

    @Property
    reasonText: string

    @Property
    isFullfilled: string

    @HasOne("ApprovalStepToApproverLink")
    approvalStepToApproverLink: ApprovalStepToApproverLink

    @HasMany("ApprovalRejectionToUploadedDocumentLink")
    approvalRejectionToUploadedDocumentLinks: ModelCollection<ApprovalRejectionToUploadedDocumentLink>

    @HasOne("Discussion")
    discussion: Discussion

    @Route("POST", {url: "/api/approvalStepToApproverLink/ofContract/:approvalStepToApproverLinkId/approvalRejection"})
    ofContractCreate: (options?: RequestOptions) => Promise<ApprovalRejection>

    beforeOfContractCreateRequest(options: RequestOptions) {
      this.beforeCreateRequest(options)
    }

    afterOfContractCreateRequest(options: RequestOptions) {
      this.afterCreateRequest(options)
    }

}