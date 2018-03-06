import autobind from 'autobind-decorator';
import { ModelCollection } from '../../../modelLayer/ModelCollection';
import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react';
import { Person } from '../../models/Person';
import { Router, Route, Link, match, Switch } from 'react-router-dom';
import { ContractComponents } from './ContractComponents'

export class SettingsMain extends BaseReactComponent {

    render(){
        return <div>
          <h3>
            settings
          </h3>
          <div className="pure-menu pure-menu-horizontal">
            <ul className="pure-menu-list">
              <li className="pure-menu-item">
                <Link to="/dashboards/contractCategories" className="pure-menu-link">
                  manage contract categories
                </Link>
              </li>
              <li className="pure-menu-item">
                <Link to="/dashboards/contractCategories/new" className="pure-menu-link">
                  create new contract category
                </Link>
              </li>
              <li className="pure-menu-item">
                <Link to="/dashboards/contractToUploadedDocumentLinkReasons" className="pure-menu-link">
                  manage contract to document link reasons
                </Link>
              </li>
              <li className="pure-menu-item">
                <Link to="/dashboards/contractToUploadedDocumentLinkReasons/new" className="pure-menu-link">
                  add new contract to document link reason
                </Link>
              </li>
            </ul>
          </div>
          <Switch>
              <Route exact path={`/dashboards/contracts`} component={ ContractComponents.Index }/>
              <Route exact path={`/dashboards/contracts/new`} component={ ContractComponents.New } />
          </Switch>
        </div>
    }

}
