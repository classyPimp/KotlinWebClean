import autobind from 'autobind-decorator';
import { ModelCollection } from '../../../modelLayer/ModelCollection';
import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react';
import { Person } from '../../models/Person';
import { Router, Route, Link, match, Switch } from 'react-router-dom';
import { DocumentTemplateComponents } from './DocumentTemplateComponents'

export class Main extends BaseReactComponent {

    render(){
        return <div>
          <h3>
            document templates
          </h3>
          <div className="pure-menu pure-menu-horizontal">
            <ul className="pure-menu-list">
              <li className="pure-menu-item">
                <Link to="/dashboards/documentTemplates" className="pure-menu-link">
                  search
                </Link>
              </li>
              <li className="pure-menu-item">
                <Link to="/dashboards/documentTemplates/new" className="pure-menu-link">
                  create new
                </Link>
              </li>
              <li className="pure-menu-item">
                <Link to="/dashboards/documentTemplates/settings" className="pure-menu-link">
                  settings
                </Link>
              </li>
            </ul>
          </div>
          <Switch>
              <Route exact path={`/dashboards/documentTemplates`} component={ DocumentTemplateComponents.Index }/>
              <Route exact path={`/dashboards/documentTemplates/new`} component={ DocumentTemplateComponents.New } />
              <Route exact path={`/dashboards/documentTemplates/settings`} component={ DocumentTemplateComponents.SettingsMain } />
          </Switch>
        </div>
    }

}
