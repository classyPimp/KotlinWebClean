import autobind from 'autobind-decorator';
import { ModelCollection } from '../../../modelLayer/ModelCollection';
import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react';
import { Person } from '../../models/Person';
import { Router, Route, Link, match, Switch } from 'react-router-dom';
import { ContractComponents } from './ContractComponents'

export class Main extends BaseReactComponent {

    render(){
        return <div>
          <h3>
            contracts
          </h3>
          <div className="pure-menu pure-menu-horizontal">
            <ul className="pure-menu-list">
              <li className="pure-menu-item">
                <Link to="/dashboards/contracts" className="pure-menu-link">
                  search
                </Link>
              </li>
              <li className="pure-menu-item">
                <Link to="/dashboards/contracts/new" className="pure-menu-link">
                  create new
                </Link>
              </li>
              <li className="pure-menu-item">
                <Link to="/dashboards/contracts/settings" className="pure-menu-link">
                  settings
                </Link>
              </li>
            </ul>
          </div>
          <Switch>
              <Route exact path={`/dashboards/contracts`} component={ ContractComponents.Index }/>
              <Route exact path={`/dashboards/contracts/new`} component={ ContractComponents.New } />
              <Route exact path={`/dashboards/contracts/settings`} component={ ContractComponents.SettingsMain } />
              <Route path = { `/dashboards/contracts/:contractId` } component = { ContractComponents.ShowMain } />
          </Switch>
        </div>
    }

}
