import autobind from 'autobind-decorator';
import { ModelCollection } from '../../../../modelLayer/ModelCollection';
import { BaseReactComponent } from "../../../../reactUtils/BaseReactComponent"
import * as React from 'react';
import { Person } from '../../../models/Person';
import { Router, Route, Link, match, Switch } from 'react-router-dom';
import { ApprovalComponents } from '../ApprovalComponents'

export class Main extends BaseReactComponent {

    props: {
      match: match<any>
    }

    render(){
        return <div>
          <h3>
            approval
          </h3>
          <div className="pure-menu pure-menu-horizontal">
            <ul className="pure-menu-list">
              <li className="pure-menu-item">
                <Link to={`/dashboards/contracts/${this.props.match.params.contractId}/approval/new`} className="pure-menu-link">
                  initialize
                </Link>
              </li>
            </ul>
          </div>
          <Switch>
              <Route path = { `/dashboards/contracts/:contractId/approval/new` } component = { ApprovalComponents.ofContract.New } />
          </Switch>
        </div>
    }

}
