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

}

ModelRegistry.register("Contact", Contact)