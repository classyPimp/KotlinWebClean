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

export class Edit extends MixinFormableTrait(BaseReactComponent) {

    props: {
      monetaryObligation: MonetaryObligation
    }

    sequentialIdToUseAsKey = 0

    componentWillMount() {
      this.props.monetaryObligation.monetaryObligationParts.forEach((monetaryObligationPart)=>{
        if (monetaryObligationPart.arbitrary) {
          monetaryObligationPart.arbitrary["idToBeUsedAsKey"] = this.sequentialIdToUseAsKey
        } else {
          monetaryObligationPart.arbitrary = {"idToBeUsedAsKey": this.sequentialIdToUseAsKey}
        }
        this.sequentialIdToUseAsKey += 1
      })
    }

    render(){
        return <div className="persontocounterpartylinkreasons-New">
          <h2>
            edit monetaryObligation
          </h2>
          {this.props.monetaryObligation.getErrorsFor('general') &&
              <ErrorsShow errors={this.props.monetaryObligation.getErrorsFor('general')}/>
          }
          <PlainInputElement
            model={this.props.monetaryObligation}
            propertyName="description"
            registerInput={(it)=>{this.registerInput(it)}}
            optional={{placeholder: "payment description"}}
            onInputChange = {this.onTotalAmountChange}
          />
          <PlainInputElement
            model={this.props.monetaryObligation}
            propertyName="totalAmount"
            registerInput={(it)=>{this.registerInput(it)}}
            optional={{placeholder: "totalAmount"}}
            onInputChange = {this.onTotalAmountChange}
            parseAsInt = {true}
          />
          {this.props.monetaryObligation.monetaryObligationParts.map((monetaryObligationPart)=>{
            return <MonetaryObligationComponents.MonetaryObligationParts.Edit
              key = {monetaryObligationPart.arbitrary["idToBeUsedAsKey"]}
              monetaryObligationPart = {monetaryObligationPart}
              onInputChange = {this.onMonetaryObligationPartInputChange}
              onDelete = {this.deleteMonetaryObligationPart}
              onDeleteCancel = {this.deleteCancelMonetaryObligationPart}
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
      this.props.monetaryObligation.monetaryObligationParts.push(monetaryObligationPart)
      this.setState({})
    }

    @autobind
    calculateAmountLeft(): number {
      let totalAmount = this.props.monetaryObligation.totalAmount
      if (!totalAmount) {
        return null
      }
      let accumulator = 0
      this.props.monetaryObligation.monetaryObligationParts.forEach((it)=>{
        if (!it.properties['markedForDestruction']) {
          if (it.amount) {
            accumulator += it.amount
          }
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
      if (monetaryObligationPart.id) {
        monetaryObligationPart.properties['markedForDestruction'] = true  
      } else {
        this.props.monetaryObligation.monetaryObligationParts.filter((it)=>{
          return it !== monetaryObligationPart
        })
      }
      
      this.forceUpdate()
    }

    @autobind
    deleteCancelMonetaryObligationPart(monetaryObligationPart: MonetaryObligationPart) {
      delete(monetaryObligationPart.properties['markedForDestruction'])
      this.forceUpdate()
    }

    @autobind
    submit() {
      this.collectInputs()
      this.props.monetaryObligation.update().then((monetaryObligation: MonetaryObligation)=>{
        if (!monetaryObligation.isValid()) {
          monetaryObligation.monetaryObligationParts.forEach((it)=>{
            this.sequentialIdToUseAsKey += 1
            it.arbitrary = {"idToBeUsedAsKey": this.sequentialIdToUseAsKey}
          })
          this.props.monetaryObligation.properties = monetaryObligation.properties
          this.props.monetaryObligation.errors = monetaryObligation.errors
          this.forceUpdate()
        } else {
          alert("successfully updated")
          monetaryObligation.monetaryObligationParts.forEach((it)=>{
            this.sequentialIdToUseAsKey += 1
            it.arbitrary = {"idToBeUsedAsKey": this.sequentialIdToUseAsKey}
          })
          this.props.monetaryObligation.properties = monetaryObligation.properties
          this.props.monetaryObligation.resetErrors()
          this.forceUpdate()
        }
      })
    }



}
