import { Promise } from 'es6-promise';
import { BaseModel } from "../BaseModel"
import { IModelProperties } from '../interfaces/IModelProperties';
import { XhrRequestMaker } from '../utils/XhrRequestMaker';
import { DefferedPromise } from '../utils/DeferredPromise';
 

export interface RequestOptions {
    url?: string,
    method?: string,
    caller?: BaseModel,
    wilds?: {[id: string]: string}
    yieldRawResponse?: boolean,
    urlPrefix?: string,
    extraParams?: IModelProperties,
    serializeAsForm?: boolean ,
    arbitrary?: any,
    params?: IModelProperties,
    responseType?: string,
    //providedWilds?: {[id:string]:any},
    prefix?: string,
    requestHeaders?: Array<string>, 
    httpMethod?: string,
    resolveWithJson?: boolean,
    deferredPromise?: DefferedPromise<any>
}

class UrlPreparator {
    hasWilds: boolean
    wildsToIndexMap: {[id: string]: number} = {}
    splittedUrl: Array<string>
    url: string

    constructor(url: string){
        this.splittedUrl = url.split("/")
        this.url = url
        this.prepareWildsToIndexMap()        
    }

    private prepareWildsToIndexMap(){
        for (let i = 0; i < this.splittedUrl.length; i++) {
            let value = this.splittedUrl[i] 
            if (value[0] === ":") {
                this.hasWilds = true
                this.wildsToIndexMap[value.substring(1, value.length)] = i
            }
        }
    }

    produceUrl(options?: {wildValues?: {[id:string]:any}, prefix?: string}): string {
        let url: string = undefined

        if (this.hasWilds) {
            let clonedSplitted = this.splittedUrl.slice(0)
            for(let key of Object.keys(options.wildValues)) {
                let index = this.wildsToIndexMap[key]    
                clonedSplitted[index] = options.wildValues[key]
            }
            url =  clonedSplitted.join("/")
        } else {
            url = this.url
        }

        if (options && options.prefix) {
            url = `${options.prefix}/${url}`
        }

        return url
    }

}

interface RouteOptions {
    url: string,
    defaultWilds?: Array<string>
}

class ModelRouteHandler {
    options: RouteOptions
    urlPreparator: UrlPreparator
    wildsThatAreSetFromSameNamedMethod: {[id: string]: boolean} = {}

    constructor(options: RouteOptions) {
        this.options = options
        
        this.urlPreparator = new UrlPreparator(options.url)
        
        if (options.defaultWilds) {
            options.defaultWilds.forEach((it)=>{
                this.wildsThatAreSetFromSameNamedMethod[it] = true
            })
        }

    }   

    makeRequest(options: RequestOptions){

    }

    produceUrl(
        caller: BaseModel, 
        providedWilds?: {[id: string]:any}, 
        prefix?: string
    ): string {
        if (this.urlPreparator.hasWilds) {
            let wildValues = this.populateWildValues(caller, providedWilds)
            return this.urlPreparator.produceUrl({ wildValues, prefix })
        } else {
            return this.urlPreparator.produceUrl({ wildValues: null, prefix })
        }
    }

    private populateWildValues(
        model: BaseModel, 
        wilds: {[id:string]:any}
    ):{[id:string]: string} {
        let wildValues: {[id: string]: any} = {}
        
        for(let key of Object.keys(this.urlPreparator.wildsToIndexMap)) {
            if (wilds && wilds[key]) {
                wildValues[key] = wilds[key]
            } else if (this.wildsThatAreSetFromSameNamedMethod[key]) {
                wildValues[key] = (model as any)[key]
            } else {
                throw `IllegalStateException: wild: ${key} is not set explicitly, and is not sourced from method`
            }
        }
        return wildValues
    }
}


export function Route(httpMethod: string, options: RouteOptions) {
    
    return function(target: BaseModel | typeof BaseModel, propertyName: string) {
        
        let routeHandler = new ModelRouteHandler(options)

        let requestFunction = function(options: RequestOptions = {}): DefferedPromise<any>{
            let beforeRequestFunc = (this as any)[`before${propertyName.charAt(0).toUpperCase() + propertyName.slice(1)}Request`]
            let afterRequestFunc = (this as any)[`after${propertyName.charAt(0).toUpperCase() + propertyName.slice(1)}Request`]

            options.caller = this
            options.url = routeHandler.produceUrl(this, options.wilds, options.prefix)
            options.httpMethod = httpMethod
            
            if (!options.resolveWithJson) {
                options.resolveWithJson = true
            }
            
            if (beforeRequestFunc) {
                (this as any)[`before${propertyName.charAt(0).toUpperCase() + propertyName.slice(1)}Request`](options)
            }

            if ((this as BaseModel).hasFile) {
                options.serializeAsForm = true
            }
            
            options.httpMethod = httpMethod
            if (options.serializeAsForm) {
                options.requestHeaders = null
            }

            options.deferredPromise = new DefferedPromise<any>()

            //options.deferredPromise.catch((reason: any)=>{throw reason as any})

            XhrRequestMaker.create(options as RequestOptions) 
                                    
            if (afterRequestFunc){
                (this as any)[`after${propertyName.charAt(0).toUpperCase() + propertyName.slice(1)}Request`](options as RequestOptions)
            }
           
            
            return options.deferredPromise
        }

        ;(target as any)[propertyName] = requestFunction

    }
}

