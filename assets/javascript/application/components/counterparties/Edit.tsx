import { ApplicationComponent } from '../ApplicationComponent';
import { CounterParty } from '../../models/CounterParty';
import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react';
import { match } from 'react-router-dom';
import { PlainInputElement } from '../../../reactUtils/plugins/formable/formElements/PlainInput';
import { MixinFormableTrait } from '../../../reactUtils/plugins/formable/MixinFormableTrait';
import autobind from 'autobind-decorator';
import { FlashMessageQueue } from '../shared/FlashMessageQueue';
import { DropDownSelectServerFed } from '../formelements/DropdownSelectServerFed'
import { IncorporationForm } from '../../models/IncorporationForm'
import { Modal } from '../shared/Modal';
import { PersonToCounterPartyLinksComponents } from '../persontocounterpartylinks/PersonToCounterPartyLinksComponents'
import { PersonToCounterPartyLink } from '../../models/PersonToCounterPartyLink'
import { Index as PersonToCounterPartyLinkIndex } from '../persontocounterpartylinks/Index'

export class Edit extends MixinFormableTrait(BaseReactComponent) {

    props: {
        match: match<any>
    }

    state: {
        counterParty: CounterParty
        linkedPersonsExpanded: boolean
    } = {
        counterParty: null,
        linkedPersonsExpanded: false
    }

    modal: Modal
    personToCounterPartyLinkIndexComponent: PersonToCounterPartyLinkIndex

    componentDidMount(){
        let id = this.props.match.params.id
        CounterParty.edit({wilds: {id}}).then((counterParty)=>{
            this.setState({counterParty})
        })
    }


    render(){
        return <div className="counterParties-Edit">
            <Modal ref={(it)=>{this.modal = it}}/>
            {this.state.counterParty && 
                <div>
                    <PlainInputElement 
                      model={this.state.counterParty}
                      propertyName="name"
                      registerInput={(it)=>{this.registerInput(it)}}
                      optional={{placeholder: "name"}}
                    />
                    <PlainInputElement 
                      model={this.state.counterParty}
                      propertyName="nameShort"
                      registerInput={(it)=>{this.registerInput(it)}}
                      optional={{placeholder: "short name"}}
                    />
                    <DropDownSelectServerFed 
                        model={this.state.counterParty}
                        propertyName="incorporationFormId"
                        queryingFunction = { IncorporationForm.formFeedsIndex.bind(IncorporationForm) }
                        propertyToSelect="id" 
                        propertyToShow="nameShort"
                        preselected = {this.state.counterParty.incorporationFormId}
                        registerInput={(it)=>{this.registerInput(it)}}
                        optional={{placeholder: "incorporation form"}}
                    />
                    <button onClick={this.submit}>
                        update
                    </button>
                    <div>
                      <p>
                        <button onClick={this.initPersonToCounterPartyLinkAddition}>
                          add link to person
                        </button>
                      </p>
                      {!this.state.linkedPersonsExpanded
                        ? <div>
                           <button onClick={this.toggleExpandedLinkedPersons}> 
                             show linked persons
                           </button>
                        </div>
                        : <div>
                          <PersonToCounterPartyLinksComponents.Index 
                            counterParty={this.state.counterParty} 
                            ref={(it)=>{this.personToCounterPartyLinkIndexComponent = it}}
                          />
                          <button onClick={this.toggleExpandedLinkedPersons}>
                            fold
                          </button>
                        </div>
                      }
                    </div>
                </div>

            }
        </div>
    }


    @autobind
    submit(){
        this.collectInputs()

        this.state.counterParty.validate()
        if (!this.state.counterParty.isValid()) {
            this.setState({})
            return
        }

        this.state.counterParty.update().then((counterParty)=>{
            if (counterParty.isValid()){
                ApplicationComponent.instance.flashMessageQueue.addMessage(
                  "counter party successfully updated"
                )
            }
            this.setState({counterParty})
        })
    }

    @autobind
    toggleExpandedLinkedPersons(){
      this.setState({linkedPersonsExpanded: !this.state.linkedPersonsExpanded})
    }

    @autobind
    initPersonToCounterPartyLinkAddition(){
      this.modal.open(
        <PersonToCounterPartyLinksComponents.New 
          counterParty={this.state.counterParty}
          onCreateSuccess={this.onPersonToCounterPartyLinkCreateSuccess}
          onCancel={this.onPersonToCounterPartyLinkCreateCancel}
        />
      )
    }

    @autobind
    onPersonToCounterPartyLinkCreateSuccess(personToCounterPartyLink: PersonToCounterPartyLink){
      this.state.counterParty.personToCounterPartyLinks.push(personToCounterPartyLink)
      this.modal.close()
      this.setState({})
      this.personToCounterPartyLinkIndexComponent.refresh()
    }

    @autobind
    onPersonToCounterPartyLinkCreateCancel(){
      this.modal.close()
      console.log(this.personToCounterPartyLinkIndexComponent)
    }

}
