import { BaseModel } from '../../modelLayer/BaseModel';
import { Property } from '../../modelLayer/annotations/Property';
import { HasOne } from '../../modelLayer/annotations/HasOne';
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { PersonContact } from './PersonContact'


export class Person extends BaseModel {

  @Property
  id: number

  @Property
  name: string

  @Property
  createdAt: string

  @Property
  updatedAt: string

  @HasMany(PersonContact)
  personContacts: PersonContact

}