import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { HasOne } from '../../modelLayer/annotations/HasOne'
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { ModelCollection } from '../../modelLayer/ModelCollection'
import { PersonToCounterPartyLink } from './PersonToCounterPartyLink'
import  { ModelRegistry } from '../../modelLayer/ModelRegistry' 

export class PersonToCounterPartyLinkReason extends BaseModel {

    static className = "personToCounterPartyLinkReason"

    @Property
    id: number

    @Property
    reasonName: string

    @Property
    updatedAt: string

    @Property
    createdAt: string

    @HasMany("PersonToCounterPartyLink")
    personToCounterPartyLinks: ModelCollection<PersonToCounterPartyLink>

}

ModelRegistry.register("PersonToCounterPartyLinkReason", PersonToCounterPartyLinkReason)