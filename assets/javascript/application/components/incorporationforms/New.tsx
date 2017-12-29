import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import {IncorporationForm} from '../../models/IncorporationForm'
import { MixinFormableTrait } from '../../../reactUtils/plugins/formable/MixinFormableTrait';
import { PlainInputElement } from '../../../reactUtils/plugins/formable/formElements/PlainInput'
import autobind from 'autobind-decorator'
import { ErrorsShow } from '../shared/ErrorsShow'
import { ApplicationComponent } from '../ApplicationComponent';

export class New extends MixinFormableTrait(BaseReactComponent) {

    state: {
      incorporationForm: IncorporationForm
    } = {
      incorporationForm: new IncorporationForm()
    }


    render(){
        return <div >
            <h2>
                Create new contact type
            </h2>
            {this.state.incorporationForm.getErrorsFor('general') &&
              <ErrorsShow errors={this.state.incorporationForm.getErrorsFor('general')}/>
            }
            <PlainInputElement 
                model={this.state.incorporationForm} 
                propertyName="name" 
                registerInput={(it)=>{this.registerInput(it)}}
                optional={{placeholder: "name"}}
            />
            <PlainInputElement 
                model={this.state.incorporationForm} 
                propertyName="nameShort" 
                registerInput={(it)=>{this.registerInput(it)}}
                optional={{placeholder: "nameShort"}}
            />
            <button onClick={this.submit}>
                submit
            </button>
        </div>
    }

    @autobind
    submit(){
        this.collectInputs()
        this.state.incorporationForm.create().then((incorporationForm)=>{
            if (!incorporationForm.isValid()) {
                this.setState({incorporationForm})
            } else {
                ApplicationComponent.instance.flashMessageQueue.addMessage(
                    <p>contact type successfully created</p>
                )
            }
        })
    }


}
