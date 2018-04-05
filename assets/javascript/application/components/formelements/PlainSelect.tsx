import { BaseReactComponent } from '../../../reactUtils/BaseReactComponent';
import { IFormElement, IFormElementProps } from '../../../reactUtils/plugins/formable/IFormElement';
import { BaseModel } from '../../../modelLayer/BaseModel';
import * as React from 'react'
import autobind from 'autobind-decorator'
import { ModelCollection } from '../../../modelLayer/ModelCollection'


export class PlainSelect extends BaseReactComponent<IFormElementProps> implements IFormElement {

  id: number
  cleanUpOnComponentWillUnmount: ()=>any

  props: {
    model: BaseModel,
    propertyName: string,
    registerInput: (element: IFormElement) => void
    options: {[id: string]: any}
    preselected?: {[id: string]: any}
    optional?: {
      placeholder?: string
      [id: string]: any
    }
  }

  state: {
    options: Array<SelectChoiceWrapper>
    allOptions: Array<SelectChoiceWrapper>
    currentlySelected: SelectChoiceWrapper
    searchInput: any
  }

  componentDidMount(){
    this.props.registerInput(this)
    this.prepareOptions()   
  }

  prepareOptions(){
    let options = []
    let propsOptions = this.props.options

    let preselected = this.props.preselected

    let preselectedKey: string = undefined
    if (preselected) {
      preselectedKey = Object.keys(preselected)[0]
    }

    for (let key of Object.keys(propsOptions)) {
      let value = propsOptions[key]
      if (preselected) {
         if (preselectedKey === key && preselected[preselectedKey] === value) {
           let preselectedChoice = new SelectChoiceWrapper(value, key)
           preselectedChoice.select()
           this.state.currentlySelected = preselectedChoice
         }
      }    
      options.push(new SelectChoiceWrapper(value, key))
    }

    let allOptions = options.slice(0)
    this.state.options = options
    this.state.allOptions = allOptions
    this.setState({})
  }

  isValid(): boolean{
      return true
  }

  constructor(...args: Array<any>) {
    super(...args)
    this.state = {
      options: [],
      currentlySelected: null,
      searchInput: null,
      allOptions: []
    }
  }

  render() {
    return <div className="formelements-DropDowndSelectServerFed">
      {this.showPlaceholderIfNecessary()}
      {this.showErrorIfNecessary()}
      <div className="formElements-dropdownSelect">
        <div>
          {this.state.currentlySelected
            ? <div className="currentlySelected-container">
              <div className="currentlySelected-value">
                {this.state.currentlySelected.showProperty()}
              </div>
              <span className="cancelSelected" onClick={this.cancelSelected}>X</span>
            </div>

            : <div>
              <input type="text" onChange={this.searchOptionsUsingInput} ref={(it)=>{this.state.searchInput = it}}/>
              <span className="openHint">V</span>
            </div>
          }
        </div>
        <div className="dropdownSelect-optionsContainer">
          {
            this.state.options.map((it, index)=>{
               return it.isSelected 
               ? null

               : <span 
                   className={`dropdown-selectOption ${it.getSelectedCssClass()}`}
                   onClick={()=>{this.selectOption(it)}}
                   key={index}
                 >
                 {it.showProperty()}
               </span>
            })
          }
        </div>
      </div>
      
    </div>
  }

  @autobind
  selectOption(option: SelectChoiceWrapper){
    if (this.state.currentlySelected) {
      this.state.currentlySelected.unselect()
    }
    option.select()
    this.setState({currentlySelected: option})
  }

  @autobind
  cancelSelected(){
    this.clearInputs()
  }

  @autobind
  collect(){
    let value: any
    if (this.state.currentlySelected) {
      value = this.state.currentlySelected.getSelectedValue()
    } else {
      value = null
    }
    this.props.model.properties[this.props.propertyName] = value
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
  searchOptionsUsingInput() {
    let inputValue = this.state.searchInput.value
    if (inputValue === "") {
      this.setState({options: this.state.allOptions.slice(0)})
    } else {
      this.setState({options: this.state.options.filter((it)=>{
        return it.showProperty().toLowerCase().startsWith(inputValue.toLowerCase())
      })})
    }
  }

  clearInputs(){
    if (this.state.currentlySelected) {
      this.state.currentlySelected.unselect()
    }
    this.setState({currentlySelected: null})
  }

  @autobind
  showPlaceholderIfNecessary() {
    if (this.props.optional && this.props.optional.placeholder) {
      return <p>
        {this.props.optional.placeholder}
      </p>
      
    }
  }

}

class SelectChoiceWrapper {
  isSelected: boolean = false

  propertyToSelect: string
  propertyToShow: string

  constructor(propertyToSelect: any, propertyToShow: any) {
    this.propertyToSelect = propertyToSelect
    this.propertyToShow = propertyToShow
  }

  getSelectedCssClass(): string {
    return this.isSelected
     ? "isSelected"
     : ""
  }

  showProperty(): any {
    return this.propertyToShow
  }

  select(){
    this.isSelected = true
  }

  unselect(){
    this.isSelected = false
  }

  getSelectedValue(): any {
    if (this.isSelected) {
      return this.propertyToSelect
    }
  }
}