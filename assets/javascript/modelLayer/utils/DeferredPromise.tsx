import {Promise} from "es6-promise"


export class DefferedPromise<T> {
     
    promise: Promise<T>
  
    innerResolve: (value: T) => void
    innerReject: (reason: any)=> void
    lastThen: Thenable<any>

    resolve(value: T) {
        this.innerResolve(value)
    }

    reject(reason: any) {
        this.innerReject(reason)
    }

    constructor() {
        this.promise = new Promise<T>((resolve, reject)=>{
            this.innerResolve = resolve
            this.innerReject = reject
        })
        this.lastThen = this.promise
    }

    getPromise(): Promise<T> {
        return this.promise
    }

    then(callback: (value: T)=>void): Thenable<any>{
        this.lastThen = this.lastThen.then(callback)
        return this.lastThen 
    }

    catch(callback: (reason: any)=>void): Thenable<any> {
      this.lastThen = (this.lastThen as Promise<any>).catch(callback)
      return this.lastThen
    }
}