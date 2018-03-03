import autobind from 'autobind-decorator';
import { ModelCollection } from '../../../../modelLayer/ModelCollection';
import { BaseReactComponent } from "../../../../reactUtils/BaseReactComponent"
import * as React from 'react';
import { CounterParty } from '../../../models/CounterParty';
import { Router, Route, Link, match, Switch } from 'react-router-dom';
import { PersonToCounterPartyLinksComponents } from '../PersonToCounterPartyLinksComponents'


export class EditMain extends BaseReactComponent {

    props: {
      match: match<any>
    }

    render() {
      return <div>
        <h3>
          link to person
        </h3>
        <div className="pure-menu pure-menu-horizontal">
          <ul className="pure-menu-list">
              <li className="pure-menu-item">
                <Link to={`/dashboards/counterParties/${this.props.match.params.counterPartyId}/personToCounterPartyLinks/${this.props.match.params.personToCounterPartyLinkId}/edit`} className="pure-menu-link">
                  edit general info
                </Link>
              </li>
              <li className="pure-menu-item">
                <Link to={`/dashboards/counterParties/${this.props.match.params.counterPartyId}/personToCounterPartyLinks/${this.props.match.params.personToCounterPartyLinkId}/edit/uploadedDocuments`} className="pure-menu-link">
                  edit related documents
                </Link>
              </li>
              <li className="pure-menu-item">
                <Link to={`/dashboards/counterParties/${this.props.match.params.counterPartyId}/personToCounterPartyLinks/${this.props.match.params.personToCounterPartyLinkId}/edit/uploadedDocuments/new`} className="pure-menu-link">
                  add new related document
                </Link>
              </li>
          </ul>
        </div>
        <Switch>
            <Route exact path={`/dashboards/counterParties/:counterPartyId/personToCounterPartyLinks/:personToCounterPartyLinkId/edit`} component={ PersonToCounterPartyLinksComponents.forCounterParty.Edit }/>
            <Route exact path={`/dashboards/counterParties/:counterPartyId/personToCounterPartyLinks/:personToCounterPartyLinkId/edit/uploadedDocuments`} component={ PersonToCounterPartyLinksComponents.uploadedDocuments.IndexEdit }/>
            <Route exact path={`/dashboards/counterParties/:counterPartyId/personToCounterPartyLinks/:personToCounterPartyLinkId/edit/uploadedDocuments/new`} component={ PersonToCounterPartyLinksComponents.uploadedDocuments.New }/>
        </Switch>
      </div>
    }

}