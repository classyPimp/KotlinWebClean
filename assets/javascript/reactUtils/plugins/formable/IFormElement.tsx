import { BaseModel } from '../../../modelLayer/BaseModel';
import { IModelProperties } from '../../../modelLayer/interfaces/IModelProperties';
import { BaseReactComponent } from '../../BaseReactComponent';
import { Component } from 'react';
import { MixinFormableTrait } from './MixinFormableTrait'


export interface  IFormElement extends BaseReactComponent {
    isValid: ()=>boolean
    collect: ()=>void
    clearInputs: ()=>void
    id: number
}

export interface IFormElementProps {
    model?: BaseModel,
    propertyName: string,
    registerInput: (element: IFormElement)=>void
    ref?: (arg: any)=> void,
    optional?: {
        placeholder?: string
        [id:string]:any
    }
}