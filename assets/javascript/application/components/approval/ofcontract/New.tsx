import { BaseReactComponent } from "../../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { Contract } from '../../../models/Contract'
import { ContractCategory } from '../../../models/ContractCategory'
import { CounterParty } from '../../../models/CounterParty'
import { ContractToCounterPartyLink } from '../../../models/ContractToCounterPartyLink'
import { MixinFormableTrait } from '../../../../reactUtils/plugins/formable/MixinFormableTrait';
import { PlainInputElement } from '../../../../reactUtils/plugins/formable/formElements/PlainInput'
import { DropDownSelectServerFed } from '../../formelements/DropdownSelectServerFed'
import autobind from 'autobind-decorator'
import { ErrorsShow } from '../../shared/ErrorsShow'
import { ApplicationComponent } from '../../ApplicationComponent';
import { Approval } from '../../../models/Approval'
import { ApprovalToApproverLink } from '../../../models/ApprovalToApproverLink'
import { ApprovalStepToUploadedDocumentLink } from '../../../models/ApprovalStepToUploadedDocumentLink'
import { ApprovalStep } from '../../../models/ApprovalStep'
import { Router, Route, Link, match, Switch } from 'react-router-dom';
import { ModelCollection } from '../../../../modelLayer/ModelCollection'
import { Modal } from '../../shared/Modal'
import { UsersComponents } from '../../users/UsersComponents'
import { User } from '../../../models/User'
import { UploadedDocument } from '../../../models/UploadedDocument'
import { PlainFileInput } from '../../formelements/PlainFileInput'

export class New extends MixinFormableTrait(BaseReactComponent) {

    state: {
      approval: Approval
    } = {
      approval: null
    }

    constructor(...args: Array<any>) {
      super(...args)
      let approvableId = this.props.match.params.contractId
      let approvalSteps = new ModelCollection<ApprovalStep>(new ApprovalStep())
      this.state.approval = new Approval({approvableId, approvalSteps})
    }

    modal: Modal = null
    approvalStepToUploadedDocumentLinkKeyTracker: number = 1

    render(){
        console.log(this.state.approval)
        return <div className="persontocounterpartylinkreasons-New">
          <Modal ref={(it)=>{this.modal = it}}/>
          <h2>
            initialize contract approval
          </h2>
          {this.state.approval.getErrorsFor('general') &&
              <ErrorsShow errors={this.state.approval.getErrorsFor('general')}/>
          }
          {this.state.approval.approvalToApproverLinks.map((approvalToApproverLink)=>{
            return <div key = {approvalToApproverLink.userId}>
              {approvalToApproverLink.errors &&
                <ErrorsShow errors = {approvalToApproverLink.errors["userId"]}/>
              }
              <p>
                {approvalToApproverLink.user.name}
              </p>
              <button onClick={()=>{this.removeApproverLink(approvalToApproverLink)}}>
                -
              </button>
            </div>
          })}
          <button onClick={this.initApproverAddition}>
            +
          </button>
          <p>
            attached documents:
          </p>          
          {this.state.approval.approvalSteps.array[0].approvalStepToUploadedDocumentLinks.map((approvalStepToUploadedDocumentLink)=>{
            return <div key ={approvalStepToUploadedDocumentLink.properties["key"]}>
              <PlainInputElement
                registerInput = {(it)=>{this.registerInput(it)}}
                model = {approvalStepToUploadedDocumentLink}
                propertyName = "description"
                optional = {{
                  placeholder: "file description"
                }}
              />
              <PlainFileInput
                model = {approvalStepToUploadedDocumentLink.uploadedDocument}
                propertyName = "file"
                registerInput = {(it)=>{this.registerInput(it)}}
                optional = {{
                  placeholder: "select file"
                }}
              />
              <button onClick={()=>{this.removeLinkToUploadedDocument(approvalStepToUploadedDocumentLink)}}>
                -
              </button>
            </div>
          })}
          <button onClick={this.addApprovalStepToUploadedDocumentLink}>
            +
          </button>
          <button onClick={this.submit}>
            submit
          </button>
        </div>
    }

    @autobind
    removeApproverLink(link: ApprovalToApproverLink) {
      this.state.approval.approvalToApproverLinks.filter((it)=>{
        return it !== link
      })
      this.forceUpdate()
    }

    @autobind
    removeLinkToUploadedDocument(link: ApprovalStepToUploadedDocumentLink) {
      this.state.approval.approvalSteps.forEach((it)=>{
        it.approvalStepToUploadedDocumentLinks.filter((it)=>{
          return it !== link
        })
      })
      this.forceUpdate()
    }

    @autobind
    initApproverAddition() {
      this.modal.open(
        <UsersComponents.forsearchform.Index onUserSelected={this.addApprovalToApproverLinkWithUser}/>
      )
    }

    @autobind
    addApprovalToApproverLinkWithUser(user: User) {
      let approvalToApproverLink = new ApprovalToApproverLink({userId: user.id, user})
      this.state.approval.approvalToApproverLinks.push(approvalToApproverLink)
      this.modal.close()
      this.forceUpdate()
    }

    @autobind
    addApprovalStepToUploadedDocumentLink() {
      this.state.approval.approvalSteps.array[0].approvalStepToUploadedDocumentLinks.push(
        new ApprovalStepToUploadedDocumentLink({
          uploadedDocument: new UploadedDocument({key: this.approvalStepToUploadedDocumentLinkKeyTracker += 1})
        })
      )
      this.forceUpdate()
    }

    @autobind
    submit(){
      this.collectInputs()
      this.state.approval.validate()
      let contractId = this.props.match.params.contractId as string
      this.state.approval.ofContractCreate({wilds: {contractId}, serializeAsForm: true}).then((approval)=>{
        if (approval.isValid()) {
          this.setState({approval})
          return
        }
        alert("no errors ok")
      })
    }

}
