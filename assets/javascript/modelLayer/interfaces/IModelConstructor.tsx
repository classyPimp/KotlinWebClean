import { BaseModel } from '../BaseModel';
import { IModelProperties } from './IModelProperties';

export interface IModelConstructor {
    new (props: IModelProperties): BaseModel
}