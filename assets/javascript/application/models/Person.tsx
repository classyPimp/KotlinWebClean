import { PersonToContactLink } from './PersonToContactLink'
import { Contact } from "./Contact"
import  { ModelRegistry } from '../../modelLayer/ModelRegistry' 
import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { HasOne } from '../../modelLayer/annotations/HasOne'
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { ModelCollection } from '../../modelLayer/ModelCollection'
import { RequestOptions, Route } from '../../modelLayer/annotations/ModelRoute'

export class Person extends BaseModel {

    static className = "person"

    @Property
    id: number

    @Property
    name: string

    @Property
    updatedAt: string

    @Property
    createdAt: string

    @HasMany("PersonToContactLink")
    personToContactLinks: ModelCollection<PersonToContactLink>

    @Route("POST", {url: '/api/persons'})
    create: (options?: RequestOptions)=>Promise<Person>

    @Route("GET", {url: "/api/persons"})
    static index: (options?: RequestOptions)=>Promise<ModelCollection<Person>>

    @Route("GET", {url: "/api/persons/:id"})
    static get: (options?: RequestOptions)=>Promise<Person>

    @Route("PUT", {url: "/api/persons/:id", defaultWilds: ["id"]})
    update: (options?: RequestOptions)=>Promise<Person>

    @Route("DELETE", {url: "/api/persons/:id", defaultWilds: ["id"]})
    delete: (options?: RequestOptions)=>Promise<Person>

    @Route("GET", {url: "/api/persons/formFeeds"})
    static formFeedsIndex: (options?: RequestOptions) => Promise<ModelCollection<Person>>

    static afterFormFeedsIndexRequest(options: RequestOptions) {
      this.afterIndexRequest(options)
    }

    addContact(){
      let contact = new Contact()
      let personToContactLink = new PersonToContactLink()
      personToContactLink.contact = contact
      this.personToContactLinks.push(personToContactLink)
    }

}  

ModelRegistry.register("Person", Person)