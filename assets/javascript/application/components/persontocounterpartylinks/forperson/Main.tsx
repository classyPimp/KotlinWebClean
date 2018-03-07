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
                  <Link to={`/dashboards/persons/${this.props.match.params.id}/personToCounterPartyLinks`} className="pure-menu-link">
                    search
                  </Link>
                </li>
                <li className="pure-menu-item">
                  <Link to={`/dashboards/persons/${this.props.match.params.id}/personToCounterPartyLinks/new`} className="pure-menu-link">
                    add new counter party link
                  </Link>
                </li>
            </ul>
          </div>
          <Switch>
              <Route exact path={`/dashboards/persons/:personId/personToCounterPartyLinks`} component={ PersonToCounterPartyLinksComponents.forPerson.Index }/>
              <Route exact path={`/dashboards/persons/:personId/personToCounterPartyLinks/new`} component={ PersonToCounterPartyLinksComponents.forPerson.New }/>
          </Switch>
        </div>
    }
}