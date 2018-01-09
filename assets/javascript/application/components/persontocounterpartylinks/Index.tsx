import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { CounterParty } from '../../models/CounterParty';
import { PersonToCounterPartyLink } from '../../models/PersonToCounterPartyLink'
import { ModelCollection } from '../../../modelLayer/ModelCollection';
import autobind from 'autobind-decorator';
import { Person } from '../../models/Person'

export class Index extends BaseReactComponent {

    props: {
      editableMode: boolean 
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
        console.log("comp did mount")
        PersonToCounterPartyLink.indexForCounterParty({wilds: {counterPartyId: `${this.props.counterParty.id}`}}).then((personToCounterPartyLinks)=>{
          this.setState({personToCounterPartyLinks})
        })
      }
    }

    refresh(){
      console.log("should refresh")
      this.componentDidMount()
    }

    render(){
        return <div>
          {
            this.state.personToCounterPartyLinks.map((it, index)=>{
            
              {return <div key={index}>
                {this.props.counterParty 
                  ? <div>
                    <p>
                      linked to person: {it.person.name}
                    </p>
                    <p>
                      link reason: {it.personToCounterPartyLinkReason.reasonName}
                    </p>
                  </div>
                  : <div>
                    linked to counter party: {it.counterParty.name}
                    link reason: {it.personToCounterPartyLinkReason.reasonName}
                  </div>
                 }
                 {this.props.editableMode &&
                   <button onClick={()=>{this.destroy(it)}}>
                     delete
                   </button>
                 }
                </div>
              }
              
            })
          }
        </div>
    }

    @autobind
    destroy(personToCounterPartyLink: PersonToCounterPartyLink) {
      personToCounterPartyLink.destroy().then((returnedLink)=>{
        if (returnedLink.isValid()) {
          console.log('should filter')
          this.state.personToCounterPartyLinks.filter((it)=>{
            return (it !== personToCounterPartyLink)
          })
          this.setState({})
        }
      })
    }

}
