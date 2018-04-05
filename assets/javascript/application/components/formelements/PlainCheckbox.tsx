import { BaseReactComponent } from '../../../reactUtils/BaseReactComponent';
import { IFormElement, IFormElementProps } from '../../../reactUtils/plugins/formable/IFormElement';
import { BaseModel } from '../../../modelLayer/BaseModel';
import * as React from 'react'
import autobind from 'autobind-decorator'

export class PlainCheckbox extends BaseReactComponent<
    IFormElementProps
> implements IFormElement {

    id: number
    cleanUpOnComponentWillUnmount: ()=>any

    state: {
      checked: boolean
    } = {
      checked: null
    }

    constructor(...args: Array<any>) {
      super(...args)
      let checked: boolean
      let propertyValue = this.props.model.properties[this.props.propertyName]
      if (propertyValue) {
        checked = true
      } else {
        checked = false
      }
      this.state.checked = checked
    }

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
            {this.showPlaceholderIfNecessary()}
            {this.showErrorIfNecessary()}
            <input 
              type={`checkbox`}
              className = {this.showInvalidCssClassIfInvalid()} 
              checked = {this.state.checked}
              onChange={this.onChange}
              ref={(input)=>{this.mainInput = input}}
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
        let checked = !this.state.checked
        this.setState({checked})
    }

    collect(){
        let value = this.state.checked
        this.props.model.properties[this.props.propertyName] = value
    }

    clearInputs(){

    }


}