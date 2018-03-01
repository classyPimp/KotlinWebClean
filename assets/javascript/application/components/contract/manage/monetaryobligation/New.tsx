import { BaseReactComponent } from "../../../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { MonetaryObligation } from '../../../../models/MonetaryObligation'
import { MonetaryObligationPart } from '../../../../models/MonetaryObligationPart'
import { CounterParty } from '../../../../models/CounterParty'
import { MixinFormableTrait } from '../../../../../reactUtils/plugins/formable/MixinFormableTrait';
import { PlainInputElement } from '../../../../../reactUtils/plugins/formable/formElements/PlainInput'
import { DropDownSelectServerFed } from '../../../formelements/DropDownSelectServerFed'
import autobind from 'autobind-decorator'
import { ErrorsShow } from '../../../shared/ErrorsShow'
import { ApplicationComponent } from '../../../ApplicationComponent';
import { MonetaryObligationComponents } from './MonetaryObligationComponents'

export class New extends MixinFormableTrait(BaseReactComponent) {

    props: {
      contractId: number,
      onCreateSuccess: (monetaryObligation: MonetaryObligation)=>any
    }

    state: {
      monetaryObligation: MonetaryObligation
    } = {
      monetaryObligation: new MonetaryObligation({contractId: this.props.contractId})
    }


    sequentialIdToUseAsKey = 0

    render(){
        console.log(this.state.monetaryObligation.totalAmount)
        return <div className="persontocounterpartylinkreasons-New">
          <h2>
            initialize new monetaryObligation
          </h2>
          {this.state.monetaryObligation.getErrorsFor('general') &&
              <ErrorsShow errors={this.state.monetaryObligation.getErrorsFor('general')}/>
          }
          <PlainInputElement
            model={this.state.monetaryObligation}
            propertyName="description"
            registerInput={(it)=>{this.registerInput(it)}}
            optional={{placeholder: "payment description"}}
            onInputChange = {this.onTotalAmountChange}
          />
          <PlainInputElement
            model={this.state.monetaryObligation}
            propertyName="totalAmount"
            registerInput={(it)=>{this.registerInput(it)}}
            optional={{placeholder: "totalAmount"}}
            onInputChange = {this.onTotalAmountChange}
            parseAsInt = {true}
          />
          {this.state.monetaryObligation.monetaryObligationParts.map((monetaryObligationPart)=>{
            return <MonetaryObligationComponents.MonetaryObligationParts.New
              key = {monetaryObligationPart.arbitrary["idToBeUsedAsKey"]}
              monetaryObligationPart = {monetaryObligationPart}
              onInputChange = {this.onMonetaryObligationPartInputChange}
              onDelete = {()=>{this.deleteMonetaryObligationPart(monetaryObligationPart)}}
            />
          })}
          <button onClick={this.addMonetaryObligationPart}>
            add payment
          </button>
          <p>
            undistributed amount {this.calculateAmountLeft()}
          </p>
          <button onClick={this.submit}>
            submit
          </button>
        </div>
    }

    @autobind
    onTotalAmountChange() {
      this.setState({})
    }

    @autobind
    addMonetaryObligationPart() {
      let monetaryObligationPart = new MonetaryObligationPart()
      this.sequentialIdToUseAsKey += 1
      monetaryObligationPart.arbitrary = {"idToBeUsedAsKey": this.sequentialIdToUseAsKey}
      this.state.monetaryObligation.monetaryObligationParts.push(monetaryObligationPart)
      this.setState({})
    }

    @autobind
    calculateAmountLeft(): number {
      let totalAmount = this.state.monetaryObligation.totalAmount
      if (!totalAmount) {
        return null
      }
      let accumulator = 0
      this.state.monetaryObligation.monetaryObligationParts.forEach((it)=>{
        if (it.amount) {
          accumulator += it.amount
        }
      })
      return totalAmount - accumulator
    }

    @autobind
    onMonetaryObligationPartInputChange() {
      this.setState({})
    }

    @autobind
    deleteMonetaryObligationPart(monetaryObligationPart: MonetaryObligationPart) {
      this.state.monetaryObligation.monetaryObligationParts.filter((it)=>{
        return it !== monetaryObligationPart
      })
    }

    @autobind
    submit() {
      this.collectInputs()
      this.state.monetaryObligation.create().then((monetaryObligation: MonetaryObligation)=>{
        if (!monetaryObligation.isValid()) {
          try {
          monetaryObligation.monetaryObligationParts.forEach((it)=>{
            this.sequentialIdToUseAsKey += 1
            it.arbitrary = {"idToBeUsedAsKey": this.sequentialIdToUseAsKey}
          })
          this.setState({monetaryObligation})
          } catch (error) {
            console.log(error)
          }
        } else {
          this.props.onCreateSuccess(monetaryObligation)
          alert("success")
        }
      })
    }



}
