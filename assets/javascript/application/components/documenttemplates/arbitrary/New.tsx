import { BaseReactComponent } from "../../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import {DocumentTemplate} from '../../../models/DocumentTemplate'
import { MixinFormableTrait } from '../../../../reactUtils/plugins/formable/MixinFormableTrait';
import { PlainInputElement } from '../../../../reactUtils/plugins/formable/formElements/PlainInput'
import autobind from 'autobind-decorator'
import { ErrorsShow } from '../../shared/ErrorsShow'
import { ApplicationComponent } from '../../ApplicationComponent';
import { PlainSelect } from '../../formelements/PlainSelect'
import { PlainFileInput } from '../../formelements/PlainFileInput'
import { UploadedDocument } from '../../../models/UploadedDocument'
import { DropDownSelectServerFed } from '../../formelements/DropdownSelectServerFed'
import { DocumentTemplateCategory } from '../../../models/DocumentTemplateCategory'
import { match } from 'react-router-dom';

export class New extends MixinFormableTrait(BaseReactComponent) {

    props: {
      match: match<any>
    }

    state: {
      documentTemplate: DocumentTemplate
    } = {
      documentTemplate: null
    }

    componentDidMount(){
      let id = this.props.match.params['id']
      DocumentTemplate.arbitraryShow({wilds: {id: `${id}`}}).then((documentTemplate)=>{
        this.setState({documentTemplate})
      })
    }

    render(){
        return this.state.documentTemplate
          ? <div>
              <h2>
                  Create document from template
              </h2>      
                <h3>
                  {this.state.documentTemplate.name}
                </h3>
                <p>
                  category: {this.state.documentTemplate.documentTemplateCategory.name}
                </p>

                {this.state.documentTemplate.getErrorsFor('general') &&
                  <ErrorsShow errors={this.state.documentTemplate.getErrorsFor('general')}/>
                }
              
              {
                this.state.documentTemplate.documentTemplateToDocumentVariableLinks.map((link, index)=>{
                  return <div key={link.id}>
                    <ErrorsShow errors={link.getErrorsFor('general')}/>
                    <ErrorsShow errors={link.documentTemplateVariable.getErrorsFor('general')}/>
                    <p>
                      {link.uniqueIdentifier 
                        ? `${link.documentTemplateVariable.name}-${link.uniqueIdentifier}`
                        : `${link.documentTemplateVariable.name}`
                      }
                    </p>
                    <PlainInputElement
                      model={link}
                      propertyName="defaultValue"
                      registerInput={(it)=>{this.registerInput(it)}}
                      optional={{
                        placeholder: "value"
                      }}
                    />                                   
                  </div>
                })
              }
              <button onClick={this.submit}>
                  submit
              </button>
          </div>
        : <div>
          ...loading
        </div>
    }

    @autobind
    submit() {
      this.collectInputs()
      this.state.documentTemplate.validate()
      this.state.documentTemplate.arbitraryCreate().then((documentTemplate)=>{
        try {
          if (documentTemplate.properties["BLOB_IS_RETURNED"]) {
            let blobResponse = documentTemplate.properties["BLOB_RESPONSE"]
            let blob = new Blob([blobResponse], {type: "application/vnd.openxmlformats-officedocument.wordprocessingml.document"})
            var downloadUrl = URL.createObjectURL(blob);
            var a = document.createElement("a");
            (a as any).style = "display: none";
            document.body.appendChild(a);
            a.href = downloadUrl;
            a.download = "myfile.docx";
            a.click();
            a.remove()
          } else {
            this.setState({documentTemplate})
          }
        } catch(error) {
          console.log(error)
        }
      })
    }


}
