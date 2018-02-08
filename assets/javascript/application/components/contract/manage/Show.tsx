import { BaseReactComponent } from "../../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { ApplicationComponent } from '../../ApplicationComponent';
import { match } from 'react-router-dom';
import { PlainInputElement } from '../../../../reactUtils/plugins/formable/formElements/PlainInput';
import { MixinFormableTrait } from '../../../../reactUtils/plugins/formable/MixinFormableTrait';
import autobind from 'autobind-decorator';
import { FlashMessageQueue } from '../../shared/FlashMessageQueue';
import { Modal } from '../../shared/Modal';
import { ContractComponents } from '../ContractComponents'
import { Contract } from '../../../models/Contract'
import { ContractCategory } from '../../../models/ContractCategory'
import { DropDownSelectServerFed } from '../../formelements/DropDownSelectServerFed'

export class Show extends MixinFormableTrait(BaseReactComponent) {

    props: {
      match: match<any>
    }

    state: {
      contract: Contract
    } = {
      contract: null
    }

    modal: Modal

    componentDidMount(){
      let id = this.props.match.params.id
      Contract.manageEdit({wilds: {id: `${id}`}}).then((contract)=>{
        this.setState({contract})
      })
    }

    render(){
        if (!this.state.contract) {
          return <div>
          </div>
        }

        return <div className="persontocounterpartylinkreasons-Edit">
          <div>
            <PlainInputElement 
                model={this.state.contract}
                propertyName="description"
                registerInput={(it)=>{this.registerInput(it)}}
                optional={{
                  placeholder: "description"
                }}
            />
            <DropDownSelectServerFed
              model = {this.state.contract}
              propertyName = "contractCategoryId"
              propertyToShow = "name"
              propertyToSelect = "id"
              registerInput = {(it)=>{this.registerInput(it)}}
              queryingFunction = {ContractCategory.inputFeedsIndex.bind(ContractCategory)}
              optional = {{
                placeholder: "select category"
              }}
            />
            <button onClick={this.submit}>
              update
            </button>
          </div>
          <div>
            {this.state.contract.contractToCounterPartyLinks.map((contractToCounterPartyLink)=>{
              return <div/>
            })}
          </div>
          <div>
            
          </div>
        </div>
    }

    @autobind
    submit(){
      this.state.contract.update().then((contract)=>{
        if (contract.isValid()) {
          ApplicationComponent.instance.flashMessageQueue.addMessage(
            "contract category successfully updated"
          )
        } 
        this.setState({contract})
      })
    }

}
