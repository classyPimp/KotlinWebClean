import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { Contract } from '../../models/Contract'
import { ContractCategory } from '../../models/ContractCategory'
import { CounterParty } from '../../models/CounterParty'
import { ContractToCounterPartyLink } from '../../models/ContractToCounterPartyLink'
import { MixinFormableTrait } from '../../../reactUtils/plugins/formable/MixinFormableTrait';
import { PlainInputElement } from '../../../reactUtils/plugins/formable/formElements/PlainInput'
import { DropDownSelectServerFed } from '../formelements/DropdownSelectServerFed'
import autobind from 'autobind-decorator'
import { ErrorsShow } from '../shared/ErrorsShow'
import { ApplicationComponent } from '../ApplicationComponent';

export class New extends MixinFormableTrait(BaseReactComponent) {

    state: {
      counterPartyDummy: CounterParty
      contract: Contract
    } = {
      counterPartyDummy: new CounterParty(),
      contract: new Contract()
    }

    render(){
        return <div className="persontocounterpartylinkreasons-New">
          <h2>
            initialize new contract
          </h2>
          {this.state.contract.getErrorsFor('general') &&
              <ErrorsShow errors={this.state.contract.getErrorsFor('general')}/>
          }
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
            optional = {{
              placeholder: "select category"
            }}
          />
          <div>
            <h3>
              counter parties
            </h3>
            {this.state.contract.contractToCounterPartyLinks.map((contractToCounterPartyLink)=>{
              return <div key={contractToCounterPartyLink.counterPartyId}>
                <p>
                  {contractToCounterPartyLink.counterParty.name}
                </p>
                <PlainInputElement
                  model={contractToCounterPartyLink}
                  propertyName="roleAccordingToContract"
                  registerInput={(it)=>{this.registerInput(it)}}
                  optional={{placeholder: "role in contract"}}
                />
                <button onClick={()=>{this.removeContractToCounterPartyLink(contractToCounterPartyLink)}}>
                  remove
                </button>
              </div>
            })}
            <DropDownSelectServerFed
              model = {this.state.counterPartyDummy}
              propertyName = "id"
              propertyToShow = "name"
              propertyToSelect = "id"
              registerInput = {(it)=>{this.registerInput(it, "counterPartyDummy")}}
              queryingFunction = {CounterParty.formFeedsIndex.bind(CounterParty)}
              optional = {{
                placeholder: "select counter party to add"
              }}
            />
            <button onClick={this.addCounterParty}>
              add selected counter party
            </button>
          </div>
                    
          <button onClick={this.submit}>
            submit
          </button>
        </div>
    }

    @autobind
    removeContractToCounterPartyLink(contractToCounterPartyLink: ContractToCounterPartyLink) {
      this.state.contract.contractToCounterPartyLinks.filter((it)=>{
        return it !== contractToCounterPartyLink
      })
      this.setState({})
    }

    @autobind
    addCounterParty() {
      this.collectInputs({namespace: "counterPartyDummy"})

      let id = this.state.counterPartyDummy.id
      if (!id) {
        alert("select counter party first")
        return
      } 

      CounterParty.show({wilds: {id: id.toString()}}).then((counterParty)=>{
        let alreadyAssigned = false
        this.state.contract.contractToCounterPartyLinks.forEach((it)=>{
          if (it.counterPartyId === counterParty.id) {
            alreadyAssigned = true
          }
        })
        if (alreadyAssigned) {
          alert(`${counterParty.name} already selected`)
          return
        }  

        let link = new ContractToCounterPartyLink({counterParty, counterPartyId: id})
        this.state.contract.contractToCounterPartyLinks.push(link)

        this.setState({})
      })
    }

    @autobind
    submit(){
      this.collectInputs()
      
      let currentContract = this.state.contract
      currentContract.validate()
      if (!currentContract.isValid()) {
        this.setState({contract: currentContract})
        return
      }

      currentContract.create().then((contract)=>{
        if (!contract.isValid()) {
              this.setState({contract})
              return
        } 
        ApplicationComponent.instance.flashMessageQueue.addMessage(
          "contract category successfully created"
        )
        this.setState({contract})
      })
    }

}
