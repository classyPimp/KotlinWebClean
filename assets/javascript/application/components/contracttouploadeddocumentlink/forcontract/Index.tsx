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
      match: match<any>
    }

    state: {
      contractToUploadedDocumentLinks: ModelCollection<ContractToUploadedDocumentLink>,
      contractToUploadedDocumentLinkReasons: ModelCollection<ContractToUploadedDocumentLinkReason>
    } = {
      contractToUploadedDocumentLinks: new ModelCollection(),
      contractToUploadedDocumentLinkReasons: new ModelCollection()
    }

    componentDidMount() {
      let contractId = this.props.match.params.contractId
      ContractToUploadedDocumentLink.forContractIndex({wilds: {contractId}}).then((contractToUploadedDocumentLinks)=>{
        this.prepareState(contractToUploadedDocumentLinks)
        this.setState({contractToUploadedDocumentLinks})
      })
    }

    modal: Modal

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
                </div>
              })}
            </div>
          })}
        </div>
    }

}
