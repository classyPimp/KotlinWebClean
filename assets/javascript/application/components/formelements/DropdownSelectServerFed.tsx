import { BaseReactComponent } from '../../../reactUtils/BaseReactComponent';
import { IFormElement, IFormElementProps } from '../../../reactUtils/plugins/formable/IFormElement';
import { BaseModel } from '../../../modelLayer/BaseModel';
import { User } from '../../models/User' 
import * as React from 'react'
import autobind from 'autobind-decorator'
import { ModelCollection } from '../../../modelLayer/ModelCollection'


export class DropDownSelectServerFed extends BaseReactComponent<IFormElementProps> implements IFormElement {

    id: number

    props: {
        model: BaseModel,
        propertyName: string,
        registerInput: (element: IFormElement)=>void,
        propertyToShow: string
        propertyToSelect: string,
        modelsToWrapAsOptions: ModelCollection<BaseModel>,
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
      this.prepareOptions(this.props.modelsToWrapAsOptions)
    }

    prepareOptions(modelsToWrapAsOptions: ModelCollection<BaseModel>) {
      let options = modelsToWrapAsOptions.array.map((it)=>{
        return new BaseModelSelectChoiseWrapper(it, this.props.propertyToShow, this.props.propertyToSelect)
      })
      let allOptions = options.slice(0)
      this.state.options = options
      this.state.allOptions = allOptions
    }

    componentWillReceiveProps(nextProps: any){
      let options = nextProps.modelsToWrapAsOptions
      this.prepareOptions(options)
      this.forceUpdate()
    }

    render(){
        return <div className="formelements-DropDowndSelectServerFed">
            {this.showPlaceholderIfNecessary()}
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
      console.log("collecting")
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

