import { ApplicationComponent } from '../ApplicationComponent';
import { Person } from '../../models/Person';
import { Contact } from '../../models/Contact';
import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react';
import { match } from 'react-router-dom';
import { PlainInputElement } from '../../../reactUtils/plugins/formable/formElements/PlainInput';
import { MixinFormableTrait } from '../../../reactUtils/plugins/formable/MixinFormableTrait';
import autobind from 'autobind-decorator';
import { FlashMessageQueue } from '../shared/FlashMessageQueue';
import { Modal } from '../shared/Modal';
import { PersonsComponents } from './PersonsComponents'
import { PersonToContactLink } from '../../models/PersonToContactLink'

export class Edit extends MixinFormableTrait(BaseReactComponent) {

    props: {
        match: match<any>
    }

    state: {
        person: Person
    } = {
        person: null
    }

    modal: Modal

    componentDidMount(){
        let id = this.props.match.params.id
        Person.get({wilds: {id}}).then((person)=>{
            this.setState({person})
        })
    }

    render(){
        return <div className="persons-Edit">
            <Modal ref={(it)=>{this.modal = it}}/>
            {this.state.person && 
                <div>
                    <PlainInputElement 
                        model={this.state.person}
                        propertyName="name"
                        registerInput={(it)=>{this.registerInput(it)}}
                    />
                    <button onClick={this.submit}>
                        update
                    </button>
                    <div className="contacts">
                      {
                        this.state.person.personToContactLinks.map((it, index)=>{
                          return <div className="contact" key={index}>
                            {it.contact.contactType.name}: {it.contact.value}
                            <button onClick={()=>{this.deleteContact(it)}}>delete</button>
                            <button onClick={()=>{this.initContactUpdate(it)}}>update</button>
                          </div>
                        })
                      }
                    </div>
                    <button onClick={this.initContactAddition}>
                      + contact
                    </button>
                </div>
            }
        </div>
    }

    @autobind
    initContactAddition(){
      this.modal.open(
        <PersonsComponents.contacts.New 
          onCreateSuccess={(contact: Contact)=>{this.assignContact(contact)}}
          onCancel={this.modal.close}
          personId={this.state.person.id}
        />
      )
    }

    @autobind
    assignContact(contact: Contact) {
      let link = contact.personToContactLink
      delete contact.properties["personToContactLink"]
      let person = this.state.person
      link.contact = contact
      person.personToContactLinks.push(link)
      this.modal.close()
      this.setState({person})
    }

    @autobind
    initContactUpdate(link: PersonToContactLink){
      let contact = link.contact
      this.modal.open(
        <PersonsComponents.contacts.Edit
          onUpdateSuccess={(contact: Contact)=>this.finalizeContactUpdate(contact)}
          onCancel={()=>{this.modal.close()}}
          personId={this.state.person.id}
          contact={contact}
        />
      )
    }

    @autobind
    finalizeContactUpdate(contact: Contact){
      this.modal.close()
      this.setState({person: this.state.person})
    }

    @autobind
    deleteContact(link: PersonToContactLink) {
      link.contact.deleteForPerson({wilds: {personId: `${this.state.person.id}`}}).then((contact)=>{
        let person = this.state.person
        person.personToContactLinks.filter((it)=>{
          return !(it === link)
        })
        this.setState({person})
      })
    }


    @autobind
    submit(){
        this.collectInputs()
        
        if (!this.state.person.isValid()) {
            this.setState({})
            return
        }

        this.state.person.update().then((person)=>{
            if (person.isValid()) {
                ApplicationComponent.instance.flashMessageQueue.addMessage(
                  "person successfully updated"
                )
                this.state.person.mergeWith(person)
                this.setState({person: this.state.person})
            } else {
              this.setState({person})
            }
        })
    }

}

