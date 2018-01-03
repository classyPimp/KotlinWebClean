import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { HasOne } from '../../modelLayer/annotations/HasOne'
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { ModelCollection } from '../../modelLayer/ModelCollection'
import { IncorporationForm } from './IncorporationForm'
import { CounterPartyToContactLink } from './CounterPartyToContactLink'
import { PersonToCounterPartyLink } from './PersonToCounterPartyLink'
import  { ModelRegistry } from '../../modelLayer/ModelRegistry' 
import { RequestOptions, Route } from '../../modelLayer/annotations/ModelRoute'

export class CounterParty extends BaseModel {

    static className = "counterParty"

    @Property
    id: number

    @Property
    incorporationFormId: number

    @Property
    name: string

    @Property
    nameShort: string

    @Property
    updatedAt: string

    @Property
    createdAt: string

    @HasOne("IncorporationForm")
    incorporationForm: IncorporationForm

    @HasMany("CounterPartyToContactLink")
    counterPartyContacts: ModelCollection<CounterPartyToContactLink>

    @HasMany("PersonToCounterPartyLink")
    personToCounterPartyLinks: ModelCollection<PersonToCounterPartyLink>

     @Route("POST", {url: "/api/counterParties"})
    create: (options?: RequestOptions) => Promise<CounterParty>

    @Route("GET", {url: "/api/counterParties"})
    static index: (options?: RequestOptions) => Promise<ModelCollection<CounterParty>>

    @Route("GET", {url: "/api/counterParties/:id"})
    static show: (options?: RequestOptions) => Promise<CounterParty>

    @Route("GET", {url: "/api/counterParties/:id/edit"})
    static edit: (options?: RequestOptions) => Promise<CounterParty>

    @Route("PUT", {url: "/api/counterParties/:id", defaultWilds: ["id"]})
    update: (options?: RequestOptions) => Promise<CounterParty>

    @Route("DELETE", {url: "/api/counterParties/:id", defaultWilds: ["id"]})
    destroy: (options?: RequestOptions) => Promise<CounterParty>

}

ModelRegistry.register("CounterParty", CounterParty)