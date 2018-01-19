import { ApplicationComponent } from '../ApplicationComponent';
import { DocumentTemplateVariable } from '../../models/DocumentTemplateVariable';
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
        documentTemplateVariable: DocumentTemplateVariable
    } = {
        documentTemplateVariable: null
    }

    componentDidMount(){
        let id = this.props.match.params.id
        DocumentTemplateVariable.edit({wilds: {id}}).then((documentTemplateVariable)=>{
            this.setState({documentTemplateVariable})
        })
    }

    render(){
        return <div className="documentTemplateVariables-Edit">
            {this.state.documentTemplateVariable && 
                <div>
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
                        update
                    </button>
                </div>
            }
        </div>
    }


    @autobind
    submit(){
        this.collectInputs()

        this.state.documentTemplateVariable.validate()
        if (!this.state.documentTemplateVariable.isValid()) {
            this.setState({})
            return
        }

        this.state.documentTemplateVariable.update().then((documentTemplateVariable)=>{
            if (documentTemplateVariable.isValid()){
                ApplicationComponent.instance.flashMessageQueue.addMessage("document template variable successfully updated")
            }
            this.setState({documentTemplateVariable})
        })
    }

}
