import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { ApplicationComponent } from '../ApplicationComponent';
import { match } from 'react-router-dom';
import { PlainInputElement } from '../../../reactUtils/plugins/formable/formElements/PlainInput';
import { MixinFormableTrait } from '../../../reactUtils/plugins/formable/MixinFormableTrait';
import autobind from 'autobind-decorator';
import { FlashMessageQueue } from '../shared/FlashMessageQueue';
import { Modal } from '../shared/Modal';
import { DocumentTemplateCategoriesComponents } from './DocumentTemplateCategoriesComponents'
import { DocumentTemplateCategory } from '../../models/DocumentTemplateCategory'


export class Edit extends MixinFormableTrait(BaseReactComponent) {

    props: {
      match: match<any>
    }

    state: {
      documentTemplateCategory: DocumentTemplateCategory
    } = {
      documentTemplateCategory: null
    }

    modal: Modal

    componentDidMount(){
      let id = this.props.match.params.id
      DocumentTemplateCategory.edit({wilds: {id: `${id}`}}).then((documentTemplateCategory)=>{
        this.setState({documentTemplateCategory})
      })
    }

    render(){
        return <div className="persontocounterpartylinkreasons-Edit">
          {this.state.documentTemplateCategory &&
            <div>
              <PlainInputElement 
                  model={this.state.documentTemplateCategory}
                  propertyName="name"
                  registerInput={(it)=>{this.registerInput(it)}}
                  optional={{
                    placeholder: "name"
                  }}
              />
              <PlainInputElement 
                  model={this.state.documentTemplateCategory}
                  propertyName="description"
                  registerInput={(it)=>{this.registerInput(it)}}
                  optional={{
                    placeholder: "description"
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
      this.state.documentTemplateCategory.update().then((documentTemplateCategory)=>{
        if (documentTemplateCategory.isValid()) {
          ApplicationComponent.instance.flashMessageQueue.addMessage(
            "document template category successfully updated"
          )
        } 
        this.setState({documentTemplateCategory})
      })
    }

}
