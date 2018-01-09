import { ApplicationComponent } from '../../ApplicationComponent';
import { Person } from '../../../models/Person';
import { Contact } from '../../../models/Contact';
import { BaseReactComponent } from "../../../../reactUtils/BaseReactComponent"
import * as React from 'react';
import { match } from 'react-router-dom';
import { PlainInputElement } from '../../../../reactUtils/plugins/formable/formElements/PlainInput';
import { MixinFormableTrait } from '../../../../reactUtils/plugins/formable/MixinFormableTrait';
import autobind from 'autobind-decorator';
import { FlashMessageQueue } from '../../shared/FlashMessageQueue';
import { Modal } from '../../shared/Modal';
import { CounterPartiesComponents } from '../CounterPartiesComponents'
import { CounterPartyToContactLink } from '../../../models/CounterPartyToContactLink'
import { ModelCollection } from '../../../../modelLayer/ModelCollection'
import { ContactType } from '../../../models/ContactType'
import { DropDownSelectServerFed } from '../../formelements/DropdownSelectServerFed'

export class Edit extends MixinFormableTrait(BaseReactComponent) {

    props: {
      counterPartyId: number,
      contactId: number,
      onUpdateSuccess: (contact: Contact, contactOnParent: Contact)=>any,
      onCancel: ()=>any,
      contactOnParent?: Contact
    }

    state: {
      contact: Contact
    } = {
      contact: null
    }

    componentDidMount(){
      Contact.editForCounterParty(
        {
          wilds: {
            counterPartyId: this.props.counterPartyId.toString(),
            id: this.props.contactId.toString()
          }
        }
      ).then((contact)=>{
        this.setState({contact})
      })
    }

    render(){
        return this.state.contact 
        ? <div className="contacts-Edit">
          <PlainInputElement
            model={this.state.contact}
            propertyName="value"
            registerInput={(it)=>{this.registerInput(it)}}
            optional={{placeholder: "value"}}
          />
          <DropDownSelectServerFed 
            model={this.state.contact}
            propertyName="contactTypeId"
            queryingFunction={ContactType.indexInputFeedForCounterParty.bind(ContactType)}  
            propertyToSelect="id" 
            propertyToShow="name"
            registerInput={(it)=>{this.registerInput(it)}}
            preselected={this.state.contact.contactTypeId}
            optional={{placeholder: "contact type"}}
          />
          <button onClick={this.submit}>update contact</button>
          <button onClick={this.cancel}>cancel</button>
        </div>
        : <div>

        </div>
    }

    @autobind
    submit(){
      this.collectInputs()
      this.state.contact.validate()
      this.state.contact.updateForCounterParty({wilds: {counterPartyId: `${this.props.counterPartyId}`}}).then((contact)=>{
        if (contact.isValid()) {
          this.state.contact.mergeWith(contact)
          this.props.onUpdateSuccess(contact, this.props.contactOnParent)
        } else {
          this.state.contact.mergeWith(contact)
          this.forceUpdate()
        }
      })
    }

    @autobind
    cancel(){
      this.state.contact.resetErrors()
      this.props.onCancel()
    }

}
