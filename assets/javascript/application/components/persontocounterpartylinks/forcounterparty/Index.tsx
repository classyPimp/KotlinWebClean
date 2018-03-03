import { BaseReactComponent } from "../../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { CounterParty } from '../../../models/CounterParty';
import { PersonToCounterPartyLink } from '../../../models/PersonToCounterPartyLink'
import { ModelCollection } from '../../../../modelLayer/ModelCollection';
import autobind from 'autobind-decorator';
import { Person } from '../../../models/Person'
import { Router, Route, Link, match, Switch } from 'react-router-dom';
import { PlainInputElement } from '../../../../reactUtils/plugins/formable/formElements/PlainInput';
import { MixinFormableTrait } from '../../../../reactUtils/plugins/formable/MixinFormableTrait';

export class Index extends MixinFormableTrait(BaseReactComponent) {

    props: {
      match: match<any>
    }

    state: {
      personToCounterPartyLinks: ModelCollection<PersonToCounterPartyLink>
      formDummyPersonToCounterPartyLink: PersonToCounterPartyLink
    } = {
      personToCounterPartyLinks: new ModelCollection<PersonToCounterPartyLink>(),
      formDummyPersonToCounterPartyLink: new PersonToCounterPartyLink()
    }

    componentWillMount() {
      console.log(this.props.match.params)
    }

    render(){
      return <div>
        <div className="pure-form">
            <PlainInputElement
              model={this.state.formDummyPersonToCounterPartyLink}
              propertyName="name"
              registerInput = {(it)=>{this.registerInput(it)}}
              optional ={{
                placeholder: "name to search"
              }}
            />
            <button onClick={this.search} className="pure-button pure-button-primary">
              search
            </button>
          </div>
        {this.state.personToCounterPartyLinks.map((it, index)=>{
          return <div key={index}>
            <p>
              <Link to = {`/dashboards/persons/${it.person.id}`}>
                {it.person.name}
              </Link>
            </p>
            <p>
              link reason: {it.personToCounterPartyLinkReason.reasonName}
            </p>
            <p>
              <Link to = {`/dashboards/counterParties/${this.props.match.params.counterPartyId}/personToCounterPartyLinks/${it.id}`}>
                details
              </Link>
            </p>
            {/*{it.personToCounterPartyLinkToUploadedDocumentLinks.map((personToCounterPartyLinkToUploadedDocumentLink)=>{
              return <div key={personToCounterPartyLinkToUploadedDocumentLink.id}>
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
            })}*/}        
          </div>  
        })}
      </div>
    }

    @autobind
    search() {
      this.collectInputs()
      let query = this.state.formDummyPersonToCounterPartyLink.properties["name"]
      PersonToCounterPartyLink.indexForCounterParty({
        wilds: {counterPartyId: this.props.match.params.counterPartyId.toString()},
        params: {query}
      }).then((personToCounterPartyLinks)=>{
        this.setState({personToCounterPartyLinks})
      })
    }

}
