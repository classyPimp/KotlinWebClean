import {BaseModel} from "../../../javascript/modelLayer/BaseModel"
import { Property } from '../../../javascript/modelLayer/annotations/Property'

export class AssociatedModel extends BaseModel {
    static className = "Associated"

    @Property
    className: string

    @Property
    associationType: string

    @Property
    fieldOnThis: string

    @Property
    fieldOnThat: string

    @Property
    pluralClassName: string

    @Property
    property: string

    componentKey: number

}