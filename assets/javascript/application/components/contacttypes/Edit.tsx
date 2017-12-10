import { ApplicationComponent } from '../ApplicationComponent';
import { ContactType } from '../../models/ContactType';
import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react';
import { match } from 'react-router-dom';
import { PlainInputElement } from '../../../reactUtils/plugins/formable/formElements/PlainInput';
import { MixinFormableTrait } from '../../../reactUtils/plugins/formable/MixinFormableTrait';
import autobind from 'autobind-decorator';
import { FlashMessageQueue } from '../shared/FlashMessageQueue';

export class Edit extends MixinFormableTrait(BaseReactComponent) {

    props: {
        match: match<any>
    }

    state: {
        contactType: ContactType
    } = {
        contactType: null
    }

    componentDidMount(){
        let id = this.props.match.params.id
        ContactType.get({wilds: {id}}).then((contactType)=>{
            this.setState({contactType})
        })
    }

    render(){
        return <div className="contactTypes-Edit">
            {this.state.contactType && 
                <div>
                    <PlainInputElement 
                        model={this.state.contactType}
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
        if (!this.state.contactType.isValid()) {
            this.setState({})
            return
        }

        this.state.contactType.update().then((contactType)=>{
            if (contactType.isValid()){
                ApplicationComponent.instance.flashMessageQueue.addMessage("contact type successfully updated")
            }
            this.setState({contactType})
        })
    }

}
