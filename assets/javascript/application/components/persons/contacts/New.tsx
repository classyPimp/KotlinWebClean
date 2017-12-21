import { BaseReactComponent } from "../../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import autobind from 'autobind-decorator';
import { Contact } from '../../../models/Contact';
import { PlainInputElement } from '../../../../reactUtils/plugins/formable/formElements/PlainInput';
import { MixinFormableTrait } from '../../../../reactUtils/plugins/formable/MixinFormableTrait';
import { FlashMessageQueue } from '../../shared/FlashMessageQueue';
import { Modal } from '../../shared/Modal';
import { PersonsComponents } from '../PersonsComponents'
import { DropDownSelectServerFed } from '../../formelements/DropdownSelectServerFed'
import { ModelCollection } from '../../../../modelLayer/ModelCollection'
import { ContactType } from '../../../models/ContactType'

export class New extends MixinFormableTrait(BaseReactComponent) {

    props: {
      onCreateSuccess: (contact: Contact)=>any
      onCancel: ()=>any
      personId: number
    }

    state: {
      contact: Contact
      contactTypesSelectOptions: ModelCollection<ContactType>
    } = {
      contact: new Contact(),
      contactTypesSelectOptions: new ModelCollection<ContactType>()
    }

    componentDidMount(){
      ContactType.indexInputFeedForPerson().then((contactTypesSelectOptions)=>{
        this.setState({contactTypesSelectOptions})
      })
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
            propertyName="contactTypeId"
            modelsToWrapAsOptions={this.state.contactTypesSelectOptions}  
            propertyToSelect="id" 
            propertyToShow="name"
            registerInput={(it)=>{this.registerInput(it)}}
            optional={{placeholder: "contact type"}}
          />
          <button onClick={this.submit}>submit</button>          
        </div>
    }

    @autobind
    submit(){
      this.collectInputs()
      this.state.contact.createForPerson({wilds: {personId: `${this.props.personId}`}}).then((contact)=>{
        if (contact.isValid()) {
            this.props.onCreateSuccess(contact)
          } else {
            this.setState({contact})
          }
      })
    }

}
