import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { HasOne } from '../../modelLayer/annotations/HasOne'
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { ModelCollection } from '../../modelLayer/ModelCollection'
import { CounterParty } from './CounterParty'
import  { ModelRegistry } from '../../modelLayer/ModelRegistry' 
import { RequestOptions, Route } from '../../modelLayer/annotations/ModelRoute'

export class IncorporationForm extends BaseModel {

    static className = "incorporationForm"

    @Property
    id: number

    @Property
    name: string

    @Property
    nameShort: string

    @Property
    updatedAt: string

    @Property
    createdAt: string

    @HasMany("CounterParty")
    counterParties: ModelCollection<CounterParty>

    @Route("POST", {url: "/api/incorporationForms"})
    create: (options?: RequestOptions) => Promise<IncorporationForm>

    @Route("GET", {url: "/api/incorporationForms"})
    static index: (options?: RequestOptions) => Promise<ModelCollection<IncorporationForm>>

    @Route("GET", {url: "/api/incorporationForms/:id"})
    static show: (options?: RequestOptions) => Promise<IncorporationForm>

    @Route("GET", {url: "/api/incorporationForms/:id/edit"})
    static edit: (options?: RequestOptions) => Promise<IncorporationForm>

    @Route("PUT", {url: "/api/incorporationForms/:id", defaultWilds: ["id"]})
    update: (options?: RequestOptions) => Promise<IncorporationForm>

    @Route("DELETE", {url: "/api/incorporationForms/:id", defaultWilds: ["id"]})
    destroy: (options?: RequestOptions) => Promise<IncorporationForm>

    @Route("GET", {url: "/api/incorporationForms/formFeeds"})
    static formFeedsIndex: (options?: RequestOptions) => Promise<ModelCollection<IncorporationForm>>

    static afterFormFeedsIndexRequest(options: RequestOptions) {
      this.afterIndexRequest(options)
    }


}

ModelRegistry.register("IncorporationForm", IncorporationForm)