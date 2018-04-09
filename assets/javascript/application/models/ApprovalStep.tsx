import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { HasOne } from '../../modelLayer/annotations/HasOne'
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { ModelCollection } from '../../modelLayer/ModelCollection'
import { RequestOptions, Route } from '../../modelLayer/annotations/ModelRoute'
import { ApprovalStepToApproverLink } from './ApprovalStepToApproverLink'
import { Approval } from './Approval'
import { ApprovalStepToUploadedDocumentLink } from './ApprovalStepToUploadedDocumentLink'

export class ApprovalStep extends BaseModel {

    static className = "approvalStep"

    @Property
    id: number

    @Property
    updatedAt: string

    @Property
    createdAt: string

    @Property
    approvalId: number

    @HasMany("ApprovalStepToApproverLink")
    approvalStepToApproverLinks: ModelCollection<ApprovalStepToApproverLink>

    @HasMany("ApprovalStepToUploadedDocumentLink")
    approvalStepToUploadedDocumentLinks: ModelCollection<ApprovalStepToUploadedDocumentLink>

    @Route("POST", {url: "/api/approval/ofContract/:approvalId/approvalStep"})
    ofApprovalOfContractCreate: (options?: RequestOptions) => Promise<ApprovalStep>

    beforeOfApprovalOfContractCreateRequest(options: RequestOptions) {
      this.beforeCreateRequest(options)
    }

    afterOfApprovalOfContractCreateRequest(options: RequestOptions) {
      this.afterCreateRequest(options)
    }

}