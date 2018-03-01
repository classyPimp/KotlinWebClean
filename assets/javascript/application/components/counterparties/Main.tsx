import autobind from 'autobind-decorator';
import { ModelCollection } from '../../../modelLayer/ModelCollection';
import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react';
import { CounterParty } from '../../models/CounterParty';
import { Router, Route, Link, match, Switch } from 'react-router-dom';
import { CounterPartiesComponents } from './CounterPartiesComponents'

export class Main extends BaseReactComponent {

    render(){
        return <div>
          <h3>
            counter parties
          </h3>
          <div className="pure-menu pure-menu-horizontal">
            <ul className="pure-menu-list">
                <li className="pure-menu-item">
                  <Link to="/dashboards/counterParties" className="pure-menu-link">
                    search
                  </Link>
                </li>
                <li className="pure-menu-item">
                  <Link to="/dashboards/counterParties/new" className="pure-menu-link">
                    create new
                  </Link>
                </li>
                <li className="pure-menu-item">
                  <Link to="/dashboards/counterParties/settings" className="pure-menu-link">
                    settings
                  </Link>
                </li>
            </ul>
          </div>
          <Switch>
              <Route exact path={`/dashboards/counterParties`} component={ CounterPartiesComponents.Index }/>
              <Route exact path={`/dashboards/counterParties/new`} component={ CounterPartiesComponents.New } />
              <Route path="/dashboards/counterParties/:id" component={ CounterPartiesComponents.ShowMain } />
          </Switch>
        </div>
    }

}