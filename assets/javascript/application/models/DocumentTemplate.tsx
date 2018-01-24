import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { HasOne } from '../../modelLayer/annotations/HasOne'
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { ModelCollection } from '../../modelLayer/ModelCollection'
import { RequestOptions, Route } from '../../modelLayer/annotations/ModelRoute'
import { UploadedDocument } from './UploadedDocument'
import { DocumentTemplateToDocumentVariableLink } from './DocumentTemplateToDocumentVariableLink'

export class DocumentTemplate extends BaseModel {

    static className = "documentTemplate"

    @Property
    id: number

    @Property
    name: string

    @Property
    description: string

    @Property
    uploadedDocumentId: number

    @Property
    updatedAt: string

    @Property
    createdAt: string

    @HasOne("UploadedDocument")
    uploadedDocument: UploadedDocument

    @HasMany("DocumentTemplateToDocumentVariableLink")
    documentTemplateToDocumentVariableLinks: ModelCollection<DocumentTemplateToDocumentVariableLink>

    @Route("POST", {url: "/api/documentTemplates/prevalidations"})
    prevalidationsCreate: (options?: RequestOptions) => Promise<DocumentTemplate>

    beforePrevalidationsCreateRequest(options: RequestOptions) {
      this.beforeCreateRequest(options)
    }

    afterPrevalidationsCreateRequest(options: RequestOptions) {
      this.afterCreateRequest(options)
    }

}