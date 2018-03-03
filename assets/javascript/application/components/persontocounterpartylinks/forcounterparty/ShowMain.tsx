import autobind from 'autobind-decorator';
import { ModelCollection } from '../../../../modelLayer/ModelCollection';
import { BaseReactComponent } from "../../../../reactUtils/BaseReactComponent"
import * as React from 'react';
import { CounterParty } from '../../../models/CounterParty';
import { Router, Route, Link, match, Switch } from 'react-router-dom';
import { PersonToCounterPartyLinksComponents } from '../PersonToCounterPartyLinksComponents'


export class ShowMain extends BaseReactComponent {

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
                  <Link to={`/dashboards/counterParties/${this.props.match.params.counterPartyId}/personToCounterPartyLinks/${this.props.match.params.personToCounterPartyLinkId}`} className="pure-menu-link">
                    general info
                  </Link>
                </li>
                <li className="pure-menu-item">
                  <Link to={`/dashboards/counterParties/${this.props.match.params.counterPartyId}/personToCounterPartyLinks/${this.props.match.params.personToCounterPartyLinkId}/uploadedDocuments`} className="pure-menu-link">
                    link related documents
                  </Link>
                </li>
                
                <li className="pure-menu-item">
                  <Link to={`/dashboards/counterParties/${this.props.match.params.counterPartyId}/personToCounterPartyLinks/${this.props.match.params.personToCounterPartyLinkId}/edit`} className="pure-menu-link">
                    edit
                  </Link>
                </li>
            </ul>
          </div>
          <Switch>
              <Route exact path={`/dashboards/counterParties/:counterPartyId/personToCounterPartyLinks/:personToCounterPartyLinkId`} component={ PersonToCounterPartyLinksComponents.forCounterParty.Show }/>
              <Route exact path={`/dashboards/counterParties/:counterPartyId/personToCounterPartyLinks/:personToCounterPartyLinkId/uploadedDocuments`} component={ PersonToCounterPartyLinksComponents.uploadedDocuments.Index }/>
              <Route exact path={`/dashboards/counterParties/:counterPartyId/personToCounterPartyLinks/:personToCounterPartyLinkId/uploadedDocuments/new`} component={ PersonToCounterPartyLinksComponents.uploadedDocuments.New }/>
              <Route path={`/dashboards/counterParties/:counterPartyId/personToCounterPartyLinks/:personToCounterPartyLinkId/edit`} component={ PersonToCounterPartyLinksComponents.forCounterParty.EditMain }/>
          </Switch>
        </div>
    }

}