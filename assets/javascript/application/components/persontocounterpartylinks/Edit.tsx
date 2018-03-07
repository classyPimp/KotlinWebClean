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
      personToCounterPartyLink: PersonToCounterPartyLink
      onDeleteSuccsess: (personToCounterPartyLink: PersonToCounterPartyLink) => void
    } 

    state: {
      documentsExpanded: boolean
    } = {
      documentsExpanded: false
    }

    modal: Modal

    componentDidMount() {
      
    }

    render(){
        let personToCounterPartyLink = this.props.personToCounterPartyLink

        return personToCounterPartyLink
         ? <div className="persontocounterpartylinks-Edit">
          <Modal ref={(it)=>{this.modal = it}}/>
          <p>
            {personToCounterPartyLink.person.name} 
          </p>
          {personToCounterPartyLink.getErrorsFor('general') &&
              <ErrorsShow errors={personToCounterPartyLink.getErrorsFor('general')}/>
          }
          <DropDownSelectServerFed 
              model={this.props.personToCounterPartyLink}
              propertyName="personToCounterPartyLinkReasonId"
              queryingFunction = { PersonToCounterPartyLinkReason.formFeedsIndex.bind(PersonToCounterPartyLinkReason) }
              propertyToSelect="id" 
              propertyToShow="reasonName"
              preselected={personToCounterPartyLink.personToCounterPartyLinkReason.id}
              registerInput={(it)=>{this.registerInput(it)}}
              optional={{placeholder: "link reason"}}
          />
          <PlainInputElement
            model={this.props.personToCounterPartyLink}
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
          <button onClick={this.destroy}>
            delete
          </button>

        </div>
        : <div className="persontocounterpartylinks-Edit">
          loading...
        </div>
    }

    @autobind
    destroy() {
      this.props.personToCounterPartyLink.destroy().then((returnedLink)=>{
        if (returnedLink.isValid()) {
          this.props.onDeleteSuccsess(this.props.personToCounterPartyLink)
        }
      })
    }

    @autobind
    toggleDocumentsExpanded() {
      let documentsExpanded = !this.state.documentsExpanded
      this.setState({documentsExpanded})
    }

    
    @autobind
    submit(){
      this.collectInputs()
      
      this.props.personToCounterPartyLink.validate()
      if (!this.props.personToCounterPartyLink.isValid()) {
        this.forceUpdate()
        return
      }

      this.props.personToCounterPartyLink.create().then((personToCounterPartyLink)=>{
        if (!personToCounterPartyLink.isValid()) {
              this.props.personToCounterPartyLink.properties = personToCounterPartyLink.properties
              this.props.personToCounterPartyLink.errors = personToCounterPartyLink.errors 
              this.forceUpdate()
              return
        } 
        ApplicationComponent.instance.flashMessageQueue.addMessage(
          "link successfully updated"
        )
        this.props.personToCounterPartyLink.properties = personToCounterPartyLink.properties
        this.forceUpdate()
      })
      
    }

}
