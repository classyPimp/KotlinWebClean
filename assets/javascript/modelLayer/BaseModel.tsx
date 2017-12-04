import { ModelSerializer } from './ModelSerializer';
import { IModelProperties } from './interfaces/IModelProperties';
import { IModelConstructor } from './interfaces/IModelConstructor';
import { IAssociationsConfig } from './interfaces/IAssociationsConfig';
import { ModelCollection } from './ModelCollection'
import {MixinSerializableTrait} from "./modelTraits/MixinSerializableTrait"
import {MixinValidatableTrait} from "./modelTraits/MixinValidatableTrait"


class ModelClassMixinContainer {
    constructor(...args: Array<any>){}
}

export class BaseModel extends MixinSerializableTrait(MixinValidatableTrait(ModelClassMixinContainer)) {
    properties: IModelProperties;
    constructor(properties?: IModelProperties){
        super(properties)
        this.init()
    }  

    static className = "NOT_SET"

    getClassName(): string {
        return (this.constructor as typeof BaseModel).className
    }


    getPureProperties(root: boolean = true): IModelProperties {
        let objectToReturn: IModelProperties = {}
        let propertiesCopy = {...(this.properties)}
        delete propertiesCopy.errors

        for (let key of Object.keys(propertiesCopy)) {
            let value = propertiesCopy[key]
            objectToReturn[key] = this.normalizeWhenGettingPureProperties(value, false)
        }
        if (root) {
            let thisClassname = this.getClassName()
            let objectToReturnWithRoot: IModelProperties = {}
            objectToReturnWithRoot[thisClassname] = objectToReturn
            return objectToReturnWithRoot
        } else {
            return objectToReturn
        }
    }

    normalizeWhenGettingPureProperties(value: any, root: boolean = false): any {
        if (value instanceof BaseModel) {
            return value.getPureProperties(root)
        } else if (value instanceof ModelCollection) {
            let mapped = value.array.map((it)=>{
                return (it as BaseModel).getPureProperties(false)
            })
            return mapped
        } else {
            return value
        }
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