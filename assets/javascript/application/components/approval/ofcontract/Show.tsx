import { BaseReactComponent } from "../../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { ApplicationComponent } from '../../ApplicationComponent';
import { match } from 'react-router-dom';
import { PlainInputElement } from '../../../../reactUtils/plugins/formable/formElements/PlainInput';
import { MixinFormableTrait } from '../../../../reactUtils/plugins/formable/MixinFormableTrait';
import autobind from 'autobind-decorator';
import { FlashMessageQueue } from '../../shared/FlashMessageQueue';
import { Modal } from '../../shared/Modal';
import { Approval } from '../../../models/Approval'
import { ApprovalStep } from '../../../models/ApprovalStep'
import { ModelCollection } from '../../../../modelLayer/ModelCollection'
import { User } from '../../../models/User'
import { CurrentUser } from '../../../services/CurrentUser'

export class Show extends MixinFormableTrait(BaseReactComponent) {

    props: {
      match: match<any>
    }

    state: {
      approval: Approval,
      lastApprovalStep: ApprovalStep
      users: ModelCollection<User>
    } = {
      approval: null,
      lastApprovalStep: null,
      users: null,
    }

    modal: Modal
    contractId = this.props.match.params.contractId as any

    componentDidMount(){
      Approval.ofContractShow({wilds: {contractId: this.contractId}}).then((approval)=>{
        let lastApprovalStep = this.getLastApprovalStep(approval)  
        this.setState({approval, lastApprovalStep})
      })
    }

    getLastApprovalStep(approval: Approval): ApprovalStep {
      let lastApprovalStep = approval.approvalSteps.array[approval.approvalSteps.array.length - 1]
      return lastApprovalStep
    }

    render(){
      if (!this.state.approval) {
        return <div>
          ...loading
        </div>
      }
      return <div>
        <div>
          <p>
            documents to approve
          </p>
          {this.state.lastApprovalStep.approvalStepToUploadedDocumentLinks.map((approvalStepToUploadedDocumentLink)=>{
            return <div key={approvalStepToUploadedDocumentLink.id}>
              <p>
                {approvalStepToUploadedDocumentLink.uploadedDocument.description}
              </p>
              <a href={approvalStepToUploadedDocumentLink.uploadedDocument.fileUrl()}>  
                {approvalStepToUploadedDocumentLink.uploadedDocument.fileName}
              </a>
            </div>
          })}
          <div>
            {this.state.lastApprovalStep.approvalStepToApproverLinks.map((approvalStepToApproverLink)=>{
              return <div key={approvalStepToApproverLink.id}>
                <p>
                  {approvalStepToApproverLink.user.name}
                </p>
                {(this.currentUserIdMatchesId(approvalStepToApproverLink.user.id)) &&
                  <div>
                    <button onClick={this.approve}>
                      approve
                    </button>
                    <button>
                      file rejection
                    </button>
                  </div>
                }
                {approvalStepToApproverLink.approvalRejections.forEach((approvalRejection)=>{
                  return <div key={approvalRejection.id}>
                    <p>
                      {approvalRejection.reasonText}
                    </p>
                    {approvalRejection.approvalRejectionToUploadedDocumentLinks.map((approvalRejectionToUploadedDocumentLink)=>{  
                      return <div>
                        <p>
                          {approvalRejectionToUploadedDocumentLink.uploadedDocument.description}
                        </p>
                        <a href={approvalRejectionToUploadedDocumentLink.uploadedDocument.fileUrl()}>  
                          {approvalRejectionToUploadedDocumentLink.uploadedDocument.fileName}
                        </a>
                      </div>
                    })}
                  </div>
                })}
              </div>
            })}
          </div>
        </div>
      </div>  
       
    }

    @autobind
    currentUserIdMatchesId(id: number): Boolean {
      console.log(`current userId: ${CurrentUser.instance.loggedInInstance.id} - to test ${id}`)
      return CurrentUser.instance.loggedInInstance.id == id
    }

    @autobind
    approve() {
      
    }


}
