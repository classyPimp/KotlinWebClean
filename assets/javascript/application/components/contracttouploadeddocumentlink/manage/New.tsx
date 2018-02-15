import { BaseReactComponent } from "../../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { ContractToUploadedDocumentLink } from '../../../models/ContractToUploadedDocumentLink'
import { MixinFormableTrait } from '../../../../reactUtils/plugins/formable/MixinFormableTrait';
import { PlainInputElement } from '../../../../reactUtils/plugins/formable/formElements/PlainInput'
import autobind from 'autobind-decorator'
import { ErrorsShow } from '../../shared/ErrorsShow'
import { ApplicationComponent } from '../../ApplicationComponent';
import { DropDownSelectServerFed } from '../../formelements/DropdownSelectServerFed'
import { ContractToUploadedDocumentLinkReason } from '../../../models/ContractToUploadedDocumentLinkReason'
import { UploadedDocument } from '../../../models/UploadedDocument'
import { PlainFileInput } from '../../formelements/PlainFileInput'

export class New extends MixinFormableTrait(BaseReactComponent) {

    props: {
      contractId: number
      onCreateSuccess: (contractToUploadedDocumentLink: ContractToUploadedDocumentLink) => any
      contractToCounterPartyLinkReasonId?: number
    }

    state: {
      contractToUploadedDocumentLink: ContractToUploadedDocumentLink
    } = {
      contractToUploadedDocumentLink: new ContractToUploadedDocumentLink({
        contractId: this.props.contractId,
        contractToUploadedDocumentLinkReasonId: this.props.contractToCounterPartyLinkReasonId,
        uploadedDocument: new UploadedDocument()
      })
    }

    render(){
        return <div className="persontocounterpartylinkreasons-New">
          <h2>
            add file
          </h2>
          {this.state.contractToUploadedDocumentLink.getErrorsFor('general') &&
              <ErrorsShow errors={this.state.contractToUploadedDocumentLink.getErrorsFor('general')}/>
          }
          <PlainInputElement
            model={this.state.contractToUploadedDocumentLink}
            propertyName="description"
            registerInput={(it)=>{this.registerInput(it)}}
            optional={{placeholder: "describe attachment"}}
          />
          <DropDownSelectServerFed
            model = {this.state.contractToUploadedDocumentLink}
            propertyName = "contractToUploadedDocumentLinkReasonId"
            registerInput = {(it)=>{this.registerInput(it)}}
            propertyToShow = "name"
            propertyToSelect  = "id"
            queryingFunction = {ContractToUploadedDocumentLinkReason.formFeedsIndex.bind(ContractToUploadedDocumentLinkReason)}
            preselected = {this.state.contractToUploadedDocumentLink.contractToUploadedDocumentLinkReasonId}
            optional = {{
              placeholder: "select file connection reason"
            }}
          />
          <PlainFileInput
            registerInput={(it)=>{this.registerInput(it)}}
            model={this.state.contractToUploadedDocumentLink.uploadedDocument}
            propertyName={"file"}
          />          
          <button onClick={this.submit}>
            submit
          </button>
        </div>
    }

    @autobind
    submit(){
      this.collectInputs()
      this.state.contractToUploadedDocumentLink.validate()
      this.state.contractToUploadedDocumentLink.forContractManageCreate().then((contractToUploadedDocumentLink)=>{
        if (contractToUploadedDocumentLink.isValid()) {
          this.props.onCreateSuccess(contractToUploadedDocumentLink)
          return
        }
        this.setState({contractToUploadedDocumentLink})
      })
    }

}
