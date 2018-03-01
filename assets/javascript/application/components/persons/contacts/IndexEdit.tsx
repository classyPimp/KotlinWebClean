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
import { PersonsComponents } from '../PersonsComponents'
import { PersonToContactLink } from '../../../models/PersonToContactLink'
import { ModelCollection } from '../../../../modelLayer/ModelCollection'
import { ContactType } from '../../../models/ContactType'
import { DropDownSelectServerFed } from '../../formelements/DropdownSelectServerFed'

export class IndexEdit extends MixinFormableTrait(BaseReactComponent) {

    props: {
      match: match<any>
    }

    state: {
      contacts: ModelCollection<Contact>
    } = {
      contacts: new ModelCollection<Contact>()
    }

    modal: Modal

    componentDidMount() {
      let personId = this.props.match.params.id.toString()
      Contact.forPersonIndexEdit({wilds: {personId}}).then((contacts)=>{
        this.setState({contacts})
      })
    }

    render(){
        return <div className="contacts-Edit">
          <Modal ref={(it)=>{this.modal = it}}/>
          {this.state.contacts.map((contact)=>{
            return <div className="contact" key={contact.id}>
              {contact.contactType.name}: {contact.value}
              <button onClick={()=>{this.delete(contact)}}>delete</button>
              <button onClick={()=>{this.initUpdate(contact)}}>update</button>
            </div>
          })}
          <button onClick={this.initContactAddition}>
            + contact
          </button>
        </div>
    }

    @autobind
    initContactAddition(){
      this.modal.open(
        <PersonsComponents.contacts.New 
          onCreateSuccess={(contact: Contact)=>{this.assignContact(contact)}}
          onCancel={this.modal.close}
          personId={this.props.match.params.id}
        />
      )
    }

    @autobind
    assignContact(contact: Contact) {
      let link = contact.personToContactLink
      delete contact.properties["personToContactLink"]
      link.contact = contact
      this.state.contacts.push(contact)
      this.modal.close()
      this.setState({})
    }

    @autobind
    delete(contact: Contact) {
      contact.deleteForPerson({wilds: {personId: `${this.props.match.params.id}`}}).then((returnedContact)=>{
        this.state.contacts.filter((it)=>{
          return !(it === contact)
        })
        this.setState({})
      })
    }

    @autobind
    initUpdate(contact: Contact) {
      this.modal.open(
        <PersonsComponents.contacts.Edit
          onUpdateSuccess={(contact: Contact)=>this.finalizeContactUpdate(contact)}
          onCancel={()=>{this.modal.close()}}
          personId={this.props.match.params.id}
          contact={contact}
        />
      )
    }

    @autobind
    finalizeContactUpdate(contact: Contact){
      this.modal.close()
      this.setState({})
    }




}
