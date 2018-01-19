import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import {DocumentTemplateVariable} from '../../models/DocumentTemplateVariable'
import { MixinFormableTrait } from '../../../reactUtils/plugins/formable/MixinFormableTrait';
import { PlainInputElement } from '../../../reactUtils/plugins/formable/formElements/PlainInput'
import autobind from 'autobind-decorator'
import { ErrorsShow } from '../shared/ErrorsShow'
import { ApplicationComponent } from '../ApplicationComponent';
import { PlainSelect } from '../formelements/PlainSelect'

export class New extends MixinFormableTrait(BaseReactComponent) {

    state: {
      documentTemplateVariable: DocumentTemplateVariable
    } = {
      documentTemplateVariable: new DocumentTemplateVariable()
    }


    render(){
        return <div >
            <h2>
                Create new document template variable
            </h2>
            {this.state.documentTemplateVariable.getErrorsFor('general') &&
              <ErrorsShow errors={this.state.documentTemplateVariable.getErrorsFor('general')}/>
            }
            <PlainInputElement 
                model={this.state.documentTemplateVariable} 
                propertyName="name" 
                registerInput={(it)=>{this.registerInput(it)}}
                optional={{placeholder: "identifier"}}
            />
            <PlainInputElement 
                model={this.state.documentTemplateVariable} 
                propertyName="name" 
                registerInput={(it)=>{this.registerInput(it)}}
                optional={{placeholder: "name"}}
            />
            <PlainInputElement 
                model={this.state.documentTemplateVariable} 
                propertyName="defaultValue" 
                registerInput={(it)=>{this.registerInput(it)}}
                optional={{placeholder: "default value"}}
            />
            <button onClick={this.submit}>
                submit
            </button>
        </div>
    }

    @autobind
    submit(){
        this.collectInputs()
        this.state.documentTemplateVariable.create().then((documentTemplateVariable)=>{
            if (!documentTemplateVariable.isValid()) {
                this.setState({documentTemplateVariable})
            } else {
                ApplicationComponent.instance.flashMessageQueue.addMessage(
                    <p>contact type successfully created</p>
                )
            }
        })
    }


}
