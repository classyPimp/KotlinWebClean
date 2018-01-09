import  { ModelRegistry } from '../../modelLayer/ModelRegistry'
import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { HasOne } from '../../modelLayer/annotations/HasOne'
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { Person } from './Person'
import { Contact } from './Contact'

export class PersonToContactLink extends BaseModel {

    static className = "personToContactLink"

    @Property
    id: number

    @Property
    personId: number

    @Property
    contactId: number

    @Property
    updatedAt: string

    @Property
    createdAt: string

    @HasOne("Person")
    person: Person

    @HasOne("Contact")
    contact: Contact

}


