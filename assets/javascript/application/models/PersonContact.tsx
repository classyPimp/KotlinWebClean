import { BaseModel } from '../../modelLayer/BaseModel';
import { Property } from '../../modelLayer/annotations/Property';
import { HasOne } from '../../modelLayer/annotations/HasOne';
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { Person } from "./Person"


export class PersonContact extends BaseModel {

  @Property
  id: number

  @Property
  contactType: number

  @Property
  value: string

  @Property
  createdAt: string

  @Property
  updatedAt: string

  @HasOne(Person)
  person: Person

}