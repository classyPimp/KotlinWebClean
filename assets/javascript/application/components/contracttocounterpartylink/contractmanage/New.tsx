import { BaseReactComponent } from "../../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { ContractToCounterPartyLink } from '../../../models/ContractToCounterPartyLink'
import { MixinFormableTrait } from '../../../../reactUtils/plugins/formable/MixinFormableTrait';
import { PlainInputElement } from '../../../../reactUtils/plugins/formable/formElements/PlainInput'
import autobind from 'autobind-decorator'
import { ErrorsShow } from '../../shared/ErrorsShow'
import { ApplicationComponent } from '../../ApplicationComponent';
import { DropDownSelectServerFed } from '../../formelements/DropdownSelectServerFed'
import { CounterParty } from '../../../models/CounterParty'

export class New extends MixinFormableTrait(BaseReactComponent) {

    props: {
      contractId: number
      onCreateSuccess: (contractToCounterPartyLink: ContractToCounterPartyLink) => any
    }

    state: {
      contractToCounterPartyLink: ContractToCounterPartyLink
    } = {
      contractToCounterPartyLink: new ContractToCounterPartyLink({contractId: this.props.contractId})
    }

    render(){
        return <div className="persontocounterpartylinkreasons-New">
          <h2>
            add counter party
          </h2>
          {this.state.contractToCounterPartyLink.getErrorsFor('general') &&
              <ErrorsShow errors={this.state.contractToCounterPartyLink.getErrorsFor('general')}/>
          }
          <PlainInputElement
            model={this.state.contractToCounterPartyLink}
            propertyName="roleAccordingToContract"
            registerInput={(it)=>{this.registerInput(it)}}
            optional={{placeholder: "role according to contract"}}
          />
          <DropDownSelectServerFed
            model = {this.state.contractToCounterPartyLink}
            propertyName = "counterPartyId"
            registerInput = {(it)=>{this.registerInput(it)}}
            propertyToShow = "name"
            propertyToSelect  = "id"
            queryingFunction = {CounterParty.formFeedsIndex.bind(CounterParty)}
            optional = {{
              placeholder: "select counter party to add"
            }}
          />
          <button onClick={this.submit}>
            submit
          </button>
        </div>
    }

    @autobind
    submit(){
      this.collectInputs()
      this.state.contractToCounterPartyLink.create().then((contractToCounterPartyLink)=>{
        if (contractToCounterPartyLink.isValid()) {
          this.props.onCreateSuccess(contractToCounterPartyLink)
          return
        }
        this.setState({contractToCounterPartyLink})
      })
    }

}
