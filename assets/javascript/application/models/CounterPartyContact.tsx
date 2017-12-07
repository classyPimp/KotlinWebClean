import { BaseModel } from '../../modelLayer/BaseModel';
import { Property } from '../../modelLayer/annotations/Property';
import { HasOne } from '../../modelLayer/annotations/HasOne';
import { CounterParty } from './CounterParty'

export class CounterPartyContact extends BaseModel {

  static className  = "counter_party_contact"

  @Property
  id: number

  @Property
  contactType: number

  @Property
  value: string

  @Property
  counterPartyId: number

  @Property
  createdAt: string

  @Property
  updatedAt: string

  @HasOne(CounterParty)
  counterParty: CounterParty

}