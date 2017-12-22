import { IAssociationsConfig, IAssociationsConfigEntry } from '../interfaces/IAssociationsConfig';
import { BaseModel } from '../BaseModel';
import { IModelConstructor } from '../interfaces/IModelConstructor';
import { AssociationTypesEnum } from '../AssociationTypesEnum';
import { ModelRegistry } from '../ModelRegistry'

export function HasOne(stringifiedClassName: string, parseAliases: Array<string> = null) {

    return function(target: BaseModel, propertyName: string) {

        let getter = function() {
            return this.properties[propertyName]
        }

        let setter = function(valueToAssign: any) {
            this.properties[propertyName] = valueToAssign
        }

        //get static associationsConfig or assign if undefined
        let associationsConfig: IAssociationsConfig = (target.constructor as typeof BaseModel).getAssociationsConfig()
      
        let associationsConfigEntry: IAssociationsConfigEntry = {
            associationType: AssociationTypesEnum.hasOne,
            getThatModelConstructor: ModelRegistry.modelGetterFuntion(stringifiedClassName),
            aliasedTo: null
        } 
        associationsConfig[propertyName] = associationsConfigEntry

        if (parseAliases) {
            //create entry for alis, so when parsing it defines that model correctly
            //but assigns to the properties under key to which it is aliased
            parseAliases.forEach((alias)=>{
                let associationsConfigEntryForAlias: IAssociationsConfigEntry = {
                    associationType: AssociationTypesEnum.hasOne,
                    getThatModelConstructor: ModelRegistry.modelGetterFuntion(stringifiedClassName),
                    aliasedTo: propertyName 
                };

                associationsConfig[alias] = associationsConfigEntryForAlias
            })
        }

        Object.defineProperty(
            target,
            propertyName,
            {
                get: getter,
                set: setter
            }
        )

    }

}