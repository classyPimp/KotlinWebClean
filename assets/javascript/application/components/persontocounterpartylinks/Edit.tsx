import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { PersonToCounterPartyLink } from '../../models/PersonToCounterPartyLink'
import { CounterParty } from '../../models/CounterParty'
import { Person } from '../../models/Person'
import { PersonToCounterPartyLinkReason } from '../../models/PersonToCounterPartyLinkReason'
import { MixinFormableTrait } from '../../../reactUtils/plugins/formable/MixinFormableTrait';
import { PlainInputElement } from '../../../reactUtils/plugins/formable/formElements/PlainInput'
import autobind from 'autobind-decorator'
import { ErrorsShow } from '../shared/ErrorsShow'
import { ApplicationComponent } from '../ApplicationComponent';
import { DropDownSelectServerFed } from '../formelements/DropdownSelectServerFed'
import { match } from 'react-router-dom';
import { PersonToCounterPartyLinksComponents } from './PersonToCounterPartyLinksComponents'
import {Modal} from '../shared/Modal'
import { PersonToCounterPartyLinkToUploadedDocumentLink } from '../../models/PersonToCounterPartyLinkToUploadedDocumentLink'

export class Edit extends MixinFormableTrait(BaseReactComponent) {

    props: {
      match: match<any>
    } 

    state: {
      personToCounterPartyLink: PersonToCounterPartyLink
      documentsExpanded: boolean
    } = {
      personToCounterPartyLink: null,
      documentsExpanded: false
    }

    modal: Modal

    componentDidMount() {
      let id = this.props.match.params.id
      PersonToCounterPartyLink.edit({wilds: {id}}).then((personToCounterPartyLink)=>{
        this.setState({personToCounterPartyLink})
      })
    }

    render(){
        let personToCounterPartyLink = this.state.personToCounterPartyLink

        return personToCounterPartyLink
         ? <div className="persontocounterpartylinks-Edit">
          <Modal ref={(it)=>{this.modal = it}}/>
          <p>
            {personToCounterPartyLink.person.name} link to {personToCounterPartyLink.counterParty.incorporationForm.nameShort} {personToCounterPartyLink.counterParty.name}
          </p>
          {personToCounterPartyLink.getErrorsFor('general') &&
              <ErrorsShow errors={personToCounterPartyLink.getErrorsFor('general')}/>
          }
          <DropDownSelectServerFed 
              model={this.state.personToCounterPartyLink}
              propertyName="personToCounterPartyLinkReasonId"
              queryingFunction = { PersonToCounterPartyLinkReason.formFeedsIndex.bind(PersonToCounterPartyLinkReason) }
              propertyToSelect="id" 
              propertyToShow="reasonName"
              preselected={personToCounterPartyLink.personToCounterPartyLinkReason.id}
              registerInput={(it)=>{this.registerInput(it)}}
              optional={{placeholder: "link reason"}}
          />
          <PlainInputElement
            model={this.state.personToCounterPartyLink}
            propertyName="specificDetails"
            registerInput={(it)=>{this.registerInput(it)}}
            optional={{placeholder: "additional info"}}
          />
          <button onClick={this.submit}>
            submit
          </button>
          <div>
            <p>
              documents
            </p>
            {this.state.documentsExpanded
              ? <div>
                <PersonToCounterPartyLinksComponents.uploadedDocuments.Index 
                  personToCounterPartyLinkId={personToCounterPartyLink.id} 
                  editMode={true}
                />
                <p>
                  <button onClick={this.toggleDocumentsExpanded}>
                    fold
                  </button>
                </p>
              </div>
              : <button onClick={this.toggleDocumentsExpanded}>
                 show documents
             </button>
            }
          </div>
        </div>
        : <div className="persontocounterpartylinks-Edit">
          loading...
        </div>
    }

    @autobind
    toggleDocumentsExpanded() {
      let documentsExpanded = !this.state.documentsExpanded
      this.setState({documentsExpanded})
    }

    
    @autobind
    submit(){
      this.collectInputs()
      
      this.state.personToCounterPartyLink.validate()
      if (!this.state.personToCounterPartyLink.isValid()) {
        this.setState({})
        return
      }

      this.state.personToCounterPartyLink.create().then((personToCounterPartyLink)=>{
        if (!personToCounterPartyLink.isValid()) {
              this.setState({personToCounterPartyLink})
              return
        } 
        ApplicationComponent.instance.flashMessageQueue.addMessage(
          "link successfully created"
        )
        this.setState({personToCounterPartyLink})
        //this.props.onCreateSuccess(personToCounterPartyLink)
      })
      
    }

}
