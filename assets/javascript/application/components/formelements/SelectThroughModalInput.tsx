import { BaseReactComponent } from '../../../reactUtils/BaseReactComponent';
import { IFormElement, IFormElementProps } from '../../../reactUtils/plugins/formable/IFormElement';
import { BaseModel } from '../../../modelLayer/BaseModel';
import * as React from 'react'
import autobind from 'autobind-decorator'
import { ModelCollection } from '../../../modelLayer/ModelCollection'
import { Modal } from '../shared/Modal'

export class SelectThroughModalInput extends BaseReactComponent<IFormElementProps> implements IFormElement {

  id: number
  cleanUpOnComponentWillUnmount: ()=>any

  props: {
    model: BaseModel,
    propertyName: string,
    registerInput: (element: IFormElement) => void
    propertyToShow: string
    propertyToSelect: string
    preselected?: BaseModel
    modalContent: any
    optional?: {
      placeholder?: string
      [id: string]: any
    }
  }

  state: {
    selectedModel: BaseModel
  } = {
    selectedModel: null
  }

  modal: Modal

  componentDidMount(){
    this.props.registerInput(this)  
  }

  isValid(): boolean{
     return true
  }

  constructor(...args: Array<any>) {
    super(...args)
    if (this.props.preselected) {
      this.state.selectedModel = this.props.preselected
    }
  }

  render() {
    return <div className="formelements-DropDowndSelectServerFed">
      <Modal ref = {(it)=>this.modal = it}/>
      {this.showPlaceholderIfNecessary()}
      {this.state.selectedModel &&
        <p>
          {this.state.selectedModel.properties[this.props.propertyToShow]}
        </p>
      }
      <button onClick = {this.initSelection}>
        {this.state.selectedModel
          ? "search and select another"
          : "search and select"
        }
      </button>
    </div>
  }

  @autobind
  collect(){
    let selectedModel = this.state.selectedModel
    if (selectedModel) {
      let selectedValue = selectedModel.properties[this.props.propertyToSelect]
      this.props.model.properties[this.props.propertyName] = selectedValue
    }
  }

  @autobind
  initSelection() {
    this.modal.open(
      <this.props.modalContent 
        onSelected = {this.onSelected}
      />
    )
  }

  @autobind
  onSelected(selectedModel: BaseModel) {
    console.log("onSelected")
    console.log(selectedModel)
    this.modal.close()
    this.setState({selectedModel})
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
    this.setState({
      selectedModel: null
    })
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
