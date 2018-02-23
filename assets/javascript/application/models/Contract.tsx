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
import { MonetaryObligation } from './MonetaryObligation'

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

    @HasMany("MonetaryObligation")
    monetaryObligations: ModelCollection<MonetaryObligation>

    @Route("POST", {url: "/api/contracts"})
    create: (options?: RequestOptions) => Promise<Contract>

    @Route("GET", {url: "/api/contracts"})
    static index: (options?: RequestOptions) => Promise<ModelCollection<Contract>>

    @Route("GET", {url: "/api/contracts/:id"})
    static show: (options?: RequestOptions) => Promise<Contract>

    @Route("GET", {url: "/api/contracts/:id/edit"})
    static edit: (options?: RequestOptions) => Promise<Contract>

    @Route("PUT", {url: "/api/contracts/:id", defaultWilds: ["id"]})
    update: (options?: RequestOptions) => Promise<Contract>

    @Route("DELETE", {url: "/api/contracts/:id", defaultWilds: ["id"]})
    destroy: (options?: RequestOptions) => Promise<Contract>

    @Route("GET", {url: "/api/contracts/manage/:id/edit"})
    static manageEdit: (options?: RequestOptions) => Promise<Contract>

    static afterManageEditRequest(options: RequestOptions) {
      this.afterEditRequest(options)
    }

    @Route("PUT", {url: "/api/contracts/manage/:id", defaultWilds: ["id"]})
    manageUpdate: (options?: RequestOptions) => Promise<Contract>

    beforeManageUpdateRequest(options: RequestOptions) {
      this.beforeUpdateRequest(options)
    }

    afterManageUpdateRequest(options: RequestOptions) {
      this.afterUpdateRequest(options)
    }

}