import { BaseReactComponent } from "../../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { ModelCollection } from '../../../../modelLayer/ModelCollection';
import { ContractToCounterPartyLink } from '../../../models/ContractToCounterPartyLink'
import { Link } from 'react-router-dom';
import autobind from 'autobind-decorator';
import { ContractToCounterPartyLinkComponents } from '../ContractToCounterPartyLinkComponents'
import { Modal } from '../../shared/Modal'

export class Index extends BaseReactComponent {

    props: {
      contractId: number
      contractToCounterPartyLinks: ModelCollection<ContractToCounterPartyLink>
    }

    state: {
      contractToCounterPartyLinkCanBeRemoved: boolean
    }

    modal: Modal

    componentWillMount() {
      this.defineIfContractToCounterPartyLinkCanBeRemoved()
    }


    render(){

        let contractToCounterPartyLinks = this.props.contractToCounterPartyLinks

        return <div className="contractToUploadedDocumentLinks-Index">
          <Modal ref={(it)=>{this.modal = it}}/>
          <div>
            {contractToCounterPartyLinks.map((contractToCounterPartyLink)=>{
              return <ContractToCounterPartyLinkComponents.Edit 
                key={contractToCounterPartyLink.id}
                contractToCounterPartyLink={contractToCounterPartyLink}
                onDelete={this.onContractToCounterPartyLinkDelete}
                removable={this.state.contractToCounterPartyLinkCanBeRemoved}
              />
            })}
          </div>
          <button onClick={this.initAddition}>
            + add
          </button>
        </div>
    }

    @autobind
    onContractToCounterPartyLinkDelete(contractToCounterPartyLink: ContractToCounterPartyLink) {
      this.props.contractToCounterPartyLinks.filter((it)=>{
        return it !== contractToCounterPartyLink
      })
      this.forceUpdate()
    }

    @autobind
    defineIfContractToCounterPartyLinkCanBeRemoved() {
      let size = this.props.contractToCounterPartyLinks.array.length
      let contractToCounterPartyLinkCanBeRemoved = false
      if (size > 1) {
        contractToCounterPartyLinkCanBeRemoved = true
      }
      this.setState({contractToCounterPartyLinkCanBeRemoved})
    }

    @autobind
    initAddition() {
      this.modal.open(
        <ContractToCounterPartyLinkComponents.New
          contractId = {this.props.contractId}
          onCreateSuccess = {(contractToCounterPartyLink: ContractToCounterPartyLink)=>{this.onContractToCounterPartyLinkCreate(contractToCounterPartyLink)}}
        />
      )
    }

    @autobind
    onContractToCounterPartyLinkCreate(contractToCounterPartyLink: ContractToCounterPartyLink) {
      this.props.contractToCounterPartyLinks.push(contractToCounterPartyLink)
      this.modal.close()
      this.defineIfContractToCounterPartyLinkCanBeRemoved()
      this.forceUpdate()
    }

}
