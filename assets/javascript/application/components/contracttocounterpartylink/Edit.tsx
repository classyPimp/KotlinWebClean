import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { ApplicationComponent } from '../ApplicationComponent';
import { match } from 'react-router-dom';
import { PlainInputElement } from '../../../reactUtils/plugins/formable/formElements/PlainInput';
import { MixinFormableTrait } from '../../../reactUtils/plugins/formable/MixinFormableTrait';
import autobind from 'autobind-decorator';
import { FlashMessageQueue } from '../shared/FlashMessageQueue';
import { Modal } from '../shared/Modal';
import { ContractToCounterPartyLinkComponents } from './ContractToCounterPartyLinkComponents'
import { ContractToCounterPartyLink } from '../../models/ContractToCounterPartyLink'
import { Link } from 'react-router-dom';
import { DropDownSelectServerFed } from '../formelements/DropdownSelectServerFed'
import { CounterParty } from '../../models/CounterParty'

export class Edit extends MixinFormableTrait(BaseReactComponent) {

    props: {
      contractToCounterPartyLink: ContractToCounterPartyLink
      onDelete: (contractToCounterPartyLink: ContractToCounterPartyLink) => any
      removable: boolean
    }

    state = {
      formDummy: new ContractToCounterPartyLink()
    }

    modal: Modal


    render(){

        let contractToCounterPartyLink = this.props.contractToCounterPartyLink
        
        return <div className="persontocounterpartylinkreasons-Edit">
          <Modal ref={(it)=>{this.modal = it}}/>
          <p>
            <Link to={`/dashboards/counterParties/${contractToCounterPartyLink.id}`}>
            {`${contractToCounterPartyLink.counterParty.incorporationForm.name} ${contractToCounterPartyLink.counterParty.name}`}
            </Link>
          </p>
          <PlainInputElement
            model = {this.props.contractToCounterPartyLink}
            propertyName = "roleAccordingToContract"
            registerInput = {(it)=>{this.registerInput(it)}}
            optional = {{
              placeholder: "role according to contract"
            }}
          />
          <button onClick={this.update}>
            update
          </button>
          {this.props.removable &&
            <button onClick={()=>{this.delete(contractToCounterPartyLink)}}>
              remove
            </button>
          }
          <button onClick={this.initReplacement}>
            replace
          </button>
        </div>
    }
    

    @autobind
    update() {
      this.collectInputs()
      let currentContractToCounterPartyLink = this.props.contractToCounterPartyLink
      this.props.contractToCounterPartyLink.forContractManageUpdate().then((contractToCounterPartyLink)=>{
        if (contractToCounterPartyLink.isValid()) {
          currentContractToCounterPartyLink.roleAccordingToContract = contractToCounterPartyLink.roleAccordingToContract
        } else {
          currentContractToCounterPartyLink.errors = contractToCounterPartyLink.errors
        }
        this.forceUpdate()
      })

    }

    @autobind
    delete(contractToCounterPartyLink: ContractToCounterPartyLink) {
      contractToCounterPartyLink.destroy().then((it)=>{
        if (it.isValid()) {
          this.props.onDelete(contractToCounterPartyLink) 
        } else {
          let errors = ""
          Object.keys(it.errors).forEach((key)=>{
            let error = it.errors[key]
            errors += (error + "; ") 
          })
          alert("could not be deleted: " + errors)
        }
      })
    }

    @autobind
    initReplacement() {
       this.modal.open(
         <div>
           <DropDownSelectServerFed
            model = {this.state.formDummy}
            propertyName = "id"
            propertyToShow = "name"
            propertyToSelect = "id"
            registerInput = {(it)=>{this.registerInput(it, "forReplacement")}}
            queryingFunction = {CounterParty.formFeedsIndex.bind(CounterParty)}
            preselected = {this.props.contractToCounterPartyLink.counterPartyId}
            optional = {{
              placeholder: "select counter party to replace"
            }}
          />
          <button onClick={this.replace}>
            replace with selected
          </button>
         </div>
       )
    }

    @autobind
    replace() {
      this.collectInputs({namespace: "forReplacement"})
      let counterPartyIdToReplaceWith = this.state.formDummy.id.toString()
      this.props.contractToCounterPartyLink.replace({wilds: {counterPartyIdToReplaceWith}}).then((oneThatReplaces)=>{
        if (oneThatReplaces.isValid()) {
          this.modal.close()
          this.props.contractToCounterPartyLink.properties = oneThatReplaces.properties
          this.setState({})
        } else {
          let errors = ""
          Object.keys(oneThatReplaces.errors).forEach((key)=>{
            let error = oneThatReplaces.errors[key]
            errors += (error + "; ") 
          })
          alert("could not be replaced: " + errors)
        }
      }) 
      
    }
}
