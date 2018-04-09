import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { HasOne } from '../../modelLayer/annotations/HasOne'
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { ModelCollection } from '../../modelLayer/ModelCollection'
import { RequestOptions, Route } from '../../modelLayer/annotations/ModelRoute'
import { ApprovalStep } from './ApprovalStep'
import { UploadedDocument } from './UploadedDocument'

export class ApprovalStepToUploadedDocumentLink extends BaseModel {

    static className = "approvalStepToUploadedDocumentLink"

    reactComponentKey: number

    @Property
    id: number

    @Property
    updatedAt: string

    @Property
    createdAt: string

    @Property
    approvalStepId: number

    @Property
    uploadedDocumentId: number

    @Property
    description: string

    @HasOne("ApprovalStep")
    approvalStep: ApprovalStep

    @HasOne("UploadedDocument")
    uploadedDocument: UploadedDocument

}