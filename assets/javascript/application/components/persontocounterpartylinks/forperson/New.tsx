import { BaseReactComponent } from "../../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { PersonToCounterPartyLink } from '../../../models/PersonToCounterPartyLink'
import { CounterParty } from '../../../models/CounterParty'
import { Person } from '../../../models/Person'
import { PersonToCounterPartyLinkReason } from '../../../models/PersonToCounterPartyLinkReason'
import { MixinFormableTrait } from '../../../../reactUtils/plugins/formable/MixinFormableTrait';
import { PlainInputElement } from '../../../../reactUtils/plugins/formable/formElements/PlainInput'
import autobind from 'autobind-decorator'
import { ErrorsShow } from '../../shared/ErrorsShow'
import { ApplicationComponent } from '../../ApplicationComponent';
import { DropDownSelectServerFed } from '../../formelements/DropdownSelectServerFed'
import { Router, Route, Link, match, Switch } from 'react-router-dom';
import { SelectThroughModalInput } from '../../formelements/SelectThroughModalInput'
import { FormSelectIndex } from '../../counterParties/FormSelectIndex'

export class New extends MixinFormableTrait(BaseReactComponent) {

    props: {
      match?: match<any>
    } 

    state: {
      personToCounterPartyLink: PersonToCounterPartyLink
    } = {
      personToCounterPartyLink: new PersonToCounterPartyLink()
    }

    constructor(...args: Array<any>) {
      super(...args)
      let personId = parseInt(this.props.match.params.personId)
      this.state.personToCounterPartyLink.personId = personId
    }

    render(){
        return <div className="persontocounterpartylinks-New">
          {this.state.personToCounterPartyLink.getErrorsFor('general') &&
              <ErrorsShow errors={this.state.personToCounterPartyLink.getErrorsFor('general')}/>
          }
          <SelectThroughModalInput 
            model = {this.state.personToCounterPartyLink}
            propertyName = "counterPartyId"
            registerInput = {(it)=>{this.registerInput(it)}}
            modalContent = {FormSelectIndex}
            propertyToShow = "name"
            propertyToSelect = "id"
            optional = {{
              placeholder: "select counter party to link"
            }}
          />
          <DropDownSelectServerFed 
              model={this.state.personToCounterPartyLink}
              propertyName="personToCounterPartyLinkReasonId"
              queryingFunction = { PersonToCounterPartyLinkReason.formFeedsIndex.bind(PersonToCounterPartyLinkReason) }
              propertyToSelect="id" 
              propertyToShow="reasonName"
              registerInput={(it)=>{this.registerInput(it)}}
              optional={{placeholder: "link reason"}}
          />
          <PlainInputElement
            model={this.state.personToCounterPartyLink}
            propertyName="specificDetails"
            registerInput={(it)=>{this.registerInput(it)}}
            optional={{placeholder: "additional info"}}
          />
          <button onClick={this.submit}>
            submit
          </button>
        </div>
    }

    @autobind
    submit(){
      this.collectInputs()
      
      this.state.personToCounterPartyLink.validate()
      if (!this.state.personToCounterPartyLink.isValid()) {
        this.setState({})
        return
      }

      this.state.personToCounterPartyLink.create().then((personToCounterPartyLink)=>{
        if (!personToCounterPartyLink.isValid()) {
              this.setState({personToCounterPartyLink})
              return
        } 
        ApplicationComponent.instance.flashMessageQueue.addMessage(
          "link successfully created"
        )
      })
      
    }

}
