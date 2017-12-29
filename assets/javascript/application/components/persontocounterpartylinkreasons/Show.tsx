import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { PersonToCounterPartyLinkReason } from '../../models/PersonToCounterPartyLinkReason'
import { match } from 'react-router-dom'

export class Show extends BaseReactComponent {

    state: {
      linkReason: PersonToCounterPartyLinkReason
    } = {
      linkReason: null
    }

    props: {
      match: match<any>
    }

    componentDidMount(){
      let id = this.props.match.params.id
      PersonToCounterPartyLinkReason.show({wilds: {id: `${id}`}}).then((linkReason)=>{
         this.setState({linkReason})
      })
    }

    render(){
        return <div className="persontocounterpartylinkreasons-Show">
          {this.state.linkReason &&
            <h3>
              {
                this.state.linkReason.reasonName
              }
            </h3>
          }
        </div>
    }

}
