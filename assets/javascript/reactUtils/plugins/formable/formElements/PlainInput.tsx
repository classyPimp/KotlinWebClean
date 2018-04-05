import autobind from 'autobind-decorator';
import { BaseModel } from '../../../../modelLayer/BaseModel';
import { BaseReactComponent } from '../../../BaseReactComponent'
import { IFormElement, IFormElementProps } from '../IFormElement';
import * as React from 'react';

export class PlainInputElement extends BaseReactComponent<
    IFormElementProps
> implements IFormElement {


    id: number
    cleanUpOnComponentWillUnmount: ()=>any

    isValid(): boolean {
        let errors = this.props.model.getErrorsFor(this.props.propertyName)
        if (errors) {
            return true
        } else {
            return false
        }
    }

    componentDidMount(){
        this.props.registerInput(this)
    }

    mainInput: any
    
    @autobind
    showPlaceholderIfNecessary(){
        if (this.props.optional && this.props.optional.placeholder) {
            return (
                <p>
                    {this.props.optional.placeholder}
                </p>
            )
        }
    }

    @autobind
    showErrorIfNecessary(){
        let errors = this.props.model.getErrorsFor(this.props.propertyName)
        if(errors) {
            return <div className="errors-list">
                {errors.map((error, index)=>{
                    return <p className="errors-individual" key={index}>
                        {error}
                    </p>
                })}
            </div>
        }
    }

    @autobind
    showInvalidCssClassIfInvalid(): string {
        if (this.props.model.getErrorsFor(this.props.propertyName)) {
            return "invalid"
        }
    }

    render(){
        return <div className="formElements-plainInput">
            {/*{this.showPlaceholderIfNecessary()}*/}
            {this.showErrorIfNecessary()}
            <input type={`text ${this.showInvalidCssClassIfInvalid()}`} 
                defaultValue={
                    this.props.model.properties[this.props.propertyName]
                }
                onChange={this.onChange}
                ref={(input)=>{this.mainInput = input}}
                placeholder={this.getPlaceHolder()}
            />
        </div>
    }

    @autobind
    getPlaceHolder(): string {
      if (this.props.optional) {
        return this.props.optional.placeholder
      }
    }

    @autobind
    onChange(){
        this.collect()
        if (this.props.onInputChange) {
          this.props.onInputChange()
        }
    }

    collect(){
        let newValue = this.mainInput.value
        if (this.props.parseAsInt) {
          newValue = parseInt(newValue)
        }
        this.props.model.properties[this.props.propertyName] = newValue
    }

    clearInputs(){

    }


}