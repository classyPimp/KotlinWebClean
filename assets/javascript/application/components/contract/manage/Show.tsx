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
import { DropDownSelectServerFed } from '../../formelements/DropdownSelectServerFed'
import { ContractToCounterPartyLinkComponents } from '../../contracttocounterpartylink/ContractToCounterPartyLinkComponents'
import { ContractToUploadedDocumentLinkComponents } from '../../contracttouploadeddocumentlink/ContractToUploadedDocumentLinkComponents'
import { MonetaryObligation } from '../../../models/MonetaryObligation'
import { MonetaryObligationComponents } from './monetaryobligation/MonetaryObligationComponents'

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
      console.log("should fetch")
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
          <Modal ref={(it)=>{this.modal = it}}/>
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
              preselected = {this.state.contract.contractCategoryId}
              optional = {{
                placeholder: "select category"
              }}
            />
            <button onClick={this.submit}>
              update
            </button>
          </div>
          <ContractToCounterPartyLinkComponents.Index
            contractId={this.state.contract.id}
            contractToCounterPartyLinks={this.state.contract.contractToCounterPartyLinks}
          />
          <ContractToUploadedDocumentLinkComponents.manage.Index />
          <MonetaryObligationComponents.Index
            contractId = {this.state.contract.id}
          />
          <button onClick={this.initMonetaryObligationAddition}>
            add credit payments
          </button>
        </div>
    }

    @autobind
    submit(){
      let contractAtState = this.state.contract
      contractAtState.manageUpdate().then((contract)=>{
        if (contract.isValid()) {
          ApplicationComponent.instance.flashMessageQueue.addMessage(
            "contract successfully updated"
          )
          contractAtState.contractCategoryId = contract.contractCategoryId
          contractAtState.contractCategory = contract.contractCategory 
          contractAtState.description = contract.description
        } else {
          contractAtState.errors = contract.errors
        }
        this.setState({contract: contractAtState})
      })
    }

    @autobind
    initMonetaryObligationAddition() {
      this.modal.open(
        <MonetaryObligationComponents.New
          contractId = {this.state.contract.id}
          onCreateSuccess = {(monetaryObligation: MonetaryObligation)=>this.onMonetaryObligationCreateSuccess(monetaryObligation)}
        />
      )
    }

    @autobind
    onMonetaryObligationCreateSuccess(monetaryObligation: MonetaryObligation) {
      this.state.contract.monetaryObligations.push(monetaryObligation)
      this.modal.close()
      this.setState({})
    }

}
