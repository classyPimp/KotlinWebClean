import { BaseReactComponent } from '../../../reactUtils/BaseReactComponent';
import { IFormElement, IFormElementProps } from '../../../reactUtils/plugins/formable/IFormElement';
import { BaseModel } from '../../../modelLayer/BaseModel';
import { User } from '../../models/User' 
import * as React from 'react'
import autobind from 'autobind-decorator'
import { ModelCollection } from '../../../modelLayer/ModelCollection'


export class DropDownSelectServerFed extends BaseReactComponent<IFormElementProps> implements IFormElement {

    id: number
    cleanUpOnComponentWillUnmount: ()=>any
    
    props: {
        model: BaseModel,
        propertyName: string,
        registerInput: (element: IFormElement)=>void,
        propertyToShow: string
        propertyToSelect: string,
        queryingFunction: ()=>Promise<ModelCollection<BaseModel>>,
        preselected?: any,
        ref?: (arg: any)=> void,
        optional?: {
            placeholder?: string
            [id:string]:any
        }  
    }

    state: {
      options: Array<BaseModelSelectChoiseWrapper>,
      allOptions: Array<BaseModelSelectChoiseWrapper>
      currentlySelected: BaseModelSelectChoiseWrapper,
      searchInput: any
    } 

    componentDidMount(){
      this.props.registerInput(this)
      
      this.props.queryingFunction().then((it)=>{
        this.prepareOptions(it)
      })
      
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

    prepareOptions(modelsToWrapAsOptions: ModelCollection<BaseModel>) {
      let options = modelsToWrapAsOptions.array.map((it)=>{
        if (this.props.preselected) {
          if (this.props.preselected === it.properties[this.props.propertyToSelect]) {
            let preselected = new BaseModelSelectChoiseWrapper(it, this.props.propertyToShow, this.props.propertyToSelect, true)
            this.state.currentlySelected = preselected
            return preselected
          }
        } 
        return new BaseModelSelectChoiseWrapper(it, this.props.propertyToShow, this.props.propertyToSelect)
      })
      let allOptions = options.slice(0)
      this.state.options = options
      this.state.allOptions = allOptions
      this.setState({})
    }

    render(){
        return <div className="formelements-DropDowndSelectServerFed">
            {this.showPlaceholderIfNecessary()}
            {this.showErrorIfNecessary()}
            <div className="formElements-dropdownSelect">
              <div className="dropdownSelect-header">
                 {this.state.currentlySelected ?
                   <div className="currentlySelected-container">
                     <div className="currentlySelected-value">{this.state.currentlySelected.showProperty()}</div>
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
                     ? 
                       null
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

    @autobind
    cancelSelected(){
      this.clearInputs()
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

    clearInputs(){
      if (this.state.currentlySelected) {
        this.state.currentlySelected.unselect()
      }
      this.setState({currentlySelected: null})
    }

    selectOption(selectOption: BaseModelSelectChoiseWrapper){
      if (this.state.currentlySelected) {
        this.state.currentlySelected.unselect()
      }
      selectOption.select()
      this.setState({currentlySelected: selectOption})
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

class BaseModelSelectChoiseWrapper {
    model: BaseModel

    isSelected: boolean = false

    getSelectedCssClass(): string {
      if (this.isSelected) {
        return "isSelected"
      } else {
        return ""
      }
    } 

    constructor(
        model: BaseModel, 
        propertyToShow: string,
        propertyToSelect: string,
        isSelected?: boolean
    ){
        this.propertyToShow = propertyToShow
        this.model = model
        if (isSelected) {
            this.isSelected = isSelected
        }
        this.propertyToSelect = propertyToSelect
    }

    propertyToSelect: string
    propertyToShow: string

    showProperty(): any{
        return this.model.properties[this.propertyToShow]
    }
    
    select(){
        this.isSelected = true
    }

    unselect(){
        this.isSelected = false
    }

    getSelectedValue(): any {
        if (this.isSelected) {
            return this.model.properties[this.propertyToSelect]
        }
    }
}

