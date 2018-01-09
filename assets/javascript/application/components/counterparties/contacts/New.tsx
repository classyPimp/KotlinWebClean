import { BaseReactComponent } from "../../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import autobind from 'autobind-decorator';
import { Contact } from '../../../models/Contact';
import { PlainInputElement } from '../../../../reactUtils/plugins/formable/formElements/PlainInput';
import { MixinFormableTrait } from '../../../../reactUtils/plugins/formable/MixinFormableTrait';
import { FlashMessageQueue } from '../../shared/FlashMessageQueue';
import { Modal } from '../../shared/Modal';
import { CounterPartiesComponents } from '../CounterPartiesComponents'
import { DropDownSelectServerFed } from '../../formelements/DropdownSelectServerFed'
import { ModelCollection } from '../../../../modelLayer/ModelCollection'
import { ContactType } from '../../../models/ContactType'

export class New extends MixinFormableTrait(BaseReactComponent) {

    props: {
      onCreateSuccess: (contact: Contact)=>any
      onCancel: ()=>any
      counterPartyId: number
    }

    state: {
      contact: Contact
    } = {
      contact: new Contact()
    }



    render(){
        return <div className="contacts-New">
          <h3>create contact</h3>
          <PlainInputElement
            model={this.state.contact}
            propertyName="value"
            registerInput={(it)=>{this.registerInput(it)}}
            optional={{placeholder: "value"}}
          />
          <DropDownSelectServerFed 
            model={this.state.contact}
            propertyName = "contactTypeId"
            queryingFunction = { ContactType.indexInputFeedForCounterParty.bind(ContactType) }  
            propertyToSelect = "id" 
            propertyToShow = "name"
            registerInput = {(it)=>{this.registerInput(it)}}
            optional = {{placeholder: "contact type"}}
          />
          <button onClick={this.submit}>submit</button>          
        </div>
    }

    @autobind
    submit(){
      this.collectInputs()
      this.state.contact.createForCounterParty({wilds: {counterPartyId: `${this.props.counterPartyId}`}}).then((contact)=>{
        if (contact.isValid()) {
          this.props.onCreateSuccess(contact)
        } else {
          this.setState({contact})
        }
      })
    }

}
