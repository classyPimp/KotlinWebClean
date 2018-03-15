import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { Contract } from '../../models/Contract'
import { ContractCategory } from '../../models/ContractCategory'
import { CounterParty } from '../../models/CounterParty'
import { ContractToCounterPartyLink } from '../../models/ContractToCounterPartyLink'
import { MixinFormableTrait } from '../../../reactUtils/plugins/formable/MixinFormableTrait';
import { PlainInputElement } from '../../../reactUtils/plugins/formable/formElements/PlainInput'
import { DropDownSelectServerFed } from '../formelements/DropDownSelectServerFed'
import { PlainDateAsTextInput } from '../formelements/PlainDateAsTextInput'
import autobind from 'autobind-decorator'
import { ErrorsShow } from '../shared/ErrorsShow'
import { ApplicationComponent } from '../ApplicationComponent';


export class EditGeneralInfo extends MixinFormableTrait(BaseReactComponent) {

    state: {
      contract: Contract
    } = {
      contract: null
    }

    componentDidMount() {
      let contractId = this.props.match.params.contractId
      Contract.editGeneralInfo({wilds: {contractId}}).then((contract)=>{
        this.setState({contract})
      })
    }

    render(){
        if (!this.state.contract) {
          return <div/>
        }

        return <div className="persontocounterpartylinkreasons-New">
          {this.state.contract.getErrorsFor('general') &&
              <ErrorsShow errors={this.state.contract.getErrorsFor('general')}/>
          }
          <p>
            internal number: {this.state.contract.contractStatus.internalNumber}
          </p>
          <PlainInputElement
            model={this.state.contract.contractStatus}
            propertyName="assignedNumber"
            registerInput={(it)=>{this.registerInput(it)}}
            optional={{placeholder: "assigned number"}}
          />
          <PlainDateAsTextInput
            model={this.state.contract.contractStatus}
            propertyName="formalDate"
            displayFormat="DD.MM.YYYY"
            registerInput={(it)=>{this.registerInput(it)}}
            optional={{placeholder: "from date"}}
          />
          <PlainInputElement
            model={this.state.contract}
            propertyName="description"
            registerInput={(it)=>{this.registerInput(it)}}
            optional={{placeholder: "description"}}
          />
          <DropDownSelectServerFed
            model = {this.state.contract}
            propertyName = "contractCategoryId"
            propertyToShow = "name"
            propertyToSelect = "id"
            registerInput = {(it)=>{this.registerInput(it)}}
            queryingFunction = {ContractCategory.inputFeedsIndex.bind(ContractCategory)}
            preselected = { this.state.contract.contractCategoryId }
            optional = {{
              placeholder: "select category"
            }}
          />     
          <button onClick={this.submit}>
            update
          </button>
        </div>
    }
    
    @autobind
    submit(){
      this.collectInputs()
      
      this.state.contract.updateGeneralInfo().then((contract)=>{
        if (contract.isValid()) {
          alert("successfully updated")
        } else {
          this.setState({contract})
        }
      })
    }

}
