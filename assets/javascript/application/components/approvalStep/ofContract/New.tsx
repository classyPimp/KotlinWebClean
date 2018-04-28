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
      approvalStep: ApprovalStep
    } = {
      approvalStep: new ApprovalStep()
    }

    props: {
      match?: match<any>
      onApprovalStepCreateSuccess: (approvalStep: ApprovalStep) => any
      approvalId: number
    }

    modal: Modal = null
    componentKeyTracker: number = 1

    render(){
        return <div className="persontocounterpartylinkreasons-New">
          <Modal ref={(it)=>{this.modal = it}}/>
          <h2>
            initialize contract approval
          </h2>
          {this.state.approvalStep.getErrorsFor('general') &&
              <ErrorsShow errors={this.state.approvalStep.getErrorsFor('general')}/>
          }
          <p>
            documents to approve:
          </p>          
          {this.state.approvalStep.approvalStepToUploadedDocumentLinks.map((approvalStepToUploadedDocumentLink)=>{
            return <div key ={approvalStepToUploadedDocumentLink.reactComponentKey}>
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
    removeLinkToUploadedDocument(link: ApprovalStepToUploadedDocumentLink) {
      this.state.approvalStep.approvalStepToUploadedDocumentLinks.filter((it)=>{
        return it !== link
      })
      this.forceUpdate()
    }


    @autobind
    addApprovalStepToUploadedDocumentLink() {
      let uploadedDocument = new UploadedDocument()
      uploadedDocument.reactComponentKey = this.componentKeyTracker += 1

      let approvalStepToUploadedDocumentLink = new ApprovalStepToUploadedDocumentLink({
        uploadedDocument
      })

      approvalStepToUploadedDocumentLink.reactComponentKey = this.componentKeyTracker += 1
      
      this.state.approvalStep.approvalStepToUploadedDocumentLinks.push(  
        approvalStepToUploadedDocumentLink
      )
      this.forceUpdate()
    }

    @autobind
    submit(){
      this.collectInputs()
      this.state.approvalStep.validate()
      let approvalId = this.props.approvalId as any
      this.state.approvalStep.ofApprovalOfContractCreate({wilds: {approvalId}, serializeAsForm: true}).then((approval)=>{
        if (!approval.isValid()) {
          this.setState({approval})
          return
        }
        alert("no errors ok")
      })
    }

}
