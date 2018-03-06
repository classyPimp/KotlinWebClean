import { BaseReactComponent } from "../../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { ModelCollection } from '../../../../modelLayer/ModelCollection';
import autobind from 'autobind-decorator';
import { PersonToCounterPartyLinkToUploadedDocumentLink } from '../../../models/PersonToCounterPartyLinkToUploadedDocumentLink'
import {Modal} from '../../shared/Modal'
import { PersonToCounterPartyLinksComponents } from '../PersonToCounterPartyLinksComponents'
import { Router, Route, Link, match, Switch } from 'react-router-dom';


export class IndexEdit extends BaseReactComponent {

    props: {
      match: match<any>
    }  

    state: {
      personToCounterPartyLinkToUploadedDocumentLinks: ModelCollection<PersonToCounterPartyLinkToUploadedDocumentLink> 
    } = {
      personToCounterPartyLinkToUploadedDocumentLinks: new ModelCollection()
    }

    modal: Modal

    componentDidMount(){
      PersonToCounterPartyLinkToUploadedDocumentLink.index(
        {
          wilds: {personToCounterPartyLinkId: this.props.match.params.personToCounterPartyLinkId}
        }
      ).then((personToCounterPartyLinkToUploadedDocumentLinks)=>{
        this.setState({personToCounterPartyLinkToUploadedDocumentLinks})
      })
    }

    render(){
        return <div>
          <Modal ref={(it)=>{this.modal = it}}/>
          {this.state.personToCounterPartyLinkToUploadedDocumentLinks.map((it, index)=>{
              return <div key={it.id}>
                {it.personToCounterPartyLinkToUploadedDocLinkReason &&
                  <p>
                    reason: {it.personToCounterPartyLinkToUploadedDocLinkReason.reasonName}
                  </p>
                }
                <p>
                  file:  
                  <a href={it.uploadedDocument.fileUrl()} className="pure-menu-link">
                    {it.uploadedDocument.fileName}
                  </a>
                </p>
                <button onClick={()=>{this.delete(it)}}>
                  delete
                </button>
              </div>
          })}
          
        </div>
    }

    @autobind
    delete(link: PersonToCounterPartyLinkToUploadedDocumentLink) {
      link.destroy({wilds: {personToCounterPartyLinkId: this.props.match.params.personToCounterPartyLinkId}}).then((destroyedLink)=>{
        if (destroyedLink.isValid()) {
          this.state.personToCounterPartyLinkToUploadedDocumentLinks.filter((it)=>{
            return it !== link
          })
          this.setState({})
        }
      })
    }

}
