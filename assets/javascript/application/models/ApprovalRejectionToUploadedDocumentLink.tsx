import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { HasOne } from '../../modelLayer/annotations/HasOne'
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { ModelCollection } from '../../modelLayer/ModelCollection'
import { RequestOptions, Route } from '../../modelLayer/annotations/ModelRoute'
import { ApprovalRejection } from './ApprovalRejection'
import { UploadedDocument } from './UploadedDocument'

export class ApprovalRejectionToUploadedDocumentLink extends BaseModel {

    static className = "approvalRejectionToUploadedDocumentLink"

    @Property
    id: number

    @Property
    updatedAt: string

    @Property
    createdAt: string

    @Property
    approvalRejectionId: number

    @Property
    uploadedDocumentId: number

    @Property
    description: string

    @HasOne("ApprovalRejection")
    approvalRejection: ApprovalRejection

    @HasOne("UploadedDocument")
    uploadedDocument: UploadedDocument    

}