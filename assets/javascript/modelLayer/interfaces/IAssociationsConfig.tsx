import { AssociationTypesEnum } from '../AssociationTypesEnum';
import { IModelProperties } from './IModelProperties';
import { BaseModel } from '../BaseModel';
import { IModelConstructor } from './IModelConstructor';

export interface IAssociationsConfig {
    [id: string]: IAssociationsConfigEntry 
}

export interface IAssociationsConfigEntry {
    associationType: AssociationTypesEnum,
    thatModelConstructor: IModelConstructor,
    aliasedTo: string | null
}