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

    @Property
    reactComponentName: string

    @Property
    composerName: string

    @Property
    updaterName: string

    @Property
    controllerName: string

    @HasMany("TableField")
    tableFields: ModelCollection<TableField>

    @HasMany("AssociatedModel")
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

    @Route("POST", {url: "/hilfhund/generate/jsModel"})
    generateJsModel: (options?: RequestOptions)=>Promise<any>

    beforeGenerateJsModelRequest(options: RequestOptions) {
        this.beforeGenerateToJsonSerializerRequest(options)
    }

    afterGenerateJsModelRequest(options: RequestOptions) {
        this.afterGenerateToJsonSerializerRequest(options)
    }

    @Route("POST", {url: "/hilfhund/generate/reactComponent"})
    generateReactComponent: (options?: RequestOptions)=>Promise<any>

    beforeGenerateReactComponentRequest(options: RequestOptions) {
        this.beforeGenerateToJsonSerializerRequest(options)
    }

    afterGenerateReactComponentRequest(options: RequestOptions) {
        this.afterGenerateToJsonSerializerRequest(options)
    }

    @Route("POST", {url: "/hilfhund/generate/composer"})
    generateComposer: (options?: RequestOptions)=>Promise<any>

    beforeGenerateComposerRequest(options: RequestOptions) {
        this.beforeGenerateToJsonSerializerRequest(options)
    }

    afterGenerateComposerRequest(options: RequestOptions) {
        this.afterGenerateToJsonSerializerRequest(options)
    }

    @Route("POST", {url: "/hilfhund/generate/validator"})
    generateValidator: (options?: RequestOptions)=>Promise<any>

    beforeGenerateValidatorRequest(options: RequestOptions) {
        console.log("before generate validator")
        this.beforeGenerateToJsonSerializerRequest(options)
    }

    afterGenerateValidatorRequest(options: RequestOptions) {
        this.afterGenerateToJsonSerializerRequest(options)
    }

    @Route("POST", {url: "/hilfhund/generate/updater"})
    generateUpdater: (options?: RequestOptions)=>Promise<any>

    beforeGenerateUpdaterRequest(options: RequestOptions) {
        console.log("before generate updater")
        this.beforeGenerateToJsonSerializerRequest(options)
    }

    afterGenerateUpdaterRequest(options: RequestOptions) {
        this.afterGenerateToJsonSerializerRequest(options)
    }

    @Route("POST", {url: "/hilfhund/generate/requestParametersWrapper"})
    generateRequestParametersWrapper: (options?: RequestOptions)=>Promise<any>

    beforeGenerateRequestParametersWrapperRequest(options: RequestOptions) {
        console.log("before generate updater")
        this.beforeGenerateToJsonSerializerRequest(options)
    }

    afterGenerateRequestParametersWrapperRequest(options: RequestOptions) {
        this.afterGenerateToJsonSerializerRequest(options)
    }

    @Route("POST", {url: "/hilfhund/generate/daos"})
    generateDaos: (options?: RequestOptions) => Promise<any>

    beforeGenerateDaosRequest(options: RequestOptions) {
        this.beforeGenerateToJsonSerializerRequest(options)
    }

    afterGenerateDaosRequest(options: RequestOptions) {
        this.afterGenerateToJsonSerializerRequest(options)
    }

    @Route("POST", {url: "/hilfhund/generate/controller"})
    generateController: (options?: RequestOptions) => Promise<any>

    beforeGenerateControllerRequest(options: RequestOptions) {
        this.beforeGenerateToJsonSerializerRequest(options)
    }

    afterGenerateControllerRequest(options: RequestOptions) {
        this.afterGenerateToJsonSerializerRequest(options)
    }


}

