import autobind from 'autobind-decorator';
import { ModelCollection } from '../../../modelLayer/ModelCollection';
import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react';
import { CounterParty } from '../../models/CounterParty';
import { Router, Route, Link, match, Switch } from 'react-router-dom';
import { CounterPartiesComponents } from './CounterPartiesComponents'
import { PersonToCounterPartyLinksComponents } from '../persontocounterpartylinks/PersonToCounterPartyLinksComponents'

export class ShowMain extends BaseReactComponent {

    props: {
      match: match<any>
    }

    render() {
        return <div>
          <h3>
            counter parties
          </h3>
          <div className="pure-menu pure-menu-horizontal">
            <ul className="pure-menu-list">
                <li className="pure-menu-item">
                  <Link to={`/dashboards/counterParties/${this.props.match.params.id}`} className="pure-menu-link">
                    general info
                  </Link>
                </li>
                <li className="pure-menu-item">
                  <Link to={`/dashboards/counterParties/${this.props.match.params.id}/contacts`} className="pure-menu-link">
                    contacts
                  </Link>
                </li>
                <li className="pure-menu-item">
                  <Link to={`/dashboards/counterParties/${this.props.match.params.id}/personToCounterPartyLinks`} className="pure-menu-link">
                    contacts
                  </Link>
                </li>
            </ul>
          </div>
          <Switch>
              <Route exact path={`/dashboards/counterParties/:id`} component={ CounterPartiesComponents.Show }/>
              <Route exact path={`/dashboards/counterParties/:id/contacts`} component={ CounterPartiesComponents.contacts.Index }/>
              <Route exact path={`/dashboards/counterParties/:id/personToCounterPartyLinks`} component={ PersonToCounterPartyLinksComponents.Index }/>
          </Switch>
        </div>
    }

}