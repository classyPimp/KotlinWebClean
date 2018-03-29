import { ModelCollection } from './ModelCollection';
import { BaseModel } from './BaseModel';
import { IModelConstructor } from './interfaces/IModelConstructor';
import { IAssociationsConfig, IAssociationsConfigEntry } from './interfaces/IAssociationsConfig';
import { IModelProperties } from './interfaces/IModelProperties';
import { AssociationTypesEnum } from './AssociationTypesEnum';
import { ModelRegistry } from './ModelRegistry';

export class ModelSerializer {

    static parseCollection<T extends BaseModel>(
        modelConstructor: IModelConstructor, 
        propertiesArray: Array<IModelProperties>
    ): ModelCollection<T> {
        let collectionToReturn = new ModelCollection<T>()
        for (let properties of propertiesArray) {
            collectionToReturn.push(new modelConstructor(properties) as T)
        }
        return collectionToReturn
    }

    static parseFromConstructor(
        properties: IModelProperties, 
        associationsConfig: IAssociationsConfig
    ): IModelProperties{
        let parsedProperties: IModelProperties = {}

        for (let key of Object.keys(properties)) {
            let value = properties[key]
            if (value instanceof BaseModel) {
                parsedProperties[key] = value
                continue
            }
            if (associationsConfig[key]) {
                this.parseAssociatedFromConstructor(
                    parsedProperties, 
                    associationsConfig[key], 
                    key, 
                    value
                )
            } else {
                parsedProperties[key] = value
            }
        }
        return parsedProperties
    }

    private static parseAssociatedFromConstructor(
        parsedProperties: IModelProperties, 
        configEntry: IAssociationsConfigEntry, 
        key: string, 
        value: IModelProperties | Array<IModelProperties>
    ){
        if (configEntry.aliasedTo) {
            key = configEntry.aliasedTo
        }
        let associationType = configEntry.associationType
        if (value) {
          let thatConstructor = configEntry.getThatModelConstructor()

          if (!thatConstructor) {
            console.log(`WARNING: ${key} is not registered at ModelRegistrator`)
          }

          if (associationType === AssociationTypesEnum.hasMany) {
              if (value instanceof ModelCollection ) {
                parsedProperties[key] = value
              } else {
                parsedProperties[key] = this.parseCollectionOfBaseModel(configEntry.getThatModelConstructor(), value as Array<IModelProperties>)
              }
          }
          if (associationType === AssociationTypesEnum.hasOne) {
              parsedProperties[key] = this.parseSingleBaseModel(configEntry.getThatModelConstructor(), value)
          } 
        } else {
           parsedProperties[key] = null
        }
    }

    private static parseSingleBaseModel(
        modelConstructor: IModelConstructor,
        properties: IModelProperties
    ): BaseModel {
        return new modelConstructor(properties)
    }

    private static parseCollectionOfBaseModel(
        modelConstructor: IModelConstructor, 
        propertiesArray: Array<IModelProperties>
    ): ModelCollection<BaseModel> {
        let collectionToReturn = new ModelCollection()
        for (let properties of propertiesArray) {
            let newModel = new modelConstructor(properties)
            collectionToReturn.push(
                newModel
            )
        }
        return collectionToReturn
    }

}