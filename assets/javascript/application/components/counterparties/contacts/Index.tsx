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
import {CounterPartyToContactLink} from '../../../models/CounterPartyToContactLink'
import { ModelCollection } from '../../../../modelLayer/ModelCollection'
import { ContactType } from '../../../models/ContactType'
import { DropDownSelectServerFed } from '../../formelements/DropdownSelectServerFed'

export class Index extends BaseReactComponent {

    props: {
      counterPartyId: number
      editableMode: boolean
    }

    state: {
      contacts: ModelCollection<Contact>
    } = {
      contacts: new ModelCollection<Contact>()
    }

    modal: Modal

    componentDidMount() {
      Contact.indexForCounterParty({wilds: {counterPartyId: `${this.props.counterPartyId}`}}).then((contacts)=>{
        this.setState({contacts})
      })
    }

    render(){
        return <div>
          {this.props.editableMode &&
            <Modal ref={(it)=>{this.modal = it}}/>
          }
          {
            this.state.contacts.map((contact, index)=>{
              return <div key={index}>
                  <p>
                    {contact.contactType.name}: {contact.value}
                  </p>
                  {this.props.editableMode &&
                    <div>
                      <button onClick={()=>{this.initEdit(contact)}}>
                        edit
                      </button>
                      <button onClick={()=>{this.delete(contact)}}>
                        delete
                      </button>
                    </div>
                  }
              </div>
            }) 
          }
          {
            this.props.editableMode &&
            <div>
              <button onClick={this.initContactAddition}>
                + contact
              </button>
            </div>
          }
        </div>
    }

    @autobind
    initEdit(contact: Contact){
      this.modal.open(
        <CounterPartiesComponents.contacts.Edit
          counterPartyId={this.props.counterPartyId}
          contactId={contact.id}
          onUpdateSuccess={this.onUpdateSuccess}
          onCancel={this.onUpdateCanel}
          contactOnParent={contact}
        />
      )
    }

    @autobind
    onUpdateSuccess(contact: Contact, contactOnParent: Contact){
      this.modal.close()
      try {
        contactOnParent.mergeWith(contact)
      } catch(e) {
        console.log(e)
      }
      console.log(this.state.contacts)
      this.setState({})
    }

    @autobind
    onUpdateCanel(){
      this.modal.close()
    }

    @autobind
    initContactAddition(){
      this.modal.open(
        <CounterPartiesComponents.contacts.New
          counterPartyId={this.props.counterPartyId}
          onCancel={this.cancelContactNew}
          onCreateSuccess={this.onContactNewSuccess}
        />
      )
    }

    @autobind
    cancelContactNew(){
      this.modal.close()
    }

    @autobind
    onContactNewSuccess(contact: Contact){
      console.log("new success")
      try {
      this.state.contacts.push(contact)
      this.modal.close()
      this.setState({})
      } catch(er) {
        console.log(er)
      }
    }


    @autobind
    delete(contact: Contact) {
      if (contact.isValid()){
        contact.deleteForCounterParty({wilds: {counterPartyId: this.props.counterPartyId.toString()}}).then(()=>{
          this.state.contacts.filter((it)=>{
            return (it !== contact)
          })
          this.setState({})
          return  
        })
      } else {
        alert("could not delete: `contact.errors.general[0]`")
      }
    }

    @autobind
    refresh(){
      this.componentDidMount()
    }

}