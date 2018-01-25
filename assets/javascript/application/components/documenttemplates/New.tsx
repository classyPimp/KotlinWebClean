import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import {DocumentTemplate} from '../../models/DocumentTemplate'
import { MixinFormableTrait } from '../../../reactUtils/plugins/formable/MixinFormableTrait';
import { PlainInputElement } from '../../../reactUtils/plugins/formable/formElements/PlainInput'
import autobind from 'autobind-decorator'
import { ErrorsShow } from '../shared/ErrorsShow'
import { ApplicationComponent } from '../ApplicationComponent';
import { PlainSelect } from '../formelements/PlainSelect'
import { PlainFileInput } from '../formelements/PlainFileInput'
import { UploadedDocument } from '../../models/UploadedDocument'

export class New extends MixinFormableTrait(BaseReactComponent) {

    state: {
      documentTemplate: DocumentTemplate
      validationReady: boolean
    } = {
      documentTemplate: new DocumentTemplate({uploadedDocument: new UploadedDocument()}),
      validationReady: false
    }


    render(){
        return <div >
            <h2>
                Create new document template
            </h2>
            <div>
              <PlainFileInput 
                model={this.state.documentTemplate.uploadedDocument}
                propertyName="file"
                registerInput={(it)=>{this.registerInput(it, "file")}}
                optional={{
                  placeholder: "select file",
                  onFileDeselected: this.onFileDeselectedInInput
                }}
              />
              <button onClick={this.validateTemplate}>
                validate template
              </button>
            </div>
            {this.state.documentTemplate.getErrorsFor('general') &&
              <ErrorsShow errors={this.state.documentTemplate.getErrorsFor('general')}/>
            }
            {
              this.state.documentTemplate.documentTemplateToDocumentVariableLinks.map((link, index)=>{
                return <div key={index}>
                  <ErrorsShow errors={link.getErrorsFor('general')}/>
                  <ErrorsShow errors={link.documentTemplateVariable.getErrorsFor('general')}/>
                  <p>
                    {link.documentTemplateVariable.name}
                  </p>                                   
                </div>
              })
            }
            {this.state.validationReady &&
              <button onClick={this.submit}>
                  submit
              </button>
            }
        </div>
    }

    resetDocumentTemplate() {
      let documentTemplate = new DocumentTemplate({uploadedDocument: new UploadedDocument()})
      this.setState({documentTemplate})
    }

    @autobind
    onFileDeselectedInInput(){
      this.resetDocumentTemplate()
    }

    @autobind
    validateTemplate() {
        this.collectInputs({namespace: 'file'})
        this.state.documentTemplate.validate()
        
        let uploadedDocument = this.state.documentTemplate.uploadedDocument

        if (!this.state.documentTemplate.uploadedDocument.file) {
          alert("file is not selected")
          return
        }
        this.state.documentTemplate.prevalidationsCreate().then((documentTemplate)=>{
            documentTemplate.uploadedDocument = uploadedDocument
            this.setState({documentTemplate, validationReady: true})
        })
    }

    @autobind
    submit() {

    }


}
