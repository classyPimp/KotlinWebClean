import autobind from 'autobind-decorator';
import { ModelCollection } from '../../../modelLayer/ModelCollection';
import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react';
import { Person } from '../../models/Person';
import { Router, Route, Link, match, Switch } from 'react-router-dom';
import { PersonsComponents } from './PersonsComponents'

export class Main extends BaseReactComponent {

    render(){
        return <div>
          <h3>
            persons
          </h3>
          <div className="pure-menu pure-menu-horizontal">
            <ul className="pure-menu-list">
                <li className="pure-menu-item">
                  <Link to="/dashboards/persons/new" className="pure-menu-link">
                    create new
                  </Link>
                </li>
                <li className="pure-menu-item">
                  <Link to="/dashboards/persons" className="pure-menu-link">
                    search
                  </Link>
                </li>
                <li className="pure-menu-item">
                  <Link to="/dashboards/persons/settings" className="pure-menu-link">
                    settings
                  </Link>
                </li>
            </ul>
          </div>
          <Switch>
              <Route exact path={`/dashboards/persons`} component={ PersonsComponents.Index }/>
              <Route exact path={`/dashboards/persons/new`} component={ PersonsComponents.New } />
              <Route path="/dashboards/persons/settings" component={ PersonsComponents.SettingsMain } />
              <Route path="/dashboards/persons/:id" component={ PersonsComponents.ShowMain } />
          </Switch>
        </div>
    }

}
