import { ModelCollection } from '../../../javascript/modelLayer/ModelCollection';
import {BaseModel} from "../../../javascript/modelLayer/BaseModel"
import { Property } from '../../../javascript/modelLayer/annotations/Property'
import {HasMany} from "../../../javascript/modelLayer/annotations/HasMany"
import {TableField} from "./TableField"
import {AssociatedModel} from "./AssociatedModel"
import { RequestOptions, Route } from '../../../javascript/modelLayer/annotations/ModelRoute';


export class Model extends BaseModel {

    static className = "model"

    @Property
    className: string

    @Property
    pluralClassName: string

    @Property
    factoryName: string

    @Property
    toJsonSerializerName: string

    @HasMany(TableField)
    tableFields: ModelCollection<TableField>

    @HasMany(AssociatedModel)
    associatedModels: ModelCollection<AssociatedModel>

    @Route("POST", {url: "/hilfhund/generate/model"})
    generateModel: (options?: RequestOptions) => Promise<any>

    @Route("POST", {url: "/hilfhund/generate/jooq"})
    static generateJooq: (options?: RequestOptions)=> Promise<any>

    beforeGenerateModelRequest(options: RequestOptions) {
        options.params = this.getPureProperties()
    }

    afterGenerateModelRequest(options: RequestOptions) {
        options.deferredPromise.then((response)=>{
            let returned = new Model(response)
            returned.validate()
            if (returned.isValid()) {
                alert("success")
            } else {
                this.errors = returned.errors
            }
            return this
        })
    }

    @Route("POST", {url: "/hilfhund/generate/factory"})
    generateFactory: (options?: RequestOptions) => Promise<any>

    beforeGenerateFactoryRequest(options: RequestOptions) {
        options.params = this.getPureProperties()
    }

    afterGenerateFactoryRequest(options: RequestOptions) {
        options.deferredPromise.then((response)=>{
            let returned = new Model(response)
            returned.validate()
            if (returned.isValid()) {
                alert("success")
            } else {
                this.errors = returned.errors
            }
            return this
        })
    }

    @Route("POST", {url: "/hilfhund/generate/toJsonSerializer"})
    generateToJsonSerializer: (options?: RequestOptions) => Promise<any>

    beforeGenerateToJsonSerializerRequest(options: RequestOptions) {
        options.params = this.getPureProperties()
    }

    afterGenerateToJsonSerializerRequest(options: RequestOptions) {
        options.deferredPromise.then((response)=>{
            let returned = new Model(response)
            returned.validate()
            if (returned.isValid()) {
                alert("success")
            } else {
                this.errors = returned.errors
            }
            return this
        })
    }

}

