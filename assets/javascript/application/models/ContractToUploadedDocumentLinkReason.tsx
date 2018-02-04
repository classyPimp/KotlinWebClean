import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { HasOne } from '../../modelLayer/annotations/HasOne'
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { ModelCollection } from '../../modelLayer/ModelCollection'
import { RequestOptions, Route } from '../../modelLayer/annotations/ModelRoute'
import { ContractToUploadedDocumentLink } from './ContractToUploadedDocumentLink'

export class ContractToUploadedDocumentLinkReason extends BaseModel {

    static className = "contractToUploadedDocumentLinkReason"

    @Property
    id: number

    @Property
    name: string

    @Property
    description: string

    @Property
    updatedAt: string

    @Property
    createdAt: string

    @HasMany("ContractToUploadedDocumentLink")
    contractToUploadedDocumentLinks: ModelCollection<ContractToUploadedDocumentLink>

    @Route("POST", {url: "/api/contractToUploadedDocumentLinkReasons"})
    create: (options?: RequestOptions) => Promise<ContractToUploadedDocumentLinkReason>

    @Route("GET", {url: "/api/contractToUploadedDocumentLinkReasons"})
    static index: (options?: RequestOptions) => Promise<ModelCollection<ContractToUploadedDocumentLinkReason>>

    @Route("GET", {url: "/api/contractToUploadedDocumentLinkReasons/:id"})
    static show: (options?: RequestOptions) => Promise<ContractToUploadedDocumentLinkReason>

    @Route("GET", {url: "/api/contractToUploadedDocumentLinkReasons/:id/edit"})
    static edit: (options?: RequestOptions) => Promise<ContractToUploadedDocumentLinkReason>

    @Route("PUT", {url: "/api/contractToUploadedDocumentLinkReasons/:id", defaultWilds: ["id"]})
    update: (options?: RequestOptions) => Promise<ContractToUploadedDocumentLinkReason>

    @Route("DELETE", {url: "/api/contractToUploadedDocumentLinkReasons/:id", defaultWilds: ["id"]})
    destroy: (options?: RequestOptions) => Promise<ContractToUploadedDocumentLinkReason>

    @Route("GET", {url: "/api/contractToUploadedDocumentLinkReasons/formFeeds"})
    static formFeedsIndex: (options?: RequestOptions)=>Promise<ModelCollection<ContractToUploadedDocumentLinkReason>>

    static afterFormFeedsIndexRequest(options: RequestOptions) {
      this.afterIndexRequest(options)
    }

}