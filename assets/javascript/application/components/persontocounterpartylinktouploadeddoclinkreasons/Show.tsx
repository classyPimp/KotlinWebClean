import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { PersonToCounterPartyLinkToUploadedDocLinkReason } from '../../models/PersonToCounterPartyLinkToUploadedDocLinkReason'
import { match } from 'react-router-dom'

export class Show extends BaseReactComponent {

    state: {
      linkReason: PersonToCounterPartyLinkToUploadedDocLinkReason
    } = {
      linkReason: null
    }

    props: {
      match: match<any>
    }

    componentDidMount(){
      let id = this.props.match.params.id
      PersonToCounterPartyLinkToUploadedDocLinkReason.show({wilds: {id: `${id}`}}).then((linkReason)=>{
         this.setState({linkReason})
      })
    }

    render(){
        return <div className="PersonToCounterPartyLinkToUploadedDocLinkReasons-Show">
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
