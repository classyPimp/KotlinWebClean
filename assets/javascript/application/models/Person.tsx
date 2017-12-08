import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { HasOne } from '../../modelLayer/annotations/HasOne'
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { ModelCollection } from '../../modelLayer/ModelCollection'
import { PersonToContactLink } from './PersonToContactLink'

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

    @HasMany(PersonToContactLink)
    personToContactLinks: ModelCollection<PersonToContactLink>



}