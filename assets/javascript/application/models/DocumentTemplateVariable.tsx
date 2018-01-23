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

    @Route("POST", {url: "/api/documentTemplateVariables"})
    create: (options?: RequestOptions) => Promise<DocumentTemplateVariable>

    @Route("GET", {url: "/api/documentTemplateVariables"})
    static index: (options?: RequestOptions) => Promise<ModelCollection<DocumentTemplateVariable>>

    @Route("GET", {url: "/api/documentTemplateVariables/:id"})
    static show: (options?: RequestOptions) => Promise<DocumentTemplateVariable>

    @Route("GET", {url: "/api/documentTemplateVariables/:id/edit"})
    static edit: (options?: RequestOptions) => Promise<DocumentTemplateVariable>

    @Route("PUT", {url: "/api/documentTemplateVariables/:id", defaultWilds: ["id"]})
    update: (options?: RequestOptions) => Promise<DocumentTemplateVariable>

    @Route("DELETE", {url: "/api/documentTemplateVariables/:id", defaultWilds: ["id"]})
    destroy: (options?: RequestOptions) => Promise<DocumentTemplateVariable>

}