import autobind from 'autobind-decorator';
import { ModelCollection } from '../../../modelLayer/ModelCollection';
import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react';
import { Person } from '../../models/Person';
import { Router, Route, Link, match, Switch } from 'react-router-dom';
import { ContractComponents } from './ContractComponents'
import { ContractStatusComponents } from '../contractstatus/ContractStatusComponents'
import { ContractToCounterPartyLinkComponents } from '../contracttocounterpartylink/ContractToCounterPartyLinkComponents'
import { ContractToUploadedDocumentLinkComponents } from '../contracttouploadeddocumentlink/ContractToUploadedDocumentLinkComponents'
import { ApprovalComponents } from '../approval/ApprovalComponents'
import { Contract } from '../../models/Contract'


export class ShowMain extends BaseReactComponent {

    props: {
      match: match<any>
    }

    state: {
      contractWithGeneralInfo: Contract
    } = {
      contractWithGeneralInfo: null
    }

    componentDidMount() {
      let contractId = this.props.match.params.contractId
      Contract.showGeneralInfo({wilds: {contractId}}).then((contractWithGeneralInfo)=>{
        this.setState({contractWithGeneralInfo})
      })
    }

    componentWillReceiveProps(nextProps: any) {
      let currentContractId = this.props.match.params.contractId
      let nextContractId = nextProps.match.params.contractId
      if (currentContractId != nextContractId) {
        this.componentDidMount()
      }
    }

    render(){
        return <div>
          <h3>
            {this.state.contractWithGeneralInfo
              ? <span>#{this.state.contractWithGeneralInfo.contractStatus.internalNumber}</span>
              : <span>...loading</span>
            }
          </h3>
          <div className="pure-menu pure-menu-horizontal">
            <ul className="pure-menu-list">
              <li className="pure-menu-item">
                <Link to={`/dashboards/contracts/${this.props.match.params.contractId}`} className="pure-menu-link">
                  general info
                </Link>
              </li>
              <li className="pure-menu-item">
                <Link to={`/dashboards/contracts/${this.props.match.params.contractId}/approval`} className="pure-menu-link">
                  approval
                </Link>
              </li>
              <li className="pure-menu-item">
                <Link to={`/dashboards/contracts/${this.props.match.params.contractId}/status`} className="pure-menu-link">
                  status
                </Link>
              </li>
              <li className="pure-menu-item">
                <Link to={`/dashboards/contracts/${this.props.match.params.contractId}/contractToCounterPartyLinks`} className="pure-menu-link">
                  parties
                </Link>
              </li>
              <li className="pure-menu-item">
                <Link to={`/dashboards/contracts/${this.props.match.params.contractId}/contractToUploadedDocumentLinks`} className="pure-menu-link">
                  related files
                </Link>
              </li>
              <li className="pure-menu-item">
                <Link to={`/dashboards/contracts/${this.props.match.params.contractId}/edit`} className="pure-menu-link">
                  edit
                </Link>
              </li>
            </ul>
          </div>
          <Switch>
              <Route exact path={`/dashboards/contracts/:contractId`} component={ this.getContractGeneralInfo } />
              <Route exact path={`/dashboards/contracts/:contractId/status`} component={ ContractStatusComponents.ShowMain }/>
              <Route exact path={`/dashboards/contracts/:contractId/contractToCounterPartyLinks`} component = { ContractToCounterPartyLinkComponents.forContract.Index } />
              <Route exact path={`/dashboards/contracts/:contractId/contractToUploadedDocumentLinks`} component = { ContractToUploadedDocumentLinkComponents.forContract.Index } />
              <Route path = {`/dashboards/contracts/:contractId/edit`} component = { ContractComponents.EditMain } />
              <Route path = "/dashboards/contracts/:contractId/approval" component = { ApprovalComponents.ofContract.Main } />
          </Switch>
        </div>
    }

    @autobind
    getContractGeneralInfo(props: any): any {
        return <ContractComponents.ShowGeneralInfo 
          contract = {this.state.contractWithGeneralInfo}
          {...props}
        />
    }

}
