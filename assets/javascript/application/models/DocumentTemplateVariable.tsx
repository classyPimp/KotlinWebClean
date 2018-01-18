import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { HasOne } from '../../modelLayer/annotations/HasOne'
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { ModelCollection } from '../../modelLayer/ModelCollection'
import { RequestOptions, Route } from '../../modelLayer/annotations/ModelRoute'

export class DocumentTemplateVariable extends BaseModel {

    static className = "documentTemplateVariable"

    @Property
    id: number

    @Property
    updatedAt: string

    @Property
    createdAt: string

}