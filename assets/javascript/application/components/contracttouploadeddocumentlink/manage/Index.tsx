import { BaseReactComponent } from "../../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { ModelCollection } from '../../../../modelLayer/ModelCollection';
import { ContractToUploadedDocumentLink } from '../../../models/ContractToUploadedDocumentLink'
import { ContractToUploadedDocumentLinkReason } from '../../../models/ContractToUploadedDocumentLinkReason'
import { Link, match } from 'react-router-dom';
import autobind from 'autobind-decorator';
import { Modal } from '../../shared/Modal'
import { ContractToUploadedDocumentLinkComponents } from '../ContractToUploadedDocumentLinkComponents'

export class Index extends BaseReactComponent {

    props: {
      match?: match<any>
    }

    state: {
      contractToUploadedDocumentLinks: ModelCollection<ContractToUploadedDocumentLink>,
      contractToUploadedDocumentLinkReasons: ModelCollection<ContractToUploadedDocumentLinkReason>
    } = {
      contractToUploadedDocumentLinks: new ModelCollection(),
      contractToUploadedDocumentLinkReasons: new ModelCollection()
    }

    modal: Modal

    componentWillMount() {
      let contractId = this.props.match.params.contractId
      ContractToUploadedDocumentLink.ofContractIndexEdit({wilds: {contractId}}).then((contractToUploadedDocumentLinks)=>{
        this.prepareState(contractToUploadedDocumentLinks)
      })
    }

    prepareState(contractToUploadedDocumentLinks: ModelCollection<ContractToUploadedDocumentLink>) {
      let reasonsById: {[id: number]: ContractToUploadedDocumentLinkReason} = {}

      contractToUploadedDocumentLinks.forEach((link)=>{  
        let reasonId = link.contractToUploadedDocumentLinkReason.id
        let reason = reasonsById[reasonId]
        if (!reason) {
          reason = new ContractToUploadedDocumentLinkReason(link.contractToUploadedDocumentLinkReason.properties)
          reasonsById[reasonId] = reason
        }
        reason.contractToUploadedDocumentLinks.push(link)
      })

      let contractToUploadedDocumentLinkReasons = Object.keys(reasonsById).map((key)=>{
        return reasonsById[key as any]
      })

      this.setState({
        contractToUploadedDocumentLinkReasons,
        contractToUploadedDocumentLinks
      })
    }

    render(){

        let contractToUploadedDocumentLinkReasons = this.state.contractToUploadedDocumentLinkReasons

        return <div className="contractToUploadedDocumentLinks-Index">
          <Modal ref={(it)=>{this.modal = it}}/>
          {contractToUploadedDocumentLinkReasons.map((contractToUploadedDocumentLinkReason)=>{
            return <div key={contractToUploadedDocumentLinkReason.id}>
              <h3>
                {contractToUploadedDocumentLinkReason.name}
              </h3>
              {contractToUploadedDocumentLinkReason.contractToUploadedDocumentLinks.map((contractToUploadedDocumentLink)=>{
                return <div key={contractToUploadedDocumentLink.id}>
                  <p>
                    {contractToUploadedDocumentLink.description}
                  </p>
                  <a href={contractToUploadedDocumentLink.uploadedDocument.fileUrl()}>  
                    {contractToUploadedDocumentLink.uploadedDocument.fileName}
                  </a>
                  <button onClick={()=>{this.delete(contractToUploadedDocumentLink)}}>
                    delete
                  </button>
                  <button onClick={()=>{this.initEditing(contractToUploadedDocumentLink)}}>
                    edit
                  </button>
                </div>
              })}
            </div>
          })}
          <button onClick={this.initContractToUploadedDocumentLink}>
            add file
          </button>
        </div>
    }

    @autobind
    onNewContractToUploadedDocumentLinkCreated(contractToUploadedDocumentLink: ContractToUploadedDocumentLink) {
      let foundFlag = false
      this.state.contractToUploadedDocumentLinkReasons.forEach((reason) => {
        if (reason.id === contractToUploadedDocumentLink.contractToUploadedDocumentLinkReason.id) {
          foundFlag = true
          reason.contractToUploadedDocumentLinks.push(contractToUploadedDocumentLink)
        } 
      })

      if (!foundFlag) {
          let reason = contractToUploadedDocumentLink.contractToUploadedDocumentLinkReason
          reason.contractToUploadedDocumentLinks.push(contractToUploadedDocumentLink)
          this.state.contractToUploadedDocumentLinkReasons.push(reason)
      }

      this.modal.close()
      this.setState({})
    }

    @autobind
    onContractToUploadedDocumentLinkDeleted(contractToUploadedDocumentLink: ContractToUploadedDocumentLink) {
      this.state.contractToUploadedDocumentLinks.filter((it)=>{
        return it !== contractToUploadedDocumentLink
      })
      this.prepareState(this.state.contractToUploadedDocumentLinks)
    }

    @autobind
    initContractToUploadedDocumentLink() {
       this.modal.open(
         <ContractToUploadedDocumentLinkComponents.manage.New
           contractId = {this.props.match.params.contractId}
           onCreateSuccess = {(contractToUploadedDocumentLink: ContractToUploadedDocumentLink)=>{
             this.state.contractToUploadedDocumentLinks.push(contractToUploadedDocumentLink)
             this.modal.close()
             this.prepareState(this.state.contractToUploadedDocumentLinks)
           }}
         />
       )
    }

    @autobind
    delete(contractToUploadedDocumentLink: ContractToUploadedDocumentLink) {
      contractToUploadedDocumentLink.forContractManageDestroy().then((returnedContractToUploadedDocumentLink)=>{
        if (!returnedContractToUploadedDocumentLink.isValid()) {
          let errorsString = "could not delete: "
          let errors = returnedContractToUploadedDocumentLink.errors
          for (let key of Object.keys(errors)) {
            let specificErrors = errors[key]
            specificErrors.forEach((error)=>{
              errorsString += error
            })
          }
          alert(errorsString)
          return
        }

        this.state.contractToUploadedDocumentLinks.filter((it)=>{
          return it !== contractToUploadedDocumentLink
        })
        
        this.prepareState(this.state.contractToUploadedDocumentLinks)

      })
    }

    @autobind
    initEditing(contractToUploadedDocumentLink: ContractToUploadedDocumentLink) {
      this.modal.open(
        <ContractToUploadedDocumentLinkComponents.manage.Edit
          contractToUploadedDocumentLink = {contractToUploadedDocumentLink}
          onEditDone = {()=>{
            this.modal.close()
            this.forceUpdate()
          }}
        />
      )
    }

}
