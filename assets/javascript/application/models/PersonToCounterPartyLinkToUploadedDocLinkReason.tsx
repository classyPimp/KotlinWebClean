import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { HasOne } from '../../modelLayer/annotations/HasOne'
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { PersonToCounterPartyLinkToUploadedDocumentLink } from './PersonToCounterPartyLinkToUploadedDocumentLink'
import { ModelCollection } from '../../modelLayer/ModelCollection'
import  { ModelRegistry } from '../../modelLayer/ModelRegistry' 
import { RequestOptions, Route } from '../../modelLayer/annotations/ModelRoute'

export class PersonToCounterPartyLinkToUploadedDocLinkReason extends BaseModel {

    static className = "personToCounterPartyLinkToUploadedDocLinkReason"

    @Property
    id: number

    @Property
    reasonName: string

    @Property
    updatedAt: string

    @Property
    createdAt: string

    @HasMany("PersonToCounterPartyLinkToUploadedDocumentLink")
    personToCounterPartyLinkToUploadedDocumentLinks: ModelCollection<PersonToCounterPartyLinkToUploadedDocumentLink>

    @Route("POST", {url: "/api/personToCounterPartyLinkToUploadedDocLinkReason"})
    create: (options?: RequestOptions) => Promise<PersonToCounterPartyLinkToUploadedDocLinkReason>

    @Route("GET", {url: "/api/personToCounterPartyLinkToUploadedDocLinkReason"})
    static index: (options?: RequestOptions) => Promise<ModelCollection<PersonToCounterPartyLinkToUploadedDocLinkReason>>

    @Route("GET", {url: "/api/personToCounterPartyLinkToUploadedDocLinkReason/:id"})
    static show: (options?: RequestOptions) => Promise<PersonToCounterPartyLinkToUploadedDocLinkReason>

    @Route("GET", {url: "/api/personToCounterPartyLinkToUploadedDocLinkReason/:id/edit"})
    static edit: (options?: RequestOptions) => Promise<PersonToCounterPartyLinkToUploadedDocLinkReason>

    @Route("PUT", {url: "/api/personToCounterPartyLinkToUploadedDocLinkReason/:id", defaultWilds: ["id"]})
    update: (options?: RequestOptions) => Promise<PersonToCounterPartyLinkToUploadedDocLinkReason>

    @Route("DELETE", {url: "/api/personToCounterPartyLinkToUploadedDocLinkReason/:id", defaultWilds: ["id"]})
    destroy: (options?: RequestOptions) => Promise<PersonToCounterPartyLinkToUploadedDocLinkReason>

    @Route("GET", {url: "/api/personToCounterPartyLinkToUploadedDocLinkReason/formFeeds"})
    static formFeedsIndex: (options?: RequestOptions) => Promise<ModelCollection<PersonToCounterPartyLinkToUploadedDocLinkReason>>

    static afterFormFeedsIndexRequest(options: RequestOptions) {
      this.afterIndexRequest(options)
    }

}
