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
      contact: Contact,
      onUpdateSuccess: (contact: Contact)=>any,
      onCancel: ()=>any
    }


    render(){
        return <div className="contacts-Edit">
          <PlainInputElement
            model={this.props.contact}
            propertyName="value"
            registerInput={(it)=>{this.registerInput(it)}}
            optional={{placeholder: "value"}}
          />
          <DropDownSelectServerFed 
            model={this.props.contact}
            propertyName="contactTypeId"
            queryingFunction={ContactType.indexInputFeedForCounterParty.bind(ContactType)}  
            propertyToSelect="id" 
            propertyToShow="name"
            registerInput={(it)=>{this.registerInput(it)}}
            preselected={this.props.contact.contactTypeId}
            optional={{placeholder: "contact type"}}
          />
          <button onClick={this.submit}>update contact</button>
          <button onClick={this.cancel}>cancel</button>
        </div>
    }

    @autobind
    submit(){
      this.collectInputs()
      this.props.contact.validate()
      this.props.contact.updateForCounterParty({wilds: {counterPartyId: `${this.props.counterPartyId}`}}).then((contact)=>{
        if (contact.isValid()) {
          this.props.contact.mergeWith(contact)
          this.props.onUpdateSuccess(contact)
        } else {
          this.props.contact.mergeWith(contact)
          this.forceUpdate()
        }
      })
    }

    @autobind
    cancel(){
      this.props.contact.resetErrors()
      this.props.onCancel()
    }

}
