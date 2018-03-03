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
import { match } from 'react-router-dom';
import { PersonToCounterPartyLinksComponents } from '../PersonToCounterPartyLinksComponents'
import { PersonToCounterPartyLinkToUploadedDocumentLink } from '../../../models/PersonToCounterPartyLinkToUploadedDocumentLink'
import { UploadedDocument } from '../../../models/UploadedDocument'
import { PlainFileInput } from '../../formelements/PlainFileInput'

export class New extends MixinFormableTrait(BaseReactComponent) {

    props: {
      match: match<any>
    }

    state: {
      personToCounterPartyLinkToUploadedDocumentLink: PersonToCounterPartyLinkToUploadedDocumentLink
    } = {
      personToCounterPartyLinkToUploadedDocumentLink: new PersonToCounterPartyLinkToUploadedDocumentLink(
        {
          uploadedDocument: new UploadedDocument()
        }
      )
    }

    render(){
        let uploadedDocument = this.state.personToCounterPartyLinkToUploadedDocumentLink.uploadedDocument
        return <div>
          <PlainFileInput
            registerInput={(it)=>{this.registerInput(it)}}
            model={uploadedDocument}
            propertyName={"file"}
          />
          <button onClick={this.submit}>
            submit
          </button>
        </div>
    }

    @autobind
    submit() {
      this.collectInputs()
      let personToCounterPartyLinkId = this.props.match.params.personToCounterPartyLinkId.toString()
      this.state.personToCounterPartyLinkToUploadedDocumentLink.validate()
      this.state.personToCounterPartyLinkToUploadedDocumentLink.create({wilds: {personToCounterPartyLinkId}}).then((personToCounterPartyLinkToUploadedDocumentLink)=>{
        if (personToCounterPartyLinkToUploadedDocumentLink.isValid()) {
          alert("success")
        } else {
          this.setState({personToCounterPartyLinkToUploadedDocumentLink})
        }
      })
    }



}
