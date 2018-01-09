import { BaseModel } from '../BaseModel';
import {IAssociationsConfig} from "../interfaces/IAssociationsConfig"
import {IModelProperties} from "../interfaces/IModelProperties"
import {ModelSerializer} from "../ModelSerializer"
import {AnyConstructor} from "../utils/AnyConstructor"
import autobind from 'autobind-decorator';


export function MixinSerializableTrait<TBase extends AnyConstructor>(Base: TBase) {
    return class SerializableTrait extends Base {
      
      static associationsConfig: IAssociationsConfig = {}

      private getStaticAssociationsConfig(): IAssociationsConfig {
          return (this.constructor as typeof SerializableTrait).associationsConfig
      }

      static getAssociationsConfig(): IAssociationsConfig {
          let config = this.associationsConfig
          if (!config) {
              config = {}
              this.associationsConfig = config
          } 
          return config
      }

      init(properties?: IModelProperties){

      }

      properties: IModelProperties;

      constructor(...args: Array<any>){
          super(args)
          args = args[0]
          let properties = this.parsePropertes(args as IModelProperties)
          this.properties = properties
      }

      parsePropertes(properties: IModelProperties): IModelProperties{
        if (properties) {
          return ModelSerializer.parseFromConstructor(
              properties,
              this.getStaticAssociationsConfig()
          )
        } else {
            return {}
        }
      }

      mergeProperties(properties: IModelProperties) {
          this.properties = {...(this.properties), ...properties}
      }

      mergeWith(model: BaseModel){
          this.properties = {...(this.properties), ...(model.properties)};
          (this as any).errors = model.errors
      }

  }
}