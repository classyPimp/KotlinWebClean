import {BaseModel} from "../../../javascript/modelLayer/BaseModel"
import { Property } from '../../../javascript/modelLayer/annotations/Property'

export class TableField extends BaseModel {

    @Property
    name: string

    @Property
    isPrimaryKey: boolean

    @Property
    type: string


    componentKey: number
    
}
