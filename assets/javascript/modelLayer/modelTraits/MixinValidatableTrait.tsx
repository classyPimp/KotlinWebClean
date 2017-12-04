import {IModelProperties} from "../interfaces/IModelProperties"
import {ModelCollection} from "../ModelCollection"
import {BaseModel} from "../BaseModel"
import {AnyConstructor} from "../utils/AnyConstructor"

export function MixinValidatableTrait<TBase extends AnyConstructor>(Base: TBase) {
    
    return class ValidatableTrait extends Base {

        properties: IModelProperties
        errors: IErrorsContainer
        hasFile: Boolean = false

        getErrorsFor(propertyName: string): Array<string> | undefined {
            if (this.errors) {
              return this.errors[propertyName]
            } else {
               return undefined
            }
        }

        addErrorFor(propertyName: string, ...errorsToAdd: string[]) {
            if (this.errors) {
                let errors = this.errors[propertyName]
                if (errors) {
                    errors.push(...errorsToAdd)
                } else {
                   this.errors[propertyName] = errorsToAdd   
                }
            } else {
                this.errors = {}
                this.errors[propertyName] = errorsToAdd
            }
        }
        
        validate(){
            this.errors = undefined
            for (let key of Object.keys(this.properties)) {
                let value = this.properties[key]
                let validator = (this as any)[`${key}Validator`]
                if (!!validator && typeof validator === "function") {
                    (this as any)[`${key}Validator`]()
                    continue
                }
                if (value instanceof ModelCollection) {
                    value.forEach((it)=>{
                        (it as ValidatableTrait).validate()
                        this.hasFile = it.hasFile
                    })
                }
                if (value instanceof ValidatableTrait) {
                    value.validate()
                    this.hasFile = value.hasFile
                    if (!value.isValid()) {
                        this.addErrorFor("nestedErrors", key)
                    }
                }
            }
            let errors = this.properties.errors
            if (errors) {
                Object.keys(errors).forEach((key)=>{
                    this.addErrorFor(key, errors[key]) 
                })
            }
            this.properties.errors = undefined
        }

        isValid(): boolean {
            if (this.errors) {
                return false
            } else {
                return true
            }
        }

        resetErrors(){
            this.errors = null
            this.hasFile = false
            for (let key of Object.keys(this.properties)) {
                let value = this.properties[key]
                if (value instanceof ModelCollection) {
                   value.forEach((it)=>{
                      it.resetErrors()
                   })
                } else if (value instanceof ValidatableTrait) {
                    value.resetErrors()
                }
            }
        }
    }
}