import { DefferedPromise } from './DeferredPromise';
import { IModelProperties } from '../interfaces/IModelProperties';
import { RequestOptions } from '../annotations/ModelRoute'

export class XhrRequestMaker {

    static onFailHandler: (xhr: XMLHttpRequest)=>void

    static get(options: RequestOptions): DefferedPromise<any>{
        options.httpMethod = "GET"
        let requestMaker = new this(options)
        return requestMaker.deferredPromise
    }

    static post(options: RequestOptions): DefferedPromise<any>{
        options.httpMethod = "POST"
        let requestMaker = new this(options)
        return requestMaker.deferredPromise
    }

    static put<T>(options: RequestOptions): DefferedPromise<any>{
        options.httpMethod = "PUT"
        let requestMaker = new this(options)
        return requestMaker.deferredPromise
    }

    static delete<T>(options: RequestOptions): DefferedPromise<any>{
        options.httpMethod = "DELETE"
        let requestMaker = new this(options)
        return requestMaker.deferredPromise
    }

    static create(options: RequestOptions): DefferedPromise<any>{
        let requestMaker = new this(options)
        return requestMaker.deferredPromise
    }

    deferredPromise: DefferedPromise<any>
    xhr: XMLHttpRequest
    options: RequestOptions

    constructor(options: RequestOptions){
        this.deferredPromise = options.deferredPromise
        this.options = options
        this.xhr = new XMLHttpRequest()
        this.setParameters()
        this.xhr.open(this.options.httpMethod, this.options.url)
        if (options.responseType) {
          (this.xhr as any).responseType = options.responseType
        }
        this.xhr.onprogress = function (e) {
            if (e.lengthComputable) {
            }
        }
        this.xhr.onloadstart = function (e) {
        }
        this.xhr.onloadend = function (e) {
        }
        this.setHeaders()
        
        this.setOnLoad()
        this.send()
    }

    send(){
        if (this.options.httpMethod != "GET" && this.options.params) {
            if (this.options.serializeAsForm) {
                this.xhr.send( this.createFormData(this.options.params) )
            } else {
              this.xhr.send(JSON.stringify(this.options.params))
            }
        } else {
            this.xhr.send()
        }
    }

    setParameters(){
        let options = this.options
        if (options.httpMethod === "GET" && options.params) {
            options.url = `${options.url}?${this.objectToQueryString(options.params)}`
        } else {
            
        }
    }

    setOnLoad(){
        this.xhr.onload = ()=>{
            if (this.xhr.status === 200) {
                let contentType = this.xhr.getResponseHeader('Content-Type')
                if (this.options.responseType === "blob") {
                    if (contentType === "blob") {
                        this.deferredPromise.resolve({BLOB_IS_RETURNED: true, BLOB_RESPONSE: this.xhr.response}) 
                    } else {
                        const blob = new Blob([this.xhr.response], {type: "text/plain"});
                        const reader = new FileReader();
                        reader.addEventListener('loadend', (e) => {
                          const text = (e.srcElement as any).result;
                          this.deferredPromise.resolve(JSON.parse(text)) 
                        });
                        reader.readAsText(blob);
                    }  
                    return
                }
                if (this.options.resolveWithJson) {
                    let response = this.xhr.responseText
                    if (response) {
                        this.deferredPromise.resolve(JSON.parse(this.xhr.responseText))   
                    } else {
                        this.deferredPromise.resolve({})
                    }
                } else {
                    this.deferredPromise.resolve(this.xhr)
                }
            }
            else {
                if (XhrRequestMaker.onFailHandler) {
                    XhrRequestMaker.onFailHandler(this.xhr)
                }
                this.deferredPromise.reject(this.xhr)
            }
        }
    }

    setHeaders(){
        if (this.options.requestHeaders) {
            this.xhr.setRequestHeader(this.options.requestHeaders[0], this.options.requestHeaders[1])
        } else if (this.options.serializeAsForm) {
            null
        } else {
            this.xhr.setRequestHeader('Content-Type', 'application/json')
        }
    }

    objectToQueryString(objectToSerialize: {[id: string]: any}, prefix?: string): string {
        var str = [], p;
        for(p in objectToSerialize) {
          if (objectToSerialize.hasOwnProperty(p)) {
            var k = prefix ? prefix + "[" + p + "]" : p, v = objectToSerialize[p];
            str.push((v !== null && typeof v === "object") ?
              this.objectToQueryString(v, k) :
              encodeURIComponent(k) + "=" + encodeURIComponent(v));
          }
        }
        return str.join("&");
    }

    createFormData(object: any, form?: FormData, namespace?: string): FormData {
        const formData = form || new FormData();
        for (let property in object) {
            if (!object.hasOwnProperty(property) || !object[property]) {
                continue;
            }
            const formKey = namespace ? `${namespace}[${property}]` : property;
            if (object[property] instanceof Date) {
                formData.append(formKey, object[property].toISOString());
            } else if (typeof object[property] === 'object' && !(object[property] instanceof File)) {
                this.createFormData(object[property], formData, formKey);
            } else {
                formData.append(formKey, object[property]);
            }
        }
        return formData;
    }

    serializeToFormData(val: IModelProperties, formData: FormData, track: string = ""): FormData {
        if (Object.prototype.toString.call(val) === '[object Array]') {
            for (let index: number = 0; index < (val as Array<any>).length; index++) {
                let value = (val as Array<any>)[index]
                if (index === 0) {
                    track = track + "[#{index}]"
                } else {
                    //#last length will grab the string depending on it's digits, eg [1], i 3 but [111] is not
                    let lastLength = ((index - 1).toString().length + 3)
                    let substringed = track.substring(0, track.length - lastLength)
                    track = substringed + "[#{index}]"
                }
                this.serializeToFormData(value, formData, track)
            }        
        } else if (this.isPlainObject(val)) {
            for (let key of Object.keys(val)) {
                let value = val[key]
                let _track: string = null;
                if (track === null) {
                  _track = key
                } else {
                  _track = `${track}[${key}]`
                }
                this.serializeToFormData(value, formData, _track)
            }  
        } else {
            formData.append(track, val as any)
        }
        return formData
    }


    isPlainObject(obj: any): boolean {

        // Basic check for Type object that's not null
        if (typeof obj == 'object' && obj !== null) {

          // If Object.getPrototypeOf supported, use it
          if (typeof Object.getPrototypeOf == 'function') {
            var proto = Object.getPrototypeOf(obj);
            return proto === Object.prototype || proto === null;
          }
          
          // Otherwise, use internal class
          // This should be reliable as if getPrototypeOf not supported, is pre-ES5
          return Object.prototype.toString.call(obj) == '[object Object]';
        }
        
        // Not an object
        return false;
    }

}

