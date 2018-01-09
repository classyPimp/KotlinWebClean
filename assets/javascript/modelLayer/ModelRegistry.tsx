import {BaseModel} from './BaseModel'
import { IModelConstructor } from './interfaces/IModelConstructor'

export class ModelRegistry {

  static registeredModels: {[id: string]: IModelConstructor} = {}

  static register(stringifiedClassName: string, model: IModelConstructor) {
    console.log("registering " + stringifiedClassName)
    this.registeredModels[stringifiedClassName] = model
  }

  static get(key: string): IModelConstructor {
    return this.registeredModels[key]
  }

  static modelGetterFuntion(key: string): ()=>IModelConstructor {
    return function(): IModelConstructor {
      return ModelRegistry.get(key)
    }
  }
}
