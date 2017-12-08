import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { HasOne } from '../../modelLayer/annotations/HasOne'
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { ModelCollection } from '../../modelLayer/ModelCollection'
import { Contact } from './Contact'
import { RequestOptions, Route } from '../../modelLayer/annotations/ModelRoute';

export class ContactType extends BaseModel {

    static className = "contactType"

    @Property
    id: number

    @Property
    name: string

    @Property
    isSpecificForType: string

    @Property
    updatedAt: string

    @Property
    createdAt: string

    @HasMany(Contact)
    contacts: ModelCollection<Contact>

    @Route("POST", {url: "/api/contactTypes/create"})
    create: (options?: RequestOptions) => Promise<ContactType>

    @Route("GET", {url: "/api/contactTypes"})
    static index: (options?: RequestOptions) => Promise<ModelCollection<ContactType>>

    @Route("GET", {url: "/api/contactTypes/:id"})
    static get: (options?: RequestOptions) => Promise<ContactType>

    @Route("UPDATE", {url: "/api/contactTypes/:id", defaultWilds: ["id"]})
    update: (options?: RequestOptions) => Promise<ContactType>

    @Route("DELETE", {url: "/api/contactTypes/:id", defaultWilds: ["id"]})
    delete: (options?: RequestOptions) => Promise<ContactType>


}