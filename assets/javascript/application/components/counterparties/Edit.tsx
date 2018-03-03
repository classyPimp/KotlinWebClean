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
import { CounterPartiesComponents } from './CounterPartiesComponents'
import { CounterPartyToContactLink } from '../../models/CounterPartyToContactLink'
import { Index as PersonToCounterPartyLinkIndex } from '../persontocounterpartylinks/Index'
import { Contact } from '../../models/Contact'
import { PersonToCounterPartyLinksComponents } from '../persontocounterpartylinks/PersonToCounterPartyLinksComponents'
import { PersonToCounterPartyLink } from '../../models/PersonToCounterPartyLink'

export class Edit extends MixinFormableTrait(BaseReactComponent) {

    props: {
        match: match<any>
    }

    state: {
        counterParty: CounterParty
        linkedPersonsExpanded: boolean
        contactsExpanded: boolean
    } = {
        counterParty: null,
        linkedPersonsExpanded: false,
        contactsExpanded: false,
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
                    {/*<div>
                      {this.state.contactsExpanded
                        ? <div>
                          <CounterPartiesComponents.contacts.Index
                            counterPartyId={this.state.counterParty.id}
                            editableMode={true}
                          />
                          <button onClick={this.toggleContactsExpanded}>
                            fold contacts
                          </button>
                        </div>
                        : <div>
                          <button onClick={this.toggleContactsExpanded}>
                            browse contacts
                          </button>
                        </div>
                      }
                    </div>*/}
                    {/*<div>
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
                            editableMode={true}
                          />
                          <p>
                            <button onClick={this.initPersonToCounterPartyLinkAddition}>
                              add link to person
                            </button>
                          </p>
                          <button onClick={this.toggleExpandedLinkedPersons}>
                            fold
                          </button>
                        </div>
                      }
                    </div>*/}
                </div>

            }
        </div>
    }

    @autobind
    initContactAddition(){
      this.modal.open(
        <CounterPartiesComponents.contacts.New 
          onCreateSuccess={(contact: Contact)=>{this.onContactCreateSuccess(contact)}}
          onCancel={this.modal.close}
          counterPartyId={this.state.counterParty.id}
        />
      )
    }

    @autobind
    onContactCreateSuccess(contact: Contact) {
      //TODO: backend wrong return values should include link!
      try {
        console.log("create success")
        let link = contact.counterPartyToContactLink
        delete contact.properties["counterPartyToContactLink"]
        let counterParty = this.state.counterParty
        link.contact = contact
        counterParty.counterPartyToContactLinks.push(link)
        console.log("should close modal")
        this.modal.close()
        this.setState({counterParty})
      } catch (error) {
        console.log(error)
      }
    }

    @autobind
    deleteContact(link: CounterPartyToContactLink) {
      link.contact.deleteForCounterParty({wilds: {counterPartyId: `${this.state.counterParty.id}`}}).then((contact)=>{
        let counterParty = this.state.counterParty
        counterParty.counterPartyToContactLinks.filter((it)=>{
          return !(it === link)
        })
        this.setState({counterParty})
      })
    }

    @autobind
    initContactUpdate(link: CounterPartyToContactLink){
      // let contact = link.contact
      // this.modal.open(
      //   <CounterPartiesComponents.contacts.Edit
      //     onUpdateSuccess={(contact: Contact)=>this.finalizeContactUpdate(contact)}
      //     onCancel={()=>{this.modal.close()}}
      //     counterPartyId={this.state.counterParty.id}
      //     contact={contact}
      //   />
      // )
    }

    @autobind
    finalizeContactUpdate(contact: Contact){
      this.modal.close()
      this.setState({counterParty: this.state.counterParty})
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

    @autobind
    toggleContactsExpanded(){
      this.setState({contactsExpanded: !this.state.contactsExpanded})
    }

}
