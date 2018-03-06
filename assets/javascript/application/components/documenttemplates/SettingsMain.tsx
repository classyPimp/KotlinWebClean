import autobind from 'autobind-decorator';
import { ModelCollection } from '../../../modelLayer/ModelCollection';
import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react';
import { Person } from '../../models/Person';
import { Router, Route, Link, match, Switch } from 'react-router-dom';
import { DocumentTemplateComponents } from './DocumentTemplateComponents'

export class SettingsMain extends BaseReactComponent {

    render(){
        return <div>
          <h3>
            document templates
          </h3>
          <div className="pure-menu pure-menu-horizontal">
            <ul className="pure-menu-list">
              <li className="pure-menu-item">
                <Link to="/dashboards/documentTemplateVariables" className="pure-menu-link">
                  manage template variables
                </Link>
              </li>
              <li className="pure-menu-item">
                <Link to="/dashboards/documentTemplateVariables/new" className="pure-menu-link">
                  add new template variable
                </Link>
              </li>
              <li className="pure-menu-item">
                <Link to="/dashboards/documentTemplateCategories" className="pure-menu-link">
                  manage document template categories
                </Link>
              </li>
              <li className="pure-menu-item">
                <Link to="/dashboards/documentTemplateCategories/new" className="pure-menu-link">
                  add new document template category
                </Link>
              </li>
            </ul>
          </div>
        </div>
    }

}
