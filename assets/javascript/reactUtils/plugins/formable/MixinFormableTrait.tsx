import { IModelProperties } from '../../../modelLayer/interfaces/IModelProperties';
import { BaseModel } from '../../../modelLayer/BaseModel';
import { AnyConstructor } from '../../../modelLayer/utils/AnyConstructor';
import autobind from 'autobind-decorator';
import { IFormElement, IFormElementProps } from './IFormElement';
import * as React from 'react';

export function MixinFormableTrait<TBase extends AnyConstructor>(Base: TBase) {
    class FormableTrait extends Base {
        
        inputElelementsId: number = -1

        inputs: {[id:string]:{[id:number]: IFormElement}} = {
            default: Object.create(null)
        }

        @autobind
        registerInput(child: IFormElement, namespace: string = "default"){
            if (child == null) {
                return
            }
            if (child.id) {
              return
            } else {
                this.inputElelementsId += 1
                child.id = this.inputElelementsId   
                if (!this.inputs[namespace]) {
                  this.inputs[namespace] = {}
                } 
                this.inputs[namespace][child.id] = child
                child.cleanUpOnComponentWillUnmount= ()=>{this.cleanChildFormable(child.id, namespace)}
            }
        }

        @autobind
        cleanChildFormable(id: number, namespace: string) {
          let inputs = this.inputs[namespace]
          if (inputs) {
            if (inputs[id]) {
              delete inputs[id]
            }
          }
        }

        @autobind
        input(
            element: {new (...args: any[]): IFormElement},
            options: IFormElementProps,
            children?: React.ReactNode[]
        ){
            return React.createElement(
                element,
                options,
                ...children
            )            
        }   

        collectInputs(
          options?: {
            namespace: string
          }
        ){
            let namespace: string = "default"
            if (options) {
              if (options.namespace) {
                namespace = options.namespace
              }
            } 
            let keys = Object.keys(this.inputs[namespace])
            for (let key of keys) {
                let childInput = this.inputs[namespace][key as any]
                if (childInput) {
                    try {
                      childInput.collect()
                    } catch(error) {
                      console.log("warning: error when collecting inputs")
                    }
                }
            }
        }

    }
    return FormableTrait
}