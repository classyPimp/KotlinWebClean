import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { HasOne } from '../../modelLayer/annotations/HasOne'
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { ModelCollection } from '../../modelLayer/ModelCollection'
import { PersonToCounterPartyLinkToUploadedDocumentLink } from './PersonToCounterPartyLinkToUploadedDocumentLink'
import  { ModelRegistry } from '../../modelLayer/ModelRegistry' 
import { RequestOptions, Route } from '../../modelLayer/annotations/ModelRoute'
import { FileUploadUtils } from '../services/FileUploadUtils'

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

    @Property
    file: File

    fileValidator(){
      if (this.properties["file"]) {
        this.hasFile = true
      }
    }

    fileUrl(namespace: string = "original"): string {
      if (this.fileName) {
        return `/uploads/uploadedDocuments/file/${FileUploadUtils.constructChunkedIdPath(this.id)}/${namespace}/${this.fileName}`
      } else {
        return null
      }
    }

    @HasMany("UploadedDocument")
    childDocuments: ModelCollection<UploadedDocument>

    @HasOne("UploadedDocument")
    parentDocument: UploadedDocument

    @HasMany("PersonToCounterPartyLinkToUploadedDocumentLink")
    personToCounterPartyLinkToUploadedDocumentLinks: ModelCollection<PersonToCounterPartyLinkToUploadedDocumentLink>


}
