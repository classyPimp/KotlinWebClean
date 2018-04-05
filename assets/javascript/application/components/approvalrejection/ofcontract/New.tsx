import { BaseReactComponent } from "../../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { ApprovalRejection } from '../../../models/ApprovalRejection'
import { ApprovalStepToApproverLink } from '../../../models/ApprovalStepToApproverLink'
import { ApprovalRejectionToUploadedDocumentLink } from '../../../models/ApprovalRejectionToUploadedDocumentLink'
import { MixinFormableTrait } from '../../../../reactUtils/plugins/formable/MixinFormableTrait';
import { PlainInputElement } from '../../../../reactUtils/plugins/formable/formElements/PlainInput'
import { DropDownSelectServerFed } from '../../formelements/DropdownSelectServerFed'
import autobind from 'autobind-decorator'
import { ErrorsShow } from '../../shared/ErrorsShow'
import { PlainFileInput } from '../../formelements/PlainFileInput'
import { ApplicationComponent } from '../../ApplicationComponent';
import { UploadedDocument } from '../../../models/UploadedDocument'

export class New extends MixinFormableTrait(BaseReactComponent) {

    props: {
      approvalStepToApproverLinkId: number
      onApprovalRejectionCreateSuccess: (approvalRejection: ApprovalRejection)=>any
    }

    state: {
      approvalRejection: ApprovalRejection
    } = {
      approvalRejection: new ApprovalRejection({approvalStepToApproverLinkId: this.props.approvalStepToApproverLinkId})
    }

    componentKeyTracker = 0

    render(){
      return <div>
        <PlainInputElement
          model = {this.state.approvalRejection}
          propertyName = "reasonText"
          registerInput = {(it)=>{this.registerInput(it)}}
          optional = {{
            placeholder: "describe rejection reason"
          }}
        />         
        {this.state.approvalRejection.approvalRejectionToUploadedDocumentLinks.map((approvalRejectionToUploadedDocumentLink)=>{
          return <div key={approvalRejectionToUploadedDocumentLink.keyForReactComponent}>
            <PlainInputElement
              model = {approvalRejectionToUploadedDocumentLink}
              propertyName = "description"
              registerInput = {(it)=>{this.registerInput(it)}}
              optional = {{
                placeholder: "file descritpion"
              }}
            />
            <PlainFileInput
              model = {approvalRejectionToUploadedDocumentLink.uploadedDocument}
              propertyName = "file"
              registerInput = {(it)=>{this.registerInput(it)}}
              optional = {{
                placeholder: "file to attach"
              }}
            />
            <button onClick = {()=>{this.removeApprovalRejectionToUploadedDocumentLink(approvalRejectionToUploadedDocumentLink)}}>
              - remove file
            </button>
          </div>
        })}
        <div>
          <button onClick={this.addApprovalRejectionToUploadedDocumentLink}>
            + add file
          </button>
        </div>   
        <button onClick={this.submit}>
          submit
        </button>        
      </div>
    }

    @autobind
    addApprovalRejectionToUploadedDocumentLink() {
      let approvalRejectionToUploadedDocumentLink = new ApprovalRejectionToUploadedDocumentLink({uploadedDocument: new UploadedDocument()})
      approvalRejectionToUploadedDocumentLink.keyForReactComponent = this.componentKeyTracker += 1
      this.state.approvalRejection.approvalRejectionToUploadedDocumentLinks.push(approvalRejectionToUploadedDocumentLink)
      this.forceUpdate()
    }

    @autobind
    removeApprovalRejectionToUploadedDocumentLink(approvalRejectionToUploadedDocumentLink: ApprovalRejectionToUploadedDocumentLink) {
      this.state.approvalRejection.approvalRejectionToUploadedDocumentLinks.filter((it)=>{
        return it !== approvalRejectionToUploadedDocumentLink
      })
      this.forceUpdate()
    }
   
    @autobind
    submit() {
      this.collectInputs()
      console.log(this.state.approvalRejection)
      this.state.approvalRejection.ofContractCreate({serializeAsForm: true, wilds: {approvalStepToApproverLinkId: this.props.approvalStepToApproverLinkId.toString()}}).then((approvalRejection)=>{
        if (approvalRejection.isValid()) {
          this.props.onApprovalRejectionCreateSuccess(approvalRejection)
          return
        }
        this.setState({approvalRejection})
      })
    }

}
