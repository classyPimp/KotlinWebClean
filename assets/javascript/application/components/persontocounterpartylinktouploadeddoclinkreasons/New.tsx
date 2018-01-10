import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { PersonToCounterPartyLinkToUploadedDocLinkReason } from '../../models/PersonToCounterPartyLinkToUploadedDocLinkReason'
import { MixinFormableTrait } from '../../../reactUtils/plugins/formable/MixinFormableTrait';
import { PlainInputElement } from '../../../reactUtils/plugins/formable/formElements/PlainInput'
import autobind from 'autobind-decorator'
import { ErrorsShow } from '../shared/ErrorsShow'
import { ApplicationComponent } from '../ApplicationComponent';

export class New extends MixinFormableTrait(BaseReactComponent) {

    state: {
      personToCounterPartyLinkToUploadedDocLinkReason: PersonToCounterPartyLinkToUploadedDocLinkReason
    } = {
      personToCounterPartyLinkToUploadedDocLinkReason: new PersonToCounterPartyLinkToUploadedDocLinkReason()
    }

    render(){
        return <div className="PersonToCounterPartyLinkToUploadedDocLinkReasons-New">
          <h2>
            Create new link reason when assigning document to linking reason fo counter party
          </h2>
          {this.state.personToCounterPartyLinkToUploadedDocLinkReason.getErrorsFor('general') &&
              <ErrorsShow errors={this.state.personToCounterPartyLinkToUploadedDocLinkReason.getErrorsFor('general')}/>
          }
          <PlainInputElement
            model={this.state.personToCounterPartyLinkToUploadedDocLinkReason}
            propertyName="reasonName"
            registerInput={(it)=>{this.registerInput(it)}}
            optional={{placeholder: "name"}}
          />
          <button onClick={this.submit}>
            submit
          </button>
        </div>
    }

    @autobind
    submit(){
      this.collectInputs()
      
      let currentPersonToCounterPartyLinkToUploadedDocLinkReason = this.state.personToCounterPartyLinkToUploadedDocLinkReason
      currentPersonToCounterPartyLinkToUploadedDocLinkReason.validate()
      if (!currentPersonToCounterPartyLinkToUploadedDocLinkReason.isValid()) {
        this.setState({personToCounterPartyLinkToUploadedDocLinkReason: currentPersonToCounterPartyLinkToUploadedDocLinkReason})
        return
      }

      currentPersonToCounterPartyLinkToUploadedDocLinkReason.create().then((personToCounterPartyLinkToUploadedDocLinkReason)=>{
        if (!personToCounterPartyLinkToUploadedDocLinkReason.isValid()) {
              this.setState({personToCounterPartyLinkToUploadedDocLinkReason})
              return
        } 
        ApplicationComponent.instance.flashMessageQueue.addMessage(
          "link reason successfully created"
        )
        this.setState({personToCounterPartyLinkToUploadedDocLinkReason})
      })
      
    }

}
