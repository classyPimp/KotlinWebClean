import { ApplicationComponent } from '../ApplicationComponent';
import { IncorporationForm } from '../../models/IncorporationForm';
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
        incorporationForm: IncorporationForm
    } = {
        incorporationForm: null
    }

    componentDidMount(){
        let id = this.props.match.params.id
        IncorporationForm.edit({wilds: {id}}).then((incorporationForm)=>{
            this.setState({incorporationForm})
        })
    }

    render(){
        return <div className="incorporationForms-Edit">
            {this.state.incorporationForm && 
                <div>
                    <PlainInputElement 
                        model={this.state.incorporationForm}
                        propertyName="name"
                        registerInput={(it)=>{this.registerInput(it)}}
                        optional={{
                          placeholder: "name"
                        }}
                    />
                    <PlainInputElement 
                        model={this.state.incorporationForm}
                        propertyName="nameShort"
                        registerInput={(it)=>{this.registerInput(it)}}
                        optional={{
                          placeholder: "short name"
                        }}
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
        if (!this.state.incorporationForm.isValid()) {
            this.setState({})
            return
        }

        this.state.incorporationForm.update().then((incorporationForm)=>{
            if (incorporationForm.isValid()){
                ApplicationComponent.instance.flashMessageQueue.addMessage("contact type successfully updated")
            }
            this.setState({incorporationForm})
        })
    }

}
