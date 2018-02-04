import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { ContractToUploadedDocumentLinkReason } from '../../models/ContractToUploadedDocumentLinkReason'
import { match } from 'react-router-dom'

export class Show extends BaseReactComponent {

    state: {
      contractToUploadedDocumentLinkReason: ContractToUploadedDocumentLinkReason
    } = {
      contractToUploadedDocumentLinkReason: null
    }

    props: {
      match: match<any>
    }

    componentDidMount(){
      let id = this.props.match.params.id
      ContractToUploadedDocumentLinkReason.show({wilds: {id: `${id}`}}).then((contractToUploadedDocumentLinkReason)=>{
         this.setState({contractToUploadedDocumentLinkReason})
      })
    }

    render(){
        return <div className="persontocounterpartylinkreasons-Show">
          {this.state.contractToUploadedDocumentLinkReason &&
            <div>
              <h3>
                {
                  this.state.contractToUploadedDocumentLinkReason.name
                }
              </h3>
              <p>
                {
                  this.state.contractToUploadedDocumentLinkReason.description
                }
              </p>
            </div>
          }
        </div>
    }

}
