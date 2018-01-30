import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { HasOne } from '../../modelLayer/annotations/HasOne'
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { ModelCollection } from '../../modelLayer/ModelCollection'
import { RequestOptions, Route } from '../../modelLayer/annotations/ModelRoute'
import { DocumentTemplate } from './DocumentTemplate'

export class DocumentTemplateCategory extends BaseModel {

    static className = "documentTemplateCategory"

    @Property
    id: number

    @Property
    name: string

    @Property
    description: string

    @Property
    updatedAt: string

    @Property
    createdAt: string

    @HasMany("DocuementTemplate")
    documentTemplates: ModelCollection<DocumentTemplate>

    @Route("POST", {url: "/api/documentTemplateCategories"})
    create: (options?: RequestOptions) => Promise<DocumentTemplateCategory>

    @Route("GET", {url: "/api/documentTemplateCategories"})
    static index: (options?: RequestOptions) => Promise<ModelCollection<DocumentTemplateCategory>>

    @Route("GET", {url: "/api/documentTemplateCategories/:id"})
    static show: (options?: RequestOptions) => Promise<DocumentTemplateCategory>

    @Route("GET", {url: "/api/documentTemplateCategories/:id/edit"})
    static edit: (options?: RequestOptions) => Promise<DocumentTemplateCategory>

    @Route("PUT", {url: "/api/documentTemplateCategories/:id", defaultWilds: ["id"]})
    update: (options?: RequestOptions) => Promise<DocumentTemplateCategory>

    @Route("DELETE", {url: "/api/documentTemplateCategories/:id", defaultWilds: ["id"]})
    destroy: (options?: RequestOptions) => Promise<DocumentTemplateCategory>

    @Route("GET", {url: "/api/documentTemplateCategories/inputFeeds"})
    static inputFeedsIndex: (options?: RequestOptions)=>Promise<ModelCollection<DocumentTemplateCategory>>

    static afterInputFeedsIndexRequest(options: RequestOptions) {
      this.afterIndexRequest(options)
    }

}