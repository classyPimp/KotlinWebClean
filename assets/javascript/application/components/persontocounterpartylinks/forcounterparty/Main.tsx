import autobind from 'autobind-decorator';
import { ModelCollection } from '../../../../modelLayer/ModelCollection';
import { BaseReactComponent } from "../../../../reactUtils/BaseReactComponent"
import * as React from 'react';
import { CounterParty } from '../../../models/CounterParty';
import { Router, Route, Link, match, Switch } from 'react-router-dom';
import { PersonToCounterPartyLinksComponents } from '../PersonToCounterPartyLinksComponents'

export class Main extends BaseReactComponent {

    props: {
      match: match<any>
    }

    render() {
        return <div>
          <h3>
            links to persons
          </h3>
          <div className="pure-menu pure-menu-horizontal">
            <ul className="pure-menu-list">
                <li className="pure-menu-item">
                  <Link to={`/dashboards/counterParties/${this.props.match.params.id}/personToCounterPartyLinks`} className="pure-menu-link">
                    search
                  </Link>
                </li>
                <li className="pure-menu-item">
                  <Link to={`/dashboards/counterParties/${this.props.match.params.id}/personToCounterPartyLinks/new`} className="pure-menu-link">
                    add new person to counter party link
                  </Link>
                </li>
            </ul>
          </div>
          <Switch>
              <Route exact path={`/dashboards/counterParties/:counterPartyId/personToCounterPartyLinks`} component={ PersonToCounterPartyLinksComponents.forCounterParty.Index }/>
              <Route exact path={`/dashboards/counterParties/:counterPartyId/personToCounterPartyLinks/new`} component={ PersonToCounterPartyLinksComponents.forCounterParty.New }/>
              <Route path={`/dashboards/counterParties/:counterPartyId/personToCounterPartyLinks/:personToCounterPartyLinkId`} component={ PersonToCounterPartyLinksComponents.forCounterParty.ShowMain }/>
          </Switch>
        </div>
    }

}