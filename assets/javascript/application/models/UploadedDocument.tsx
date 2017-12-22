import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { HasOne } from '../../modelLayer/annotations/HasOne'
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { ModelCollection } from '../../modelLayer/ModelCollection'
import { PersonToCounterPartyLinkToUploadedDocumentLink } from './PersonToCounterPartyLinkToUploadedDocumentLink'
import  { ModelRegistry } from '../../modelLayer/ModelRegistry' 

export class UploadedDocument extends BaseModel {

    static className = "uploadedDocument"

    @Property
    id: number

    @Property
    uploadedDocumentId: number

    @Property
    description: string

    @Property
    fileName: string

    @Property
    fileSize: number

    @Property
    fileMime: string

    @Property
    updatedAt: string

    @Property
    isFolder: boolean

    @Property
    folderName: string

    @Property
    createdAt: string

    @HasMany("UploadedDocument")
    childDocuments: ModelCollection<UploadedDocument>

    @HasOne("UploadedDocument")
    parentDocument: UploadedDocument

    @HasMany("PersonToCounterPartyLinkToUploadedDocumentLink")
    personToCounterPartyLinkToUploadedDocumentLinks: ModelCollection<PersonToCounterPartyLinkToUploadedDocumentLink>

}

ModelRegistry.register("UploadedDocument", UploadedDocument)