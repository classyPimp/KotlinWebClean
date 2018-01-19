import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { HasOne } from '../../modelLayer/annotations/HasOne'
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { ModelCollection } from '../../modelLayer/ModelCollection'
import { RequestOptions, Route } from '../../modelLayer/annotations/ModelRoute'
import { Contract } from './Contract'
import { UploadedDocument } from './UploadedDocument'

export class ContractToUploadedDocumentLink extends BaseModel {

    static className = "contractToUploadedDocumentLink"

    @Property
    id: number

    @Property
    contractId: number

    @Property
    uploadedDocumentId: number

    @Property
    updatedAt: string

    @Property
    createdAt: string

    @HasOne("Contract")
    contract: Contract

    @HasOne("UploadedDocument")
    uploadedDocument: UploadedDocument

}