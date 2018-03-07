import { BaseReactComponent } from "../../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { CounterParty } from '../../../models/CounterParty';
import { PersonToCounterPartyLink } from '../../../models/PersonToCounterPartyLink'
import { ModelCollection } from '../../../../modelLayer/ModelCollection';
import autobind from 'autobind-decorator';
import { Person } from '../../../models/Person'
import { Link } from 'react-router-dom';
import { match } from 'react-router-dom';

export class Show extends BaseReactComponent {

    props: {
      personToCounterPartyLink: PersonToCounterPartyLink,
      match?: match<any>
    }

    render(){
      return <div>
        <p>
          linked to: 
          <Link to={`/dashboards/counterParties/${this.props.personToCounterPartyLink.counterParty.id}`}>
            {`${this.props.personToCounterPartyLink.counterParty.incorporationForm.name} ${this.props.personToCounterPartyLink.counterParty.name}`}
          </Link>
        </p>
        <p>
          link reason: {`${this.props.personToCounterPartyLink.personToCounterPartyLinkReason.reasonName}`}
        </p>
        {this.props.personToCounterPartyLink.personToCounterPartyLinkToUploadedDocumentLinks.isNotEmpty() &&
          <div>
            <p>
                link related documents:
            </p>
            {this.props.personToCounterPartyLink.personToCounterPartyLinkToUploadedDocumentLinks.map((personToCounterPartyLinkToUploadedDocumentLink)=>{
              return <div key = {personToCounterPartyLinkToUploadedDocumentLink.id}>
                {personToCounterPartyLinkToUploadedDocumentLink.personToCounterPartyLinkToUploadedDocLinkReason &&
                  <p>
                  reason name: {personToCounterPartyLinkToUploadedDocumentLink.personToCounterPartyLinkToUploadedDocLinkReason.reasonName}
                  </p>
                }
                <p>
                  file:  
                  <a href={personToCounterPartyLinkToUploadedDocumentLink.uploadedDocument.fileUrl()} className="pure-menu-link">
                    {personToCounterPartyLinkToUploadedDocumentLink.uploadedDocument.fileName}
                  </a>
                </p>
              </div>
            })}
          </div>
        }
        <Link to = {`/dashboards/counterParties/${this.props.personToCounterPartyLink.counterPartyId}/personToCounterPartyLinks/${this.props.personToCounterPartyLink.id}`}>
          details
        </Link>
      </div>
    }

}
