import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { ModelCollection } from '../../../modelLayer/ModelCollection';
import { PersonToCounterPartyLinkToUploadedDocLinkReason } from '../../models/PersonToCounterPartyLinkToUploadedDocLinkReason'
import { Link } from 'react-router-dom';
import autobind from 'autobind-decorator';

export class Index extends BaseReactComponent {

    state: {
      linkReasons: ModelCollection<PersonToCounterPartyLinkToUploadedDocLinkReason>
    } = {
      linkReasons: new ModelCollection()
    }

    componentDidMount(){
      PersonToCounterPartyLinkToUploadedDocLinkReason.index().then((linkReasons: ModelCollection<PersonToCounterPartyLinkToUploadedDocLinkReason>)=>{
        this.setState({linkReasons})
      })
    }

    render(){
        return <div className="PersonToCounterPartyLinkToUploadedDocLinkReasons-Index">
          {
            this.state.linkReasons.map((linkReason, index)=>{
              return <div className="individual" key={index}>
                <p>
                  <Link to={`/dashboards/PersonToCounterPartyLinkToUploadedDocLinkReasons/${linkReason.id}`}>
                    {
                      linkReason.reasonName
                    }
                  </Link>
                </p>
                <Link to={`/dashboards/PersonToCounterPartyLinkToUploadedDocLinkReasons/${linkReason.id}`}>
                  <button>
                    show
                  </button>
                </Link>
                <Link to={`/dashboards/PersonToCounterPartyLinkToUploadedDocLinkReasons/${linkReason.id}/edit`}>
                  <button>
                    edit
                  </button>
                </Link>
                <button onClick={()=>{this.destroyLinkReason(linkReason)}}>
                  delete
                </button>
              </div>
            })
          }
        </div>
    }

    @autobind
    destroyLinkReason(linkReason: PersonToCounterPartyLinkToUploadedDocLinkReason) {
        linkReason.destroy().then((returnedLinkReason)=>{
          if (returnedLinkReason.isValid()) {
            this.state.linkReasons.filter((it)=>{
              return it !== linkReason
            })
            this.setState({linkReasons: this.state.linkReasons})
          } else {
            alert("could not be deleted")
          }
        }) 
    }

}
