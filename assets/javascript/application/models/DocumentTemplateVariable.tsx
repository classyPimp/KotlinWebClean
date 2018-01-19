import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { HasOne } from '../../modelLayer/annotations/HasOne'
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { ModelCollection } from '../../modelLayer/ModelCollection'
import { RequestOptions, Route } from '../../modelLayer/annotations/ModelRoute'
import { DocumentTemplateToDocumentVariableLink } from './DocumentTemplateToDocumentVariableLink'

export class DocumentTemplateVariable extends BaseModel {

    static className = "documentTemplateVariable"

    @Property
    id: number

    @Property
    name: string

    @Property
    humanizedName: string

    @Property
    description: string

    @Property
    defaultValue: string

    @Property
    isPreprogrammed: boolean

    @Property
    updatedAt: string

    @Property
    createdAt: string

    @HasMany("DocumentTemplateToDocumentTemplateVariableLink")
    documentTemplateToDocumentVariableLinks: ModelCollection<DocumentTemplateToDocumentVariableLink>

    @Route("POST", {url: "/api/documentTemplateVariable"})
    create: (options?: RequestOptions) => Promise<DocumentTemplateVariable>

    @Route("GET", {url: "/api/documentTemplateVariable"})
    static index: (options?: RequestOptions) => Promise<ModelCollection<DocumentTemplateVariable>>

    @Route("GET", {url: "/api/documentTemplateVariable/:id"})
    static show: (options?: RequestOptions) => Promise<DocumentTemplateVariable>

    @Route("GET", {url: "/api/documentTemplateVariable/:id/edit"})
    static edit: (options?: RequestOptions) => Promise<DocumentTemplateVariable>

    @Route("PUT", {url: "/api/documentTemplateVariable/:id", defaultWilds: ["id"]})
    update: (options?: RequestOptions) => Promise<DocumentTemplateVariable>

    @Route("DELETE", {url: "/api/documentTemplateVariable/:id", defaultWilds: ["id"]})
    destroy: (options?: RequestOptions) => Promise<DocumentTemplateVariable>

}