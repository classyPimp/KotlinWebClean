import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import {ContactType} from '../../models/ContactType'
import { MixinFormableTrait } from '../../../reactUtils/plugins/formable/MixinFormableTrait';
import { PlainInputElement } from '../../../reactUtils/plugins/formable/formElements/PlainInput'
import autobind from 'autobind-decorator'
import { ErrorsShow } from '../shared/ErrorsShow'
import { ApplicationComponent } from '../ApplicationComponent';

export class New extends MixinFormableTrait(BaseReactComponent) {

    state: {
      contactType: ContactType
    } = {
      contactType: new ContactType()
    }


    render(){
        return <div >
            <h2>
                Create new contact type
            </h2>
            {this.state.contactType.getErrorsFor('general') &&
              <ErrorsShow errors={this.state.contactType.getErrorsFor('general')}/>
            }
            <PlainInputElement model={this.state.contactType} propertyName="name" registerInput={(it)=>{this.registerInput(it)}}
                optional={{placeholder: "name"}}
            />
            <button onClick={this.submit}>
                submit
            </button>
        </div>
    }

    @autobind
    submit(){
        this.collectInputs()
        this.state.contactType.create().then((contactType)=>{
            if (!contactType.isValid()) {
                this.setState({contactType})
            } else {
                ApplicationComponent.instance.flashMessageQueue.addMessage(
                    <p>contact type successfully created</p>
                )
            }
        })
    }


}
