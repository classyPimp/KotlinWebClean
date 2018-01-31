import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { HasOne } from '../../modelLayer/annotations/HasOne'
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { ModelCollection } from '../../modelLayer/ModelCollection'
import { RequestOptions, Route } from '../../modelLayer/annotations/ModelRoute'
import { UploadedDocument } from './UploadedDocument'
import { DocumentTemplateToDocumentVariableLink } from './DocumentTemplateToDocumentVariableLink'
import { DocumentTemplateCategory } from './DocumentTemplateCategory'

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

    @Property
    documentTemplateCategoryId: number

    @HasOne("UploadedDocument")
    uploadedDocument: UploadedDocument

    @HasOne("DocumentTemplateCategory")
    documentTemplateCategory: DocumentTemplateCategory

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

    @Route("GET", {url: "/api/documentTemplates/arbitrary/:id"})
    static arbitraryShow: (options?: RequestOptions) => Promise<DocumentTemplate>

    static afterArbitraryShowRequest(options: RequestOptions) {
      this.afterShowRequest(options)
    }

    @Route("GET", {url: "/api/documentTemplates/new"})
    static new: (options?: RequestOptions) => Promise<DocumentTemplate>

    @Route("POST", {url: "/api/documentTemplates"})
    create: (options?: RequestOptions) => Promise<DocumentTemplate>

    @Route("POST", {url: "/api/documentTemplates/arbitrary"})
    arbitraryCreate: (options?: RequestOptions) => Promise<DocumentTemplate>

    beforeArbitraryCreateRequest(options: RequestOptions) {
      this.beforeCreateRequest(options)
    }

    afterArbitraryCreateRequest(options: RequestOptions) {
      this.afterCreateRequest(options)
    }

    @Route("GET", {url: "/api/documentTemplates"})
    static index: (options?: RequestOptions) => Promise<ModelCollection<DocumentTemplate>>

    @Route("DELETE", {url: "/api/documentTemplates/:id", defaultWilds: ["id"]})
    destroy: (options?: RequestOptions) => Promise<DocumentTemplate>
    
}