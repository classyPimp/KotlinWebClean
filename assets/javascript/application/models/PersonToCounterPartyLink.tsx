import { BaseModel } from '../../modelLayer/BaseModel';
import { Property } from '../../modelLayer/annotations/Property';
import { HasOne } from '../../modelLayer/annotations/HasOne';
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { CounterParty } from './CounterParty'
import { Person } from './Person'
 

export class PersonToCounterPartyLink extends BaseModel {

  static className: "person_to_counter_party_link"

  @Property
  id: number

  @Property
  personId: number

  @Property
  counterPartyId: number

  @Property
  linkReason: number

  @Property
  specificDetails: string

  @Property
  createdAt: string

  @Property
  updatedAt: string

  @HasOne(CounterParty)
  counterParty: CounterParty

  @HasOne(Person)
  person: Person

}