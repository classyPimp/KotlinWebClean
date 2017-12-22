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
        let x = Person.get({wilds: {id}})
        x.then((person)=>{
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
      console.log("contact created ok")
      let link = contact.personToContactLink
      delete contact.properties["personToContactLink"]
      let person = this.state.person
      person.personToContactLinks.push(link)
      this.modal.close
      this.setState({person})
    }


    @autobind
    submit(){
        this.collectInputs()
        if (!this.state.person.isValid()) {
            this.setState({})
            return
        }

        this.state.person.update().then((person)=>{
            if (person.isValid()){
                ApplicationComponent.instance.flashMessageQueue.addMessage(
                  "person successfully updated"
                )
            }
            this.setState({person})
        })
    }

}

