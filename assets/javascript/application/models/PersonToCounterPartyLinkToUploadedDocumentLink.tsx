import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { HasOne } from '../../modelLayer/annotations/HasOne'
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { ModelCollection } from '../../modelLayer/ModelCollection'
import { PersonToCounterPartyLink } from './PersonToCounterPartyLink'
import { UploadedDocument } from './UploadedDocument'
import { PersonToCounterPartyLinkToUploadedDocLinkReason } from './PersonToCounterPartyLinkToUploadedDocLinkReason'
import  { ModelRegistry } from '../../modelLayer/ModelRegistry' 
import { RequestOptions, Route } from '../../modelLayer/annotations/ModelRoute'


export class PersonToCounterPartyLinkToUploadedDocumentLink extends BaseModel {

    static className = "personToCounterPartyLinkToUploadedDocumentLink"

    @Property
    id: number

    @Property
    personToCounterPartyLinkId: number

    @Property
    uploadedDocumentId: number

    @Property
    personToCounterPartyLinkToUploadedDocLinkReasonId: number

    @Property
    updatedAt: string

    @Property
    createdAt: string

    @HasOne("PersonToCounterPartyLink")
    personToCounterPartyLink: PersonToCounterPartyLink

    @HasOne("UploadedDocument") 
    uploadedDocument: UploadedDocument

    @HasOne("PersonToCounterPartyLinkToUploadedDocLinkReason")
    personToCounterPartyLinkToUploadedDocLinkReason: PersonToCounterPartyLinkToUploadedDocLinkReason


    @Route("POST", {url: "/api/personToCounterPartyLinks/:personToCounterPartyLinkId/personToCounterPartyLinkToUploadedDocumentLinks"})
    create: (options?: RequestOptions) => Promise<PersonToCounterPartyLinkToUploadedDocumentLink>

    @Route("GET", {url: "/api/personToCounterPartyLinks/:personToCounterPartyLinkId/personToCounterPartyLinkToUploadedDocumentLinks"})
    static index: (options?: RequestOptions) => Promise<ModelCollection<PersonToCounterPartyLinkToUploadedDocumentLink>>

    @Route("GET", {url: "/api/personToCounterPartyLinks/:personToCounterPartyLinkId/personToCounterPartyLinkToUploadedDocumentLinks/:id"})
    static show: (options?: RequestOptions) => Promise<PersonToCounterPartyLinkToUploadedDocumentLink>

    @Route("GET", {url: "/api/personToCounterPartyLinks/:personToCounterPartyLinkId/personToCounterPartyLinkToUploadedDocumentLinks/:id/edit"})
    static edit: (options?: RequestOptions) => Promise<PersonToCounterPartyLinkToUploadedDocumentLink>

    @Route("PUT", {url: "/api/personToCounterPartyLinks/:personToCounterPartyLinkId/personToCounterPartyLinkToUploadedDocumentLinks/:id", defaultWilds: ["id"]})
    update: (options?: RequestOptions) => Promise<PersonToCounterPartyLinkToUploadedDocumentLink>

    @Route("DELETE", {url: "/api/personToCounterPartyLinks/:personToCounterPartyLinkId/personToCounterPartyLinkToUploadedDocumentLinks/:id", defaultWilds: ["id"]})
    destroy: (options?: RequestOptions) => Promise<PersonToCounterPartyLinkToUploadedDocumentLink>


}
