import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { HasOne } from '../../modelLayer/annotations/HasOne'
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { PersonToCounterPartyLinkToUploadedDocumentLink } from './PersonToCounterPartyLinkToUploadedDocumentLink'
import { ModelCollection } from '../../modelLayer/ModelCollection'
import  { ModelRegistry } from '../../modelLayer/ModelRegistry' 

export class PersonToCounterPartyLinkToUploadedDocLinkReason extends BaseModel {

    static className = "personToCounterPartyLinkToUploadedDocLinkReason"

    @Property
    id: number

    @Property
    reasonName: string

    @Property
    updatedAt: string

    @Property
    createdAt: string

    @HasMany("PersonToCounterPartyLinkToUploadedDocumentLink")
    personToCounterPartyLinkToUploadedDocumentLinks: ModelCollection<PersonToCounterPartyLinkToUploadedDocumentLink>

}
