import { BaseReactComponent } from "../../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { ApplicationComponent } from '../../ApplicationComponent';
import { Router, Route, Link, match, Switch } from 'react-router-dom';
import { PlainInputElement } from '../../../../reactUtils/plugins/formable/formElements/PlainInput';
import { MixinFormableTrait } from '../../../../reactUtils/plugins/formable/MixinFormableTrait';
import autobind from 'autobind-decorator';
import { FlashMessageQueue } from '../../shared/FlashMessageQueue';
import { Modal } from '../../shared/Modal';
import { Approval } from '../../../models/Approval'
import { ApprovalStep } from '../../../models/ApprovalStep'
import { ApprovalRejection } from '../../../models/ApprovalRejection'
import { ModelCollection } from '../../../../modelLayer/ModelCollection'
import { User } from '../../../models/User'
import { CurrentUser } from '../../../services/CurrentUser'
import { ApprovalStepToApproverLink } from '../../../models/ApprovalStepToApproverLink'
import { ApprovalRejectionComponents } from '../../approvalrejection/ApprovalRejectionComponents'
import { DiscussionComponents } from '../../discussion/DiscussionComponents'
import { ApprovalStepComponents } from '../../approvalStep/ApprovalStepComponents'

export class Show extends MixinFormableTrait(BaseReactComponent) {

    props: {
      match: match<any>
      approval: Approval
    }

    state: {
      approval: Approval,
      lastApprovalStep: ApprovalStep
      users: ModelCollection<User>
      currentStepInView: number
    } = {
      approval: null,
      lastApprovalStep: null,
      users: null,
      currentStepInView: null
    }

    modal: Modal
    contractId = this.props.match.params.contractId as any

    componentDidMount(){
      // Approval.ofContractShow({wilds: {contractId: this.contractId}}).then((approval)=>{
      //   let lastApprovalStep = this.getLastApprovalStep(approval)  
      //   this.setState({approval, lastApprovalStep})
      // })
      let approval = this.props.approval
      this.handlePassedApprovalFromProps(approval)
    }

    handlePassedApprovalFromProps(approval: Approval) {
      let lastApprovalStep: ApprovalStep = this.getLastApprovalStep(approval)
      let currentStepInView: number = null
      for (let i = 0; i < approval.approvalSteps.array.length, i++;) {
        let approvalStep: ApprovalStep = approval.approvalSteps.array[i] 
        if (approvalStep.id === lastApprovalStep.id) {
          currentStepInView = i
          break
        }
      }
      if (!currentStepInView) {
        currentStepInView = approval.approvalSteps.array.length - 1
      }
      this.setState({approval, lastApprovalStep, currentStepInView})
    }

    componentWillReceiveProps(nextProps: any) {
      let currentApproval = this.state.approval
      let nextApproval = nextProps.approval
      if (currentApproval !== nextApproval) {
        this.handlePassedApprovalFromProps(nextApproval)
      }
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
        <Modal ref={(it)=>{this.modal = it}}/>
        <div>
          <div>
            {this.state.approval.approvalSteps.map((it, index)=>{
              return <span onClick = {()=>{this.setApprovalStepInViewIndex(index)}}>
                {(this.state.currentStepInView === index) 
                  ? `>${index}<`
                  : `${index}`
                }
              </span>
            })}
          </div>
          <p>
            documents to approve
          </p>
          {this.getCurrentStepInView().approvalStepToUploadedDocumentLinks.map((approvalStepToUploadedDocumentLink)=>{
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
            <button onClick={this.initializeNewApprovalStep}>
              initialize new approval step
            </button>
          </div>
          <div>
            {this.getCurrentStepInView().approvalStepToApproverLinks.map((approvalStepToApproverLink)=>{
              return <div key={approvalStepToApproverLink.id}>
                <p>
                  {approvalStepToApproverLink.user.name}
                </p>

                {approvalStepToApproverLink.isApproved 
                  ? <div>
                    {approvalStepToApproverLink.isApproved}
                  </div>
                  : (this.currentUserIdMatchesId(approvalStepToApproverLink.user.id)) 
                    ? <div>
                      {!approvalStepToApproverLink.isApproved &&
                        <div>
                          <button onClick={()=>{this.approve(approvalStepToApproverLink)}}>
                            approve
                          </button>
                          <button onClick={()=>{this.createRejection(approvalStepToApproverLink)}}>
                            file rejection
                          </button>
                        </div>
                      }
                    </div>
                    : <div>
                      X
                    </div>
                }
              </div>
            })}
          </div>
          <div>
            {(this.state.approval.approvalRejections.array.length > 0) &&
              <p>
                filed rejections:
              </p>
            }
            {this.state.approval.approvalRejections.map((approvalRejection)=>{
              if (!this.shouldRenderRejection(approvalRejection)) {
                return null
              }
              return <div key={approvalRejection.id}>
                <p>
                  {approvalRejection.user.name}      
                </p>
                <Link to = {`/dashboards/contracts/${this.props.match.params.contractId}/approval/withRejectionDiscussion/${approvalRejection.discussion.id}`}>
                  {approvalRejection.reasonText}
                </Link>
                {approvalRejection.approvalRejectionToUploadedDocumentLinks.map((approvalRejectionToUploadedDocumentLink)=>{  
                  return <div key = {approvalRejectionToUploadedDocumentLink.id}>
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
        </div>
        <Route path = {`/dashboards/contracts/${this.props.match.params.contractId}/approval/withRejectionDiscussion/:discussionId`} component = {DiscussionComponents.Show}/>
      </div>  
       
    }

    @autobind
    getNextToCurrentApprovalStepsCreatedAt(): string {
      let index = this.state.currentStepInView
      let next = this.state.approval.approvalSteps.array[index + 1]
      if (next) {
        return next.createdAt
      } 
      return null
    }

    @autobind
    shouldRenderRejection(approvalRejection: ApprovalRejection) {
      let createdAt = approvalRejection.createdAt
      let currentApprovalStepCreatedAt = this.getCurrentStepInView().createdAt
      let nextApprovalStepCreatedAt = this.getNextToCurrentApprovalStepsCreatedAt()
      if (nextApprovalStepCreatedAt === null) {
        console.log("no next, should render")
        return true
      }
      if (createdAt < nextApprovalStepCreatedAt) {
        return true
      }
      return false
    }

    @autobind
    getCurrentStepInView(): ApprovalStep {
      return this.state.approval.approvalSteps.array[this.state.currentStepInView]
    }

    @autobind
    setApprovalStepInViewIndex(index: number) {
      this.setState({currentStepInView: index})
    }

    @autobind
    initializeNewApprovalStep() {
      this.modal.open(
        <ApprovalStepComponents.ofContract.New
          onApprovalStepCreateSuccess = {this.onApprovalStepCreateSuccess}
          approvalId = {this.state.approval.id}
        />
      )
    }

    @autobind
    onApprovalStepCreateSuccess(approvalStep: ApprovalStep) {
      this.modal.close()
      this.componentDidMount()
    }

    @autobind
    currentUserIdMatchesId(id: number): Boolean {
      console.log(`current userId: ${CurrentUser.instance.loggedInInstance.id} - to test ${id}`)
      return CurrentUser.instance.loggedInInstance.id == id
    }

    @autobind
    approve(approvalStepToApproverLink: ApprovalStepToApproverLink) {
       approvalStepToApproverLink.ofContractApprove().then((returnedApprovalStepToApproverLink)=>{
         returnedApprovalStepToApproverLink.validate()
         if (!returnedApprovalStepToApproverLink.isValid()) {
           alert("invalid TODO: describe errors")
         } else {
           this.componentDidMount()
         }
       })
    }

    @autobind
    createRejection(approvalStepToApproverLink: ApprovalStepToApproverLink) {
      this.modal.open(
        <ApprovalRejectionComponents.ofContract.New
          approvalId = {this.state.approval.id}
          onApprovalRejectionCreateSuccess = {(approvalRejection: ApprovalRejection)=>{this.onApprovalRejectionCreateSuccess(approvalRejection, approvalStepToApproverLink)}}
        />
      )
    }

    @autobind
    onApprovalRejectionCreateSuccess(approvalRejection: ApprovalRejection, approvalStepToApproverLink: ApprovalStepToApproverLink) {
      this.state.approval.approvalRejections.push(approvalRejection)
      this.modal.close()
      this.componentDidMount()
    }


}
