import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { ContractToUploadedDocumentLinkReason } from '../../models/ContractToUploadedDocumentLinkReason'
import { MixinFormableTrait } from '../../../reactUtils/plugins/formable/MixinFormableTrait';
import { PlainInputElement } from '../../../reactUtils/plugins/formable/formElements/PlainInput'
import autobind from 'autobind-decorator'
import { ErrorsShow } from '../shared/ErrorsShow'
import { ApplicationComponent } from '../ApplicationComponent';

export class New extends MixinFormableTrait(BaseReactComponent) {

    state: {
      contractToUploadedDocumentLinkReason: ContractToUploadedDocumentLinkReason
    } = {
      contractToUploadedDocumentLinkReason: new ContractToUploadedDocumentLinkReason()
    }

    render(){
        return <div className="persontocounterpartylinkreasons-New">
          <h2>
            Create new contract category
          </h2>
          {this.state.contractToUploadedDocumentLinkReason.getErrorsFor('general') &&
              <ErrorsShow errors={this.state.contractToUploadedDocumentLinkReason.getErrorsFor('general')}/>
          }
          <PlainInputElement
            model={this.state.contractToUploadedDocumentLinkReason}
            propertyName="name"
            registerInput={(it)=>{this.registerInput(it)}}
            optional={{placeholder: "name"}}
          />
          <PlainInputElement
            model={this.state.contractToUploadedDocumentLinkReason}
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
      
      let currentContractToUploadedDocumentLinkReason = this.state.contractToUploadedDocumentLinkReason
      currentContractToUploadedDocumentLinkReason.validate()
      if (!currentContractToUploadedDocumentLinkReason.isValid()) {
        this.setState({contractToUploadedDocumentLinkReason: currentContractToUploadedDocumentLinkReason})
        return
      }

      currentContractToUploadedDocumentLinkReason.create().then((contractToUploadedDocumentLinkReason)=>{
        if (!contractToUploadedDocumentLinkReason.isValid()) {
              this.setState({contractToUploadedDocumentLinkReason})
              return
        } 
        ApplicationComponent.instance.flashMessageQueue.addMessage(
          "contract category successfully created"
        )
        this.setState({contractToUploadedDocumentLinkReason})
      })
      
    }

}
