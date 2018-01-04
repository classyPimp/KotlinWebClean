import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { CounterParty } from '../../models/CounterParty';
import { PersonToCounterPartyLink } from '../../models/PersonToCounterPartyLink'
import { ModelCollection } from '../../../modelLayer/ModelCollection';
import autobind from 'autobind-decorator';
import { Person } from '../../models/Person'

export class Index extends BaseReactComponent {

    props: {
      person?: Person
      counterParty?: CounterParty 
    }

    state: {
      personToCounterPartyLinks: ModelCollection<PersonToCounterPartyLink>
    } = {
      personToCounterPartyLinks: new ModelCollection<PersonToCounterPartyLink>()
    }

    componentDidMount(){
      if (this.props.counterParty) {
        PersonToCounterPartyLink.indexForCounterParty({wilds: {counterPartyId: `${this.props.counterParty.id}`}}).then((personToCounterPartyLinks)=>{
          this.setState({personToCounterPartyLinks})
        })
      }
    }

    render(){
        return <div>
          {
            this.state.personToCounterPartyLinks.map((it, index)=>{
            
              {this.props.counterParty 
                ? <div key={index}>
                  <p>
                    linked to person: {it.person.name}
                    link reason: {it.personToCounterPartyLinkReason.reasonName}
                  </p>
                </div>
                : <div key={index}>
                  linked to counter party: {it.counterParty.name}
                  link reason: {it.personToCounterPartyLinkReason.reasonName}
                </div>
              }
              
            })
          }
        </div>
    }

}
