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
                </div>
            }
        </div>
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

