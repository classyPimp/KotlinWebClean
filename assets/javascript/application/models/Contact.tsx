import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { HasOne } from '../../modelLayer/annotations/HasOne'
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { ContactType } from './ContactType'
import { PersonToContactLink } from './PersonToContactLink'
import { ModelCollection } from '../../modelLayer/ModelCollection'
import { CounterPartyToContactLink } from './CounterPartyToContactLink'
import { RequestOptions, Route } from '../../modelLayer/annotations/ModelRoute';
import  { ModelRegistry } from '../../modelLayer/ModelRegistry' 

export class Contact extends BaseModel {

    static className = "contact"

    @Property
    id: number

    @Property
    contactTypeId: number

    @Property
    value: string    

    @Property
    updatedAt: string

    @Property
    createdAt: string

    @HasOne("ContactType")
    contactType: ContactType

    @HasOne("PersonToContactLink")
    personToContactLink: PersonToContactLink

    @HasOne("CounterPartyToContactLink")
    counterPartyToContactLink: CounterPartyToContactLink   

    @Route("POST", {url: "/api/persons/:personId/contacts"})
    createForPerson: (options?: RequestOptions)=>Promise<Contact>

    beforeCreateForPersonRequest(options: RequestOptions) {
      this.beforeCreateRequest(options)
    }

    afterCreateForPersonRequest(options: RequestOptions) {
      this.afterCreateRequest(options)
    }

    @Route("DELETE", {url: "/api/persons/:personId/contacts/:id", defaultWilds: ["id"]})
    deleteForPerson: (options?: RequestOptions)=>Promise<Contact>

    beforeDeleteForPersonRequest(options: RequestOptions){
      this.beforeDeleteRequest(options)
    }

    afterDeleteForPersonRequest(options: RequestOptions) {
      this.afterDeleteRequest(options)
    }

    @Route("PUT", {url: "/api/persons/:personId/contacts/:id", defaultWilds: ["id"]})
    updateForPerson: (options?: RequestOptions)=>Promise<Contact>

    beforeUpdateForPersonRequest(options: RequestOptions) {
      this.beforeUpdateRequest(options)
    }

    afterUpdateForPersonRequest(options: RequestOptions) {
      this.afterUpdateRequest(options)
    }

    @Route("POST", {url: "/api/counterParties/:counterPartyId/contacts"})    
    createForCounterParty: (options?: RequestOptions)=>Promise<Contact>

    beforeCreateForCounterPartyRequest(options: RequestOptions) {
      this.beforeCreateRequest(options)
    }

    afterCreateForCounterPartyRequest(options: RequestOptions) {
      this.afterCreateRequest(options)
    }

    @Route("DELETE", {url: "/api/counterParties/:counterPartyId/contacts/:id", defaultWilds: ["id"]})
    deleteForCounterParty: (options?: RequestOptions)=>Promise<Contact>

    beforeDeleteForCounterPartyRequest(options: RequestOptions){
      this.beforeDeleteRequest(options)
    }

    afterDeleteForCounterPartyRequest(options: RequestOptions) {
      this.afterDeleteRequest(options)
    }

    @Route("PUT", {url: "/api/counterParties/:counterPartyId/contacts/:id", defaultWilds: ["id"]})
    updateForCounterParty: (options?: RequestOptions)=>Promise<Contact>

    beforeUpdateForCounterPartyRequest(options: RequestOptions) {
      this.beforeUpdateRequest(options)
    }

    afterUpdateForCounterPartyRequest(options: RequestOptions) {
      this.afterUpdateRequest(options)
    }

}

ModelRegistry.register("Contact", Contact)