import { BaseReactComponent } from '../../../reactUtils/BaseReactComponent';
import { IFormElement, IFormElementProps } from '../../../reactUtils/plugins/formable/IFormElement';
import { BaseModel } from '../../../modelLayer/BaseModel';
import * as React from 'react'
import autobind from 'autobind-decorator'
import { ModelCollection } from '../../../modelLayer/ModelCollection'


export class PlainFileInput extends BaseReactComponent<IFormElementProps> implements IFormElement {

  id: number

  props: {
    model: BaseModel,
    propertyName: string,
    registerInput: (element: IFormElement) => void
    optional?: {
      placeholder?: string
      [id: string]: any
    }
  }

  state: {
    fileIsSelected: boolean,
    selectedFile: File
  } = {
    fileIsSelected: false,
    selectedFile: null
  }

  mainInput: any

  componentDidMount(){
    this.props.registerInput(this)
  }

  isValid(): boolean{
      return true
  }

  constructor(...args: Array<any>) {
    super(...args)
  }

  render() {
    return <div className="formelements-PlainFileInput">
      {this.showPlaceholderIfNecessary()}
      {this.showErrorIfNecessary()}
      {this.state.fileIsSelected
        ? <div>
          <p>
            selected file: {this.state.selectedFile.name}
          </p>
          <button>
            cancel
          </button>
        </div>
        : <input type="file" ref={(it)=>{this.mainInput = it}} onChange={this.handleInputChange}/>
      }
      
    </div>
  }


  @autobind
  collect(){
    let file = this.state.selectedFile
    this.props.model.properties[this.props.propertyName] = file
  }

  @autobind
  handleInputChange() {
    let file = this.mainInput.files[0]
    if (file) {
      this.setState({fileIsSelected: true, selectedFile: file})
    } else {
      this.setState({fileIsSelected: false, selectedFile: null})
    }
  }

  @autobind
  cancelSelect() {
    this.clearInputs()
    this.setState({fileIsSelected: false})
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
    this.mainInput.value = ""
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

