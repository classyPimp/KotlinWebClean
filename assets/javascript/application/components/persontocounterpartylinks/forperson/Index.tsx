import { BaseReactComponent } from "../../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { CounterParty } from '../../../models/CounterParty';
import { PersonToCounterPartyLink } from '../../../models/PersonToCounterPartyLink'
import { ModelCollection } from '../../../../modelLayer/ModelCollection';
import autobind from 'autobind-decorator';
import { Person } from '../../../models/Person'
import { Link } from 'react-router-dom';
import { match } from 'react-router-dom';
import { PersonToCounterPartyLinksComponents } from '../PersonToCounterPartyLinksComponents'

export class Index extends BaseReactComponent {

    props: {
      match: match<any>
    }

    state: {
      personToCounterPartyLinks: ModelCollection<PersonToCounterPartyLink>
    } = {
      personToCounterPartyLinks: new ModelCollection<PersonToCounterPartyLink>()
    }

    componentDidMount(){  
      let personId = this.props.match.params.id
      PersonToCounterPartyLink.indexForPerson({wilds: {personId}}).then((personToCounterPartyLinks)=>{
        this.setState({personToCounterPartyLinks})
      })
    }

    refresh(){
      console.log("should refresh")
      this.componentDidMount()
    }

    render(){
        return <div>
          {this.state.personToCounterPartyLinks.map((it, index)=>{
            return <PersonToCounterPartyLinksComponents.forPerson.Show
              personToCounterPartyLink = {it}
            />            
          })}
        </div>
    }

}
