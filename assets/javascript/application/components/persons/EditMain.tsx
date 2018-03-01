import autobind from 'autobind-decorator';
import { ModelCollection } from '../../../modelLayer/ModelCollection';
import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react';
import { Person } from '../../models/Person';
import { Router, Route, Link, match, Switch } from 'react-router-dom';
import { PersonsComponents } from './PersonsComponents'

export class EditMain extends BaseReactComponent {

    render(){
        return <div>
          <h3>
            edit
          </h3>
          <div className="pure-menu pure-menu-horizontal">
            <ul className="pure-menu-list">
                <li className="pure-menu-item">
                  <Link to={`/dashboards/persons/${this.props.match.params.id}/edit`} className="pure-menu-link">
                    edit general info
                  </Link>
                </li>
                <li className="pure-menu-item">
                  <Link to={`/dashboards/persons/${this.props.match.params.id}/edit/contacts`} className="pure-menu-link">
                    edit contacts
                  </Link>
                </li>
            </ul>
          </div>
          <Switch>
              <Route exact path={`/dashboards/persons/:id/edit`} component={ PersonsComponents.Edit }/>
              <Route exact path={`/dashboards/persons/:id/edit/contacts`} component={ PersonsComponents.contacts.IndexEdit }/>
          </Switch>
        </div>
    }

}
