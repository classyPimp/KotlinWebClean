import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { ApplicationComponent } from '../ApplicationComponent';
import { match } from 'react-router-dom';
import { PlainInputElement } from '../../../reactUtils/plugins/formable/formElements/PlainInput';
import { MixinFormableTrait } from '../../../reactUtils/plugins/formable/MixinFormableTrait';
import autobind from 'autobind-decorator';
import { FlashMessageQueue } from '../shared/FlashMessageQueue';
import { Modal } from '../shared/Modal';
import { ContractToUploadedDocumentLinkReasonComponents } from './ContractToUploadedDocumentLinkReasonComponents'
import { ContractToUploadedDocumentLinkReason } from '../../models/ContractToUploadedDocumentLinkReason'


export class Edit extends MixinFormableTrait(BaseReactComponent) {

    props: {
      match: match<any>
    }

    state: {
      contractToUploadedDocumentLinkReason: ContractToUploadedDocumentLinkReason
    } = {
      contractToUploadedDocumentLinkReason: null
    }

    modal: Modal

    componentDidMount(){
      let id = this.props.match.params.id
      ContractToUploadedDocumentLinkReason.edit({wilds: {id: `${id}`}}).then((contractToUploadedDocumentLinkReason)=>{
        this.setState({contractToUploadedDocumentLinkReason})
      })
    }

    render(){
        return <div className="persontocounterpartylinkreasons-Edit">
          {this.state.contractToUploadedDocumentLinkReason &&
            <div>
              <PlainInputElement 
                  model={this.state.contractToUploadedDocumentLinkReason}
                  propertyName="name"
                  registerInput={(it)=>{this.registerInput(it)}}
                  optional={{
                    placeholder: "name"
                  }}
              />
              <PlainInputElement 
                  model={this.state.contractToUploadedDocumentLinkReason}
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
      this.state.contractToUploadedDocumentLinkReason.update().then((contractToUploadedDocumentLinkReason)=>{
        if (contractToUploadedDocumentLinkReason.isValid()) {
          ApplicationComponent.instance.flashMessageQueue.addMessage(
            "contract category successfully updated"
          )
        } 
        this.setState({contractToUploadedDocumentLinkReason})
      })
    }

}
