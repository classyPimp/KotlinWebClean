import autobind from 'autobind-decorator';
import { ModelCollection } from '../../../modelLayer/ModelCollection';
import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react';
import { CounterParty } from '../../models/CounterParty';
import { Router, Route, Link, match, Switch } from 'react-router-dom';
import { CounterPartiesComponents } from './CounterPartiesComponents'
import { PersonToCounterPartyLinksComponents } from '../persontocounterpartylinks/PersonToCounterPartyLinksComponents'

export class SettingsMain extends BaseReactComponent {

    props: {
      match: match<any>
    }

    render() {
        return <div>
          <h3>
            settings
          </h3>
          <div className="pure-menu pure-menu-horizontal">
            <ul className="pure-menu-list">
                <li className="pure-menu-item">
                  <Link to={`/dashboards/incorporationForms`} className="pure-menu-link">
                    list incorporation forms
                  </Link>
                </li>
                <li className="pure-menu-item">
                  <Link to={`/dashboards/incorporationForms/new`} className="pure-menu-link">
                    add new incorporation form
                  </Link>
                </li>
                <li className="pure-menu-item">
                  <Link to={`/dashboards/persontocounterpartylinkreasons`} className="pure-menu-link">
                    list person to counter party link reasons
                  </Link>
                </li>
                <li className="pure-menu-item">
                  <Link to={`/dashboards/persontocounterpartylinkreasons`} className="pure-menu-link">
                    add new person to counter party link reason
                  </Link>
                </li>
                <li className="pure-menu-item">
                  <Link to={`/dashboards/PersonToCounterPartyLinkToUploadedDocLinkReasons`} className="pure-menu-link">
                    list person link to document link reasons
                  </Link>
                </li>
                <li className="pure-menu-item">
                  <Link to={`/dashboards/PersonToCounterPartyLinkToUploadedDocLinkReasons/new`} className="pure-menu-link">
                    add new person link to document link reason
                  </Link>
                </li>
            </ul>
          </div>
        </div>
    }

}