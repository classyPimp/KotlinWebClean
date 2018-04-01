import autobind from 'autobind-decorator';
import { ModelCollection } from '../../../modelLayer/ModelCollection';
import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react';
import { Person } from '../../models/Person';
import { Router, Route, Link, match, Switch } from 'react-router-dom';
import { PersonsComponents } from './PersonsComponents'
import { PersonToCounterPartyLinksComponents } from '../persontocounterpartylinks/PersonToCounterPartyLinksComponents'

export class SettingsMain extends BaseReactComponent {

    render(){
        return <div>
          <h3>
            settings
          </h3>
          <div className="pure-menu pure-menu-horizontal">
            <ul className="pure-menu-list">
                <li className="pure-menu-item">
                  <Link to={`/dashboards/contactTypes`} className="pure-menu-link">
                    edit contact types
                  </Link>
                </li>
                <li className="pure-menu-item">
                  <Link to={`/dashboards/contactTypes/new`} className="pure-menu-link">
                    create new contact types
                  </Link>
                </li>
                <li className="pure-menu-item">
                  <Link to={`/dashboards/persontocounterpartylinkreasons`} className="pure-menu-link">
                    edit person to counter party link reasons
                  </Link>
                </li>
                <li className="pure-menu-item">
                  <Link to={`/dashboards/persontocounterpartylinkreasons/new`} className="pure-menu-link">
                    create new person to counter party link reason
                  </Link>
                </li>
            </ul>
          </div>
        </div>
    }

}
