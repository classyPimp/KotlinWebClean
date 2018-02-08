import { BaseReactComponent } from "../../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { ModelCollection } from '../../../../modelLayer/ModelCollection';
import { ContractToCounterPartyLink } from '../../../models/ContractToCounterPartyLink'
import { Link } from 'react-router-dom';
import autobind from 'autobind-decorator';

export class Index extends BaseReactComponent {

    props: {
      contractToCounterPartyLinks: ModelCollection<ContractToCounterPartyLink>
    }


    render(){

        let contractToCounterPartyLinks = this.props.contractToCounterPartyLinks

        return <div className="contractToUploadedDocumentLinks-Index">
          {contractToCounterPartyLinks.map((contractToCounterPartyLink)=>{
            return <div key={contractToCounterPartyLink.id}>
              
            </div>
          })}
        </div>
    }

}
