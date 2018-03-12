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

export class EditMain extends BaseReactComponent {

    props: {
      match: match<any>
    }

    render(){
        return <div>
          <h3>
            contract
          </h3>
          <div className="pure-menu pure-menu-horizontal">
            <ul className="pure-menu-list">
              <li className="pure-menu-item">
                <Link to={`/dashboards/contracts/${this.props.match.params.contractId}/edit`} className="pure-menu-link">
                  edit general info
                </Link>
              </li>
              <li className="pure-menu-item">
                <Link to={`/dashboards/contracts/${this.props.match.params.contractId}/edit/contractStatuses`} className="pure-menu-link">
                  edit statuses
                </Link>
              </li>
              <li className="pure-menu-item">
                <Link to={`/dashboards/contracts/${this.props.match.params.contractId}/edit/contractToCounterPartyLinks`} className="pure-menu-link">
                  edit parties
                </Link>
              </li>
              <li className="pure-menu-item">
                <Link to={`/dashboards/contracts/${this.props.match.params.contractId}/edit/contractToUploadedDocumentLinks`} className="pure-menu-link">
                  manage related documents
                </Link>
              </li>
            </ul>
          </div>
          <Switch>
              <Route exact path={`/dashboards/contracts/:contractId/edit`} component={ ContractComponents.EditGeneralInfo }/>
              <Route exact path={`/dashboards/contracts/:contractId/edit/contractStatuses`} component={ ContractStatusComponents.forContract.Edit }/>
              <Route exact path={`/dashboards/contracts/:contractId/edit/contractToUploadedDocumentLinks`} component={ ContractToUploadedDocumentLinkComponents.manage.Index }/>
              <Route exact path={`/dashboards/contracts/:contractId/edit/contractToCounterPartyLinks`} component={ ContractToCounterPartyLinkComponents.forContract.IndexEdit }/>
          </Switch>
        </div>
    }

}
