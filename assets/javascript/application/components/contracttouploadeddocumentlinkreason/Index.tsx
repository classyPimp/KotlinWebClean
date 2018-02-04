import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { ModelCollection } from '../../../modelLayer/ModelCollection';
import { ContractToUploadedDocumentLinkReason } from '../../models/ContractToUploadedDocumentLinkReason'
import { Link } from 'react-router-dom';
import autobind from 'autobind-decorator';

export class Index extends BaseReactComponent {

    state: {
      contractToUploadedDocumentLinkReasons: ModelCollection<ContractToUploadedDocumentLinkReason>
    } = {
      contractToUploadedDocumentLinkReasons: new ModelCollection()
    }

    componentDidMount(){
      ContractToUploadedDocumentLinkReason.index().then((contractToUploadedDocumentLinkReasons: ModelCollection<ContractToUploadedDocumentLinkReason>)=>{
        this.setState({contractToUploadedDocumentLinkReasons})
      })
    }

    render(){
        return <div className="contractToUploadedDocumentLinkReasons-Index">
          {
            this.state.contractToUploadedDocumentLinkReasons.map((contractToUploadedDocumentLinkReason, index)=>{
              return <div className="individual" key={index}>
                <p>
                  <Link to={`/dashboards/contractToUploadedDocumentLinkReasons/${contractToUploadedDocumentLinkReason.id}`}>
                    {
                      contractToUploadedDocumentLinkReason.name
                    }
                  </Link>
                </p>
                <Link to={`/dashboards/contractToUploadedDocumentLinkReasons/${contractToUploadedDocumentLinkReason.id}`}>
                  <button>
                    show
                  </button>
                </Link>
                <Link to={`/dashboards/contractToUploadedDocumentLinkReasons/${contractToUploadedDocumentLinkReason.id}/edit`}>
                  <button>
                    edit
                  </button>
                </Link>
                <button onClick={()=>{this.destroyLinkReason(contractToUploadedDocumentLinkReason)}}>
                  delete
                </button>
              </div>
            })
          }
        </div>
    }

    @autobind
    destroyLinkReason(contractToUploadedDocumentLinkReason: ContractToUploadedDocumentLinkReason) {
        contractToUploadedDocumentLinkReason.destroy().then((returnedContractToUploadedDocumentLinkReason)=>{
          if (returnedContractToUploadedDocumentLinkReason.isValid()) {
            this.state.contractToUploadedDocumentLinkReasons.filter((it)=>{
              return it !== contractToUploadedDocumentLinkReason
            })
            this.setState({contractToUploadedDocumentLinkReasons: this.state.contractToUploadedDocumentLinkReasons})
          } else {
            alert("could not be deleted")
          }
        }) 
    }

}
