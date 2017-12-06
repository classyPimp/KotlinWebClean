import { BaseModel } from '../../modelLayer/BaseModel';
import { Property } from '../../modelLayer/annotations/Property';
import { HasOne } from '../../modelLayer/annotations/HasOne';
import { HasMany } from '../../modelLayer/annotations/HasMany'


export class UploadedDocument extends BaseModel {

  static className = "uploaded_document"

  @Property
  id: number

  @Property
  uploadedDocumentId: number

  @Property
  description: string

  @Property
  fileName: string

  @Property
  fileSize: string

  @Property
  fileMime: string

  @Property
  isFolder: boolean

  @Property
  folderName: string

  @Property
  createdAt: string

  @Property
  updatedAt: string

  @HasMany(UploadedDocument)
  childDocuments: Array<UploadedDocument>

  @HasOne(UploadedDocument)
  parentDocument: UploadedDocument

}