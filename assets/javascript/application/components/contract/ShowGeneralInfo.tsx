import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { Router, Route, Link, match, Switch } from 'react-router-dom';
import { ContractComponents } from './ContractComponents'
import { Contract } from '../../models/Contract'
import { TimeUtils } from '../../services/TimeUtils'

export class ShowGeneralInfo extends BaseReactComponent {

    props: {
      match: match<any>
      contract: Contract
    }

    // state: {
    //   contract: Contract
    // } = {
    //   contract: null
    // }

    // componentDidMount() {
    //   let contractId = this.props.match.params.contractId
    //   Contract.showGeneralInfo({wilds: {contractId}}).then((contract)=>{
    //     this.setState({contract})
    //   })
    // }

    render(){
        if (!this.props.contract) {
          return <div/>
        }

        return <div>
          <div>  
            <p>
              internal number: {this.props.contract.contractStatus.internalNumber}
            </p>
            <p>
              assigned number: {this.props.contract.contractStatus.assignedNumber}
            </p>
            <p>
              from date: {TimeUtils.timestampStringToReadableDateString(this.props.contract.contractStatus.formalDate)}
            </p>
          </div>

          <div>
            <p>
              status: 
              {this.props.contract.contractStatus.isProject && " is project" }
            </p>

          </div>
          <p>
            description: {this.props.contract.description}
          </p>
          <p>
            category: {this.props.contract.contractCategory.name}
          </p>
          <div>
            <p>
              contract parties:
            </p>
            {this.props.contract.contractToCounterPartyLinks.map((contractToCounterPartyLink)=>{
              return <p key={contractToCounterPartyLink.id}>
                {contractToCounterPartyLink.roleAccordingToContract}: {contractToCounterPartyLink.counterParty.incorporationForm.nameShort} {contractToCounterPartyLink.counterParty.name}
              </p>
            })}
          </div>
        </div>
    }

}
