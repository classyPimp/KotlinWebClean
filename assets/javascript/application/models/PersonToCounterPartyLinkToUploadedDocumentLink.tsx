import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { HasOne } from '../../modelLayer/annotations/HasOne'
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { ModelCollection } from '../../modelLayer/ModelCollection'
import { PersonToCounterPartyLink } from './PersonToCounterPartyLink'
import { UploadedDocument } from './UploadedDocument'
import { PersonToCounterPartyLinkToUploadedDocLinkReason } from './PersonToCounterPartyLinkToUploadedDocLinkReason'
import  { ModelRegistry } from '../../modelLayer/ModelRegistry' 

export class PersonToCounterPartyLinkToUploadedDocumentLink extends BaseModel {

    static className = "personToCounterPartyLinkToUploadedDocumentLink"

    @Property
    id: number

    @Property
    personToCounterPartyLinkId: number

    @Property
    uploadedDocumentId: number

    @Property
    personToCounterPartyLinkToUploadedDocLinkReasonId: number

    @Property
    updatedAt: string

    @Property
    createdAt: string

    @HasOne("PersonToCounterPartyLink")
    personToCounterPartyLink: PersonToCounterPartyLink

    @HasOne("UploadedDocument") 
    uploadedDocument: UploadedDocument

    @HasOne("PersonToCounterPartyLinkToUploadedDocLinkReason")
    personToCounterPartyLinkToUploadedDocLinkReason: PersonToCounterPartyLinkToUploadedDocLinkReason

}

ModelRegistry.register("PersonToCounterPartyLinkToUploadedDocumentLink", PersonToCounterPartyLinkToUploadedDocumentLink)