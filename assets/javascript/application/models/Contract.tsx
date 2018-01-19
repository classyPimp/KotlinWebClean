import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { HasOne } from '../../modelLayer/annotations/HasOne'
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { ModelCollection } from '../../modelLayer/ModelCollection'
import { RequestOptions, Route } from '../../modelLayer/annotations/ModelRoute'
import { ContractToUploadedDocumentLink } from './ContractToUploadedDocumentLink'

export class Contract extends BaseModel {

    static className = "contract"

    @Property
    id: number

    @Property
    description: string

    @Property
    updatedAt: string

    @Property
    createdAt: string

    @HasMany("ContractToUploadedDocumentLink")
    contractToUploadedDocumentLinks: ModelCollection<ContractToUploadedDocumentLink>
}