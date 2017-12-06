import { BaseModel } from '../../modelLayer/BaseModel';
import { Property } from '../../modelLayer/annotations/Property';
import { HasOne } from '../../modelLayer/annotations/HasOne';
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { PersonToCounterPartyLink } from './PersonToCounterPartyLink'
import { UploadedDocument } from './UploadedDocument'

export class PersonToCounterPartyLinkToUploadedDocumentLink extends BaseModel {

  @Property
  id: number

  @Property
  personToCounterPartyLinkId: number

  @Property
  uploadedDocumentId: number

  @Property
  type: number

  @Property
  createdAt: number

  @Property
  updatedAt: number

  @HasOne(PersonToCounterPartyLink)
  personToCounterPartyLink: PersonToCounterPartyLink

  @HasOne(UploadedDocument)
  uploadedDocument: UploadedDocument

}