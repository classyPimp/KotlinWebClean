import { IAssociationsConfig, IAssociationsConfigEntry } from '../interfaces/IAssociationsConfig';
import { AssociationTypesEnum } from '../AssociationTypesEnum';
import { IModelConstructor } from '../interfaces/IModelConstructor';
import { BaseModel } from '../BaseModel';
import { ModelCollection } from '../ModelCollection';
import { ModelRegistry } from '../ModelRegistry'

export function HasMany(stringifiedClassName: string, parseAliases: Array<string> = null, thatConstructor?: IModelConstructor) {

    return function(target: BaseModel, propertyName: string){

        let get = function(){
            let valueAtProperty = this.properties[propertyName]
            if (valueAtProperty == null) {
                let defaultValue = new ModelCollection<BaseModel>()
                this[propertyName] = defaultValue
                return defaultValue   
            } else {
                return valueAtProperty
            }

        }

        let set = function(valueToSet: ModelCollection<BaseModel>) {

            this.properties[propertyName] = valueToSet
        }

        let associationsConfig: IAssociationsConfig = (target.constructor as typeof BaseModel).getAssociationsConfig()
        
        let associationsConfigEntry: IAssociationsConfigEntry = {
            associationType: AssociationTypesEnum.hasMany,
            getThatModelConstructor: ModelRegistry.modelGetterFuntion(stringifiedClassName),
            aliasedTo: null
        }; 
        associationsConfig[propertyName] = associationsConfigEntry

        if (parseAliases) {
            parseAliases.forEach((alias)=>{
                let configEntryForAlias: IAssociationsConfigEntry = {
                    associationType: AssociationTypesEnum.hasMany,
                    getThatModelConstructor: ModelRegistry.modelGetterFuntion(stringifiedClassName),
                    aliasedTo: propertyName 
                };
                associationsConfig[alias] = configEntryForAlias
            })
        }


        Object.defineProperty(
            target,
            propertyName,
            {
                get,
                set
            }
        )

    }

}