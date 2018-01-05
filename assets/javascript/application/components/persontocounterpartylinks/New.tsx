import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { PersonToCounterPartyLink } from '../../models/PersonToCounterPartyLink'
import { CounterParty } from '../../models/CounterParty'
import { Person } from '../../models/Person'
import { PersonToCounterPartyLinkReason } from '../../models/PersonToCounterPartyLinkReason'
import { MixinFormableTrait } from '../../../reactUtils/plugins/formable/MixinFormableTrait';
import { PlainInputElement } from '../../../reactUtils/plugins/formable/formElements/PlainInput'
import autobind from 'autobind-decorator'
import { ErrorsShow } from '../shared/ErrorsShow'
import { ApplicationComponent } from '../ApplicationComponent';
import { DropDownSelectServerFed } from '../formelements/DropdownSelectServerFed'


export class New extends MixinFormableTrait(BaseReactComponent) {

    props: {
      counterParty?: CounterParty
      person?: Person
      onCreateSuccess: (personToCounterPartyLink: PersonToCounterPartyLink)=>any,
      onCancel: ()=>any
    } 

    state: {
      personToCounterPartyLink: PersonToCounterPartyLink
    } = {
      personToCounterPartyLink: new PersonToCounterPartyLink()
    }

    constructor(...args: Array<any>) {
      super(...args)
      if (this.props.counterParty) {
        let counterPartyId = this.props.counterParty.id
        this.state.personToCounterPartyLink = new PersonToCounterPartyLink({counterPartyId})
      } else if (this.props.person) {
        let personId = this.props.person.id
        this.state.personToCounterPartyLink = new PersonToCounterPartyLink({personId})
      }
    }

    render(){
        return <div className="persontocounterpartylinks-New">
          {this.props.counterParty 
            ? <h2>
              Create new person to {this.props.counterParty.name} link
            </h2>

            : <h2>
              Create new {this.props.person.name} to counter party link
            </h2>
          }
          {this.state.personToCounterPartyLink.getErrorsFor('general') &&
              <ErrorsShow errors={this.state.personToCounterPartyLink.getErrorsFor('general')}/>
          }
          { this.props.counterParty 
            ? <DropDownSelectServerFed 
                model={this.state.personToCounterPartyLink}
                propertyName="personId"
                queryingFunction = { Person.formFeedsIndex.bind(Person) }
                propertyToSelect="id" 
                propertyToShow="name"
                registerInput={(it)=>{this.registerInput(it)}}
                optional={{placeholder: "person to link"}}
            />
            : <DropDownSelectServerFed 
                model={this.state.personToCounterPartyLink}
                propertyName="counterParyId"
                queryingFunction = { CounterParty.formFeedsIndex.bind(CounterParty) }
                propertyToSelect="id" 
                propertyToShow="name"
                registerInput={(it)=>{this.registerInput(it)}}
                optional={{placeholder: "person to link"}}
            />
          }
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
        this.setState({personToCounterPartyLink})
        this.props.onCreateSuccess(personToCounterPartyLink)
      })
      
    }

}
