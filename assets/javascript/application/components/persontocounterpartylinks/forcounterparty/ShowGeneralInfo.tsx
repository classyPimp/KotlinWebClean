import autobind from 'autobind-decorator';
import { ModelCollection } from '../../../../modelLayer/ModelCollection';
import { BaseReactComponent } from "../../../../reactUtils/BaseReactComponent"
import * as React from 'react';
import { CounterParty } from '../../../models/CounterParty';
import { Router, Route, Link, match, Switch } from 'react-router-dom';
import { PersonToCounterPartyLinksComponents } from '../PersonToCounterPartyLinksComponents'
import { PersonToCounterPartyLink } from "../../../models/PersonToCounterPartyLink"

export class ShowGeneralInfo extends BaseReactComponent {

    props: {
      match: match<any>
    }

    state: {
      personToCounterPartyLink: PersonToCounterPartyLink
    }

    componentDidMount() {
      let counterPartyId = this.props.match.params.id.toString()
      PersonToCounterPartyLink.showForCounterParty({wilds: {counterPartyId}}).then((personToCounterPartyLink)=>{
        this.setState({personToCounterPartyLink})
      })
    }

    render() {
        return <div>
          {this.state.personToCounterPartyLink &&
            <div>
              <p>
                <Link to = {`/dashboards/persons/${this.state.personToCounterPartyLink.person.id}`}>
                  {this.state.personToCounterPartyLink.person.name}
                </Link>
              </p>
              <p>
                link reason: {this.state.personToCounterPartyLink.personToCounterPartyLinkReason.reasonName}
              </p>
            </div>
          }
        </div>
    }

}