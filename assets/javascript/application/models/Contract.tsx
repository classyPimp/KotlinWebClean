import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { HasOne } from '../../modelLayer/annotations/HasOne'
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { ModelCollection } from '../../modelLayer/ModelCollection'
import { RequestOptions, Route } from '../../modelLayer/annotations/ModelRoute'
import { ContractToUploadedDocumentLink } from './ContractToUploadedDocumentLink'
import { ContractStatus } from './ContractStatus'
import { ContractNumber } from './ContractNumber'
import { ContractCategory } from './ContractCategory'
import { ContractToCounterPartyLink } from './ContractToCounterPartyLink' 

export class Contract extends BaseModel {

    static className = "contract"

    @Property
    id: number

    @Property
    contractNumberId: number

    @Property
    contractStatusId: number

    @Property
    contractCategoryId: number

    @Property
    description: string

    @Property
    updatedAt: string

    @Property
    createdAt: string

    @HasMany("ContractToUploadedDocumentLink")
    contractToUploadedDocumentLinks: ModelCollection<ContractToUploadedDocumentLink>

    @HasOne("ContractStatus")
    contractStatus: ContractStatus

    @HasOne("ContractNumber")
    contractNumber: number

    @HasOne("ContractCategory")
    contractCategory: ContractCategory

    @HasMany("ContractToCounterPartyLink")
    contractToCounterPartyLinks: ModelCollection<ContractToCounterPartyLink>
}