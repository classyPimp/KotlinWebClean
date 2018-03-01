import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { HasOne } from '../../modelLayer/annotations/HasOne'
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { Person } from './Person'
import { CounterParty } from './CounterParty'
import { PersonToCounterPartyLinkReason } from './PersonToCounterPartyLinkReason'
import  { ModelRegistry } from '../../modelLayer/ModelRegistry' 
import { RequestOptions, Route } from '../../modelLayer/annotations/ModelRoute'
import { ModelCollection } from '../../modelLayer/ModelCollection'
import { PersonToCounterPartyLinkToUploadedDocumentLink } from './PersonToCounterPartyLinkToUploadedDocumentLink'

export class PersonToCounterPartyLink extends BaseModel {

    static className = "personToCounterPartyLink"

    @Property
    id: number

    @Property
    personId: number

    @Property
    counterPartyId: number

    @Property
    personToCounterPartyLinkReasonId: number

    @Property
    specificDetails: string

    @Property
    updatedAt: string

    @Property
    createdAt: string

    @HasOne("Person")
    person: Person

    @HasOne("CounterParty")
    counterParty: CounterParty

    @HasOne("PersonToCounterPartyLinkReason")
    personToCounterPartyLinkReason: PersonToCounterPartyLinkReason

    @HasMany("PersonToCounterPartyLinkToUploadedDocumentLink")
    personToCounterPartyLinkToUploadedDocumentLinks: ModelCollection<PersonToCounterPartyLinkToUploadedDocumentLink>

    @Route("POST", {url: "/api/personToCounterPartyLinks"})
    create: (options?: RequestOptions) => Promise<PersonToCounterPartyLink>

    @Route("GET", {url: "/api/personToCounterPartyLinks"})
    static index: (options?: RequestOptions) => Promise<ModelCollection<PersonToCounterPartyLink>>

    @Route("GET", {url: "/api/personToCounterPartyLinks/:id"})
    static show: (options?: RequestOptions) => Promise<PersonToCounterPartyLink>

    @Route("GET", {url: "/api/personToCounterPartyLinks/:id/edit"})
    static edit: (options?: RequestOptions) => Promise<PersonToCounterPartyLink>

    @Route("PUT", {url: "/api/personToCounterPartyLinks/:id", defaultWilds: ["id"]})
    update: (options?: RequestOptions) => Promise<PersonToCounterPartyLink>

    @Route("DELETE", {url: "/api/personToCounterPartyLinks/:id", defaultWilds: ["id"]})
    destroy: (options?: RequestOptions) => Promise<PersonToCounterPartyLink>

    @Route("GET", {url: "/api/counterParties/:counterPartyId/personToCounterPartyLinks"})
    static indexForCounterParty: (options?: RequestOptions) => Promise<ModelCollection<PersonToCounterPartyLink>>

    static afterIndexForCounterPartyRequest(options: RequestOptions){
      this.afterIndexRequest(options)
    }

    @Route("GET", {url: "/api/persons/:personId/personToCounterPartyLinks"})
    static indexForPerson: (options?: RequestOptions) => Promise<ModelCollection<PersonToCounterPartyLink>>

    static afterIndexForPersonRequest(options: RequestOptions) {
      this.afterIndexRequest(options)
    }
}
