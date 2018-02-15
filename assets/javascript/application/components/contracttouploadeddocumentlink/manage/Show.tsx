import { BaseReactComponent } from "../../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { ModelCollection } from '../../../../modelLayer/ModelCollection';
import { ContractToUploadedDocumentLink } from '../../../models/ContractToUploadedDocumentLink'
import { ContractToUploadedDocumentLinkReason } from '../../../models/ContractToUploadedDocumentLinkReason'
import { Link } from 'react-router-dom';
import autobind from 'autobind-decorator';

export class Show extends BaseReactComponent {

    props: {
      contractToUploadedDocumentLink: ContractToUploadedDocumentLink
    }

    render(){
        let contractToUploadedDocumentLink = this.props.contractToUploadedDocumentLink  

        return <div className="contractToUploadedDocumentLinks-Index">
          <Link to={`#{contractToUploadedDocumentLink.uploadedDocument.fileUrl()}`}>
            {contractToUploadedDocumentLink.uploadedDocument.fileName}
          </Link>
          
        </div>
    }

}
