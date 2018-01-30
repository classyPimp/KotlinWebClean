import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { DocumentTemplateCategory } from '../../models/DocumentTemplateCategory'
import { MixinFormableTrait } from '../../../reactUtils/plugins/formable/MixinFormableTrait';
import { PlainInputElement } from '../../../reactUtils/plugins/formable/formElements/PlainInput'
import autobind from 'autobind-decorator'
import { ErrorsShow } from '../shared/ErrorsShow'
import { ApplicationComponent } from '../ApplicationComponent';

export class New extends MixinFormableTrait(BaseReactComponent) {

    state: {
      documentTemplateCategory: DocumentTemplateCategory
    } = {
      documentTemplateCategory: new DocumentTemplateCategory()
    }

    render(){
        return <div className="persontocounterpartylinkreasons-New">
          <h2>
            Create new document template category
          </h2>
          {this.state.documentTemplateCategory.getErrorsFor('general') &&
              <ErrorsShow errors={this.state.documentTemplateCategory.getErrorsFor('general')}/>
          }
          <PlainInputElement
            model={this.state.documentTemplateCategory}
            propertyName="name"
            registerInput={(it)=>{this.registerInput(it)}}
            optional={{placeholder: "name"}}
          />
          <PlainInputElement
            model={this.state.documentTemplateCategory}
            propertyName="description"
            registerInput={(it)=>{this.registerInput(it)}}
            optional={{placeholder: "description"}}
          />
          <button onClick={this.submit}>
            submit
          </button>
        </div>
    }

    @autobind
    submit(){
      this.collectInputs()
      
      let currentDocumentTemplateCategory = this.state.documentTemplateCategory
      currentDocumentTemplateCategory.validate()
      if (!currentDocumentTemplateCategory.isValid()) {
        this.setState({documentTemplateCategory: currentDocumentTemplateCategory})
        return
      }

      currentDocumentTemplateCategory.create().then((documentTemplateCategory)=>{
        if (!documentTemplateCategory.isValid()) {
              this.setState({documentTemplateCategory})
              return
        } 
        ApplicationComponent.instance.flashMessageQueue.addMessage(
          "document template category successfully created"
        )
        this.setState({documentTemplateCategory})
      })
      
    }

}
