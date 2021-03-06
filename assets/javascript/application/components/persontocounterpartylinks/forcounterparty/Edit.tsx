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

export class Edit extends MixinFormableTrait(BaseReactComponent) {

    props: {
      match: match<any>
    } 

    state: {
      personToCounterPartyLink: PersonToCounterPartyLink
    } = {
      personToCounterPartyLink: null
    }

    componentDidMount() {
      let counterPartyId = this.props.match.params.counterPartyId
      let id = this.props.match.params.personToCounterPartyLinkId
      PersonToCounterPartyLink.forCounterPartyEdit({
        wilds: {
          counterPartyId,
          id,
        }
      }).then((personToCounterPartyLink)=>{
        this.setState({personToCounterPartyLink})
      })
    }

    render(){
        if (!this.state.personToCounterPartyLink) {
          return <div/>
        }

        return <div className="persontocounterpartylinks-New">
          {this.state.personToCounterPartyLink.getErrorsFor('general') &&
              <ErrorsShow errors={this.state.personToCounterPartyLink.getErrorsFor('general')}/>
          }
         <DropDownSelectServerFed 
              model={this.state.personToCounterPartyLink}
              propertyName="personId"
              queryingFunction = { Person.formFeedsIndex.bind(Person) }
              propertyToSelect="id" 
              propertyToShow="name"
              registerInput={(it)=>{this.registerInput(it)}}
              preselected = {this.state.personToCounterPartyLink.personId}
              optional={{placeholder: "person to link"}}
          />
          <DropDownSelectServerFed 
              model={this.state.personToCounterPartyLink}
              propertyName="personToCounterPartyLinkReasonId"
              queryingFunction = { PersonToCounterPartyLinkReason.formFeedsIndex.bind(PersonToCounterPartyLinkReason) }
              propertyToSelect="id" 
              propertyToShow="reasonName"
              registerInput={(it)=>{this.registerInput(it)}}
              preselected = {this.state.personToCounterPartyLink.personToCounterPartyLinkReasonId}
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

      this.state.personToCounterPartyLink.update().then((personToCounterPartyLink)=>{
        if (!personToCounterPartyLink.isValid()) {
              this.setState({personToCounterPartyLink})
              return
        } 
        ApplicationComponent.instance.flashMessageQueue.addMessage(
          "link successfully updated"
        )
      })
      
    }

}
