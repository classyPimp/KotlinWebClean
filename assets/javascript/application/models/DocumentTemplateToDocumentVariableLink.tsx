import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { HasOne } from '../../modelLayer/annotations/HasOne'
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { ModelCollection } from '../../modelLayer/ModelCollection'
import { RequestOptions, Route } from '../../modelLayer/annotations/ModelRoute'
import { DocumentTemplate } from './DocumentTemplate'
import { DocumentTemplateVariable } from './DocumentTemplateVariable'

export class DocumentTemplateToDocumentVariableLink extends BaseModel {

    static className = "documentTemplateToDocumentVariableLink"

    @Property
    id: number

    @Property
    documentTemplateId: number

    @Property
    documentTemplateVariableId: number

    @Property
    uniqueIdentifier: string

    @Property
    updatedAt: string

    @Property
    createdAt: string

    @HasOne("DocumentTemplate")
    documentTemplate: DocumentTemplate

    @HasOne("DocumentTemplateVariable")
    documentTemplateVariable: DocumentTemplateVariable

}