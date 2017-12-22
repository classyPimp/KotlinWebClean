import { User } from '../models/User';
import { Person } from '../models/Person';
import autobind from 'autobind-decorator';
import { PlainInputElement } from '../../reactUtils/plugins/formable/formElements/PlainInput';

import { BaseReactComponent } from '../../reactUtils/BaseReactComponent';

import * as React from 'react';
import { MixinFormableTrait } from '../../reactUtils/plugins/formable/MixinFormableTrait';
import { Modal } from './shared/Modal';
import { ApplicationComponent } from './ApplicationComponent';

import { DropDownSelectServerFed } from './formelements/DropdownSelectServerFed'
import { ContactType } from '../models/ContactType'
import { ModelCollection } from '../../modelLayer/ModelCollection'

export class DemoComponent extends MixinFormableTrait(BaseReactComponent) {

    state: {
        modal: Modal,
        selectOptions: ModelCollection<ContactType>
        [id: string]: any
    } = {
        modal: null, 
        selectOptions: new ModelCollection<ContactType>(),
        user: new User()
    }

    componentDidMount(){
      // ContactType.index().then((contactTypes)=>{
      //    this.setState({selectOptions: contactTypes})
      // })
      Person.get({wilds: {id: "1"}}).then((person)=>{
        console.log("demo returned:")
        console.log(person)
        console.log(person.properties)
      })
    }


    render(){
        return (
            <div>
                <Modal ref={(it)=>{this.state.modal = it}}/>                
                <button onClick={this.openModal}>
                    open modal
                </button>
                <button onClick={this.addFlash}>
                    add flash message
                </button>
                <DropDownSelectServerFed 
                  model={this.state.user}
                  propertyName="id"
                  modelsToWrapAsOptions={this.state.selectOptions}  
                  propertyToSelect="id" 
                  propertyToShow="name"
                  registerInput={(it)=>{this.registerInput(it)}}
                />
                <button onClick={this.submit}>
                  submit
                </button>
            </div>
        )
    }

    submodal: Modal = null

    messageCounter = -1

    @autobind
    addFlash(){
        this.messageCounter  += 1
        ApplicationComponent.instance.flashMessageQueue.addMessage(
            <p>
                flash message {this.messageCounter}
            </p>
        )
    }

    @autobind
    openModal(){
        this.state.modal.open(
            <div>
            Why do we use it?
            </div>
        )
    }

    @autobind
    submit(){
      console.log("submit")
      console.log(this.state.user)
      this.collectInputs()
      console.log(this.state.user)
    }


}