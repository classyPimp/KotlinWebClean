import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { HasOne } from '../../modelLayer/annotations/HasOne'
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { ModelCollection } from '../../modelLayer/ModelCollection'
import { PersonToCounterPartyLink } from './PersonToCounterPartyLink'
import  { ModelRegistry } from '../../modelLayer/ModelRegistry' 
import { RequestOptions, Route } from '../../modelLayer/annotations/ModelRoute'

export class PersonToCounterPartyLinkReason extends BaseModel {

    static className = "personToCounterPartyLinkReason"

    @Property
    id: number

    @Property
    reasonName: string

    @Property
    updatedAt: string

    @Property
    createdAt: string

    @HasMany("PersonToCounterPartyLink")
    personToCounterPartyLinks: ModelCollection<PersonToCounterPartyLink>

    @Route("POST", {url: "/api/persontocounterpartylinkreasons"})
    create: (options?: RequestOptions) => Promise<PersonToCounterPartyLinkReason>

    @Route("GET", {url: "/api/persontocounterpartylinkreasons"})
    static index: (options?: RequestOptions) => Promise<ModelCollection<PersonToCounterPartyLinkReason>>

    @Route("GET", {url: "/api/persontocounterpartylinkreasons/:id"})
    static show: (options?: RequestOptions) => Promise<PersonToCounterPartyLinkReason>

    @Route("GET", {url: "/api/persontocounterpartylinkreasons/:id/edit"})
    static edit: (options?: RequestOptions) => Promise<PersonToCounterPartyLinkReason>

    @Route("PUT", {url: "/api/persontocounterpartylinkreasons/:id", defaultWilds: ["id"]})
    update: (options?: RequestOptions) => Promise<PersonToCounterPartyLinkReason>

    @Route("DELETE", {url: "/api/persontocounterpartylinkreasons/:id", defaultWilds: ["id"]})
    destroy: (options?: RequestOptions) => Promise<PersonToCounterPartyLinkReason>

    @Route("GET", {url: "/api/persontocounterpartylinkreasons/formFeeds"})
    static formFeedsIndex: (options?: RequestOptions) => Promise<ModelCollection<PersonToCounterPartyLinkReason>>

    static afterFormFeedsIndexRequest(options: RequestOptions) {
      this.afterIndexRequest(options)
    }
    
}

ModelRegistry.register("PersonToCounterPartyLinkReason", PersonToCounterPartyLinkReason)