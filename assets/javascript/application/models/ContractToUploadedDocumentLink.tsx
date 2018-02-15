import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { HasOne } from '../../modelLayer/annotations/HasOne'
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { ModelCollection } from '../../modelLayer/ModelCollection'
import { RequestOptions, Route } from '../../modelLayer/annotations/ModelRoute'
import { Contract } from './Contract'
import { UploadedDocument } from './UploadedDocument'
import { ContractToUploadedDocumentLinkReason } from './ContractToUploadedDocumentLinkReason'

export class ContractToUploadedDocumentLink extends BaseModel {

    static className = "contractToUploadedDocumentLink"

    @Property
    id: number

    @Property
    contractId: number

    @Property
    uploadedDocumentId: number

    @Property
    description: string

    @Property
    contractToUploadedDocumentLinkReasonId: number

    @Property
    updatedAt: string

    @Property
    createdAt: string

    @HasOne("Contract")
    contract: Contract

    @HasOne("UploadedDocument")
    uploadedDocument: UploadedDocument

    @HasOne("ContractToUploadedDocumentLinkReason")
    contractToUploadedDocumentLinkReason: ContractToUploadedDocumentLinkReason

    @Route("POST", {url: "/api/contracts/:contractId/contractToUploadedDocumentLinks", defaultWilds: ["contractId"]})
    forContractManageCreate: (options?: RequestOptions) => Promise<ContractToUploadedDocumentLink>

    beforeForContractManageCreateRequest(options: RequestOptions) {
      this.beforeCreateRequest(options)
    }

    afterForContractManageCreateRequest(options: RequestOptions) {
      this.afterCreateRequest(options)
    }

    @Route("PUT", {url: "/api/contracts/:contractId/contractToUploadedDocumentLinks/:id", defaultWilds: ["contractId", "id"]})
    forContractManageUpdate: (options?: RequestOptions) => Promise<ContractToUploadedDocumentLink>

    beforeForContractManageUpdateRequest(options: RequestOptions) {
      this.beforeUpdateRequest(options)
    }

    afterForContractManageUpdateRequest(options: RequestOptions) {
      this.afterUpdateRequest(options)
    }

    @Route("DELETE", {url: "/api/contracts/:contractId/contractToUploadedDocumentLinks/:id", defaultWilds: ["contractId", "id"]})
    forContractManageDestroy: (options?: RequestOptions) => Promise<ContractToUploadedDocumentLink>

    afterForContractManageDestroyRequest(options: RequestOptions) {
      this.afterCreateRequest
    }
}