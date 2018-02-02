import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { ApplicationComponent } from '../ApplicationComponent';
import { match } from 'react-router-dom';
import { PlainInputElement } from '../../../reactUtils/plugins/formable/formElements/PlainInput';
import { MixinFormableTrait } from '../../../reactUtils/plugins/formable/MixinFormableTrait';
import autobind from 'autobind-decorator';
import { FlashMessageQueue } from '../shared/FlashMessageQueue';
import { Modal } from '../shared/Modal';
import { ContractCategoryComponents } from './ContractCategoryComponents'
import { ContractCategory } from '../../models/ContractCategory'


export class Edit extends MixinFormableTrait(BaseReactComponent) {

    props: {
      match: match<any>
    }

    state: {
      contractCategory: ContractCategory
    } = {
      contractCategory: null
    }

    modal: Modal

    componentDidMount(){
      let id = this.props.match.params.id
      ContractCategory.edit({wilds: {id: `${id}`}}).then((contractCategory)=>{
        this.setState({contractCategory})
      })
    }

    render(){
        return <div className="persontocounterpartylinkreasons-Edit">
          {this.state.contractCategory &&
            <div>
              <PlainInputElement 
                  model={this.state.contractCategory}
                  propertyName="name"
                  registerInput={(it)=>{this.registerInput(it)}}
                  optional={{
                    placeholder: "name"
                  }}
              />
              <PlainInputElement 
                  model={this.state.contractCategory}
                  propertyName="description"
                  registerInput={(it)=>{this.registerInput(it)}}
                  optional={{
                    placeholder: "description"
                  }}
              />
              <button onClick={this.submit}>
                update
              </button>
            </div>
          }
        </div>
    }

    @autobind
    submit(){
      this.state.contractCategory.update().then((contractCategory)=>{
        if (contractCategory.isValid()) {
          ApplicationComponent.instance.flashMessageQueue.addMessage(
            "contract category successfully updated"
          )
        } 
        this.setState({contractCategory})
      })
    }

}
