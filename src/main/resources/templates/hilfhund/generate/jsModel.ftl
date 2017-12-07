import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { HasOne } from '../../modelLayer/annotations/HasOne'
import { HasMany } from '../../modelLayer/annotations/HasMany'

export class ${className} extends BaseModel {

    static className = "${decapitalizedClassName}"

    @Property
    id: number

    @Property
    updatedAt: string

    @Property
    createdAt: string

}