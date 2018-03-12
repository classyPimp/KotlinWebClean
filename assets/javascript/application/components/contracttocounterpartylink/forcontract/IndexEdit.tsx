import { BaseReactComponent } from "../../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { ModelCollection } from '../../../../modelLayer/ModelCollection';
import { ContractToCounterPartyLink } from '../../../models/ContractToCounterPartyLink'
import { Link, match } from 'react-router-dom';
import autobind from 'autobind-decorator';
import { ContractToCounterPartyLinkComponents } from '../ContractToCounterPartyLinkComponents'
import { Modal } from '../../shared/Modal'

export class IndexEdit extends BaseReactComponent {

    props: {
      match: match<any>
    }

    state: {
      contractToCounterPartyLinkCanBeRemoved: boolean,
      contractToCounterPartyLinks: ModelCollection<ContractToCounterPartyLink>
    } = {
      contractToCounterPartyLinkCanBeRemoved: null,
      contractToCounterPartyLinks: new ModelCollection()      
    }

    modal: Modal

    componentWillMount() {
      let contractId = this.props.match.params.contractId
      ContractToCounterPartyLink.forContractIndexEdit({wilds: {contractId}}).then((contractToCounterPartyLinks)=>{
        this.defineIfContractToCounterPartyLinkCanBeRemoved()
        this.setState({contractToCounterPartyLinks})
      })
      
    }


    render(){

        let contractToCounterPartyLinks = this.state.contractToCounterPartyLinks

        if (!contractToCounterPartyLinks) {
          return <div />
        }

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
      this.state.contractToCounterPartyLinks.filter((it)=>{
        return it !== contractToCounterPartyLink
      })
      this.defineIfContractToCounterPartyLinkCanBeRemoved()
      this.forceUpdate()
    }

    @autobind
    defineIfContractToCounterPartyLinkCanBeRemoved() {
      let size = this.state.contractToCounterPartyLinks.array.length
      let contractToCounterPartyLinkCanBeRemoved = false
      if (size > 1) {
        contractToCounterPartyLinkCanBeRemoved = true
      }
      this.setState({contractToCounterPartyLinkCanBeRemoved})
    }

    @autobind
    initAddition() {
      let contractId = this.props.match.params.contractId
      this.modal.open(
        <ContractToCounterPartyLinkComponents.New
          contractId = {contractId}
          onCreateSuccess = {(contractToCounterPartyLink: ContractToCounterPartyLink)=>{this.onContractToCounterPartyLinkCreate(contractToCounterPartyLink)}}
        />
      )
    }

    @autobind
    onContractToCounterPartyLinkCreate(contractToCounterPartyLink: ContractToCounterPartyLink) {
      this.state.contractToCounterPartyLinks.push(contractToCounterPartyLink)
      this.modal.close()
      this.defineIfContractToCounterPartyLinkCanBeRemoved()
      this.forceUpdate()
    }

}
