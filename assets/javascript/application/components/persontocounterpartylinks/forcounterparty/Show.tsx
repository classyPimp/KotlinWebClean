import { BaseReactComponent } from "../../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { PersonToCounterPartyLink } from '../../../models/PersonToCounterPartyLink'
import { CounterParty } from '../../../models/CounterParty'
import { Person } from '../../../models/Person'
import { PersonToCounterPartyLinkReason } from '../../../models/PersonToCounterPartyLinkReason'
import { MixinFormableTrait } from '../../../../reactUtils/plugins/formable/MixinFormableTrait';
import { PlainInputElement } from '../../../../reactUtils/plugins/formable/formElements/PlainInput'
import autobind from 'autobind-decorator'
import { ErrorsShow } from '../../shared/ErrorsShow'
import { ApplicationComponent } from '../../ApplicationComponent';
import { DropDownSelectServerFed } from '../../formelements/DropdownSelectServerFed'
import { Router, Route, Link, match, Switch } from 'react-router-dom';

export class Show extends MixinFormableTrait(BaseReactComponent) {

    props: {
      match: match<any>
    } 

    state: {
      personToCounterPartyLink: PersonToCounterPartyLink
    } = {
      personToCounterPartyLink: null
    }

    componentDidMount() {
      console.log(this.props.match.params)
      PersonToCounterPartyLink.showForCounterParty({
        wilds: {
          counterPartyId: this.props.match.params.counterPartyId,
          id: this.props.match.params.personToCounterPartyLinkId
        }
      }).then((personToCounterPartyLink)=>{
        this.setState({personToCounterPartyLink})
      })
    }    

    render(){
      if (!this.state.personToCounterPartyLink) {
        return <div/>
      }

      return <div className="persontocounterpartylinks-New">
        <p>
          <Link to = {`/dashboards/persons/${this.state.personToCounterPartyLink.person.id}`}>
            {this.state.personToCounterPartyLink.person.name}
          </Link>
        </p>
        <p>
          link reason: {this.state.personToCounterPartyLink.personToCounterPartyLinkReason.reasonName}
        </p>
        {this.state.personToCounterPartyLink.specificDetails &&
          <p>
            specific details: {this.state.personToCounterPartyLink.specificDetails}
          </p>
        }
      </div>
    }

}
