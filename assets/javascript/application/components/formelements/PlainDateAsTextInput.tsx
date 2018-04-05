import autobind from 'autobind-decorator';
import { BaseModel } from '../../../modelLayer/BaseModel';
import { BaseReactComponent } from '../../../reactUtils/BaseReactComponent'
import { IFormElement, IFormElementProps } from '../../../reactUtils/plugins/formable/IFormElement';
import { TimeUtils } from '../../services/TimeUtils'
import * as React from 'react';

export class PlainDateAsTextInput extends BaseReactComponent<
    IFormElementProps
> implements IFormElement {

    props: {
        model?: BaseModel,
      propertyName: string,
      registerInput: (element: IFormElement)=>void
      displayFormat?: string
      parseFormat?: string
      ref?: (arg: any)=> void
      onInputChange?: ()=>any
      parseAsInt?: boolean
      optional?: {
          placeholder?: string
          [id:string]:any
      }
    } 

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

    @autobind
    constructDefaultInputValue(): string {
      let valueAtProps = this.props.model.properties[this.props.propertyName]
      if (!valueAtProps) {
        return null
      }
      return TimeUtils.timestampStringToReadableDateString(valueAtProps)
    }

    render(){
        return <div className="formElements-plainInput">
            {/*{this.showPlaceholderIfNecessary()}*/}
            {this.showErrorIfNecessary()}
            <input type={`text ${this.showInvalidCssClassIfInvalid()}`} 
                defaultValue={
                    this.constructDefaultInputValue()
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
        if (newValue) {
          this.props.model.properties[this.props.propertyName] = TimeUtils.stringToTimeStampString(newValue)
        }
    }

    clearInputs(){

    }


}