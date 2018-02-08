import { BaseReactComponent } from "../../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { ModelCollection } from '../../../../modelLayer/ModelCollection';
import { ContractToUploadedDocumentLink } from '../../../models/ContractToUploadedDocumentLink'
import { ContractToUploadedDocumentLinkReason } from '../../../models/ContractToUploadedDocumentLinkReason'
import { Link } from 'react-router-dom';
import autobind from 'autobind-decorator';

export class Show extends BaseReactComponent {

    props: {
      contractToUploadedDocumentLinks: ModelCollection<ContractToUploadedDocumentLink>
    }

    state: {
      contractToUploadedDocumentLinkReasons: ModelCollection<ContractToUploadedDocumentLinkReason>
    }

    componentDidMount() {
      let reasonsById: {[id: number]: ContractToUploadedDocumentLinkReason} = {}
      this.props.contractToUploadedDocumentLinks.forEach((link)=>{
        let reasonId = link.contractToUploadedDocumentLinkReason.id
        let match = reasonsById[reasonId]
        if (!match) {
           reasonsById[reasonId] = link.contractToUploadedDocumentLinkReason 
        }
      })
      let contractToUploadedDocumentLinkReasons = Object.keys(reasonsById).map((key)=>{
        return reasonsById[key as any]
      })
      this.setState({contractToUploadedDocumentLinkReasons})
    }

    render(){

        let contractToUploadedDocumentLinkReasons = this.state.contractToUploadedDocumentLinkReasons

        return <div className="contractToUploadedDocumentLinks-Index">
          {contractToUploadedDocumentLinkReasons.map((contractToUploadedDocumentLinkReason)=>{
            return <div key={contractToUploadedDocumentLinkReason.id}>
              <h3>
                {contractToUploadedDocumentLinkReason.name}
              </h3>
              {contractToUploadedDocumentLinkReason.contractToUploadedDocumentLinks.map((contractToUploadedDocumentLink)=>{
                <div>
                  <Link to={contractToUploadedDocumentLink.uploadedDocument.fileUrl()}>  
                    {contractToUploadedDocumentLink.uploadedDocument.fileName}
                  </Link>
                </div>
              })}
            </div>
          })}
        </div>
    }

}
