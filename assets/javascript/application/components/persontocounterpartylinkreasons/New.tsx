import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { PersonToCounterPartyLinkReason } from '../../models/PersonToCounterPartyLinkReason'
import { MixinFormableTrait } from '../../../reactUtils/plugins/formable/MixinFormableTrait';
import { PlainInputElement } from '../../../reactUtils/plugins/formable/formElements/PlainInput'
import autobind from 'autobind-decorator'
import { ErrorsShow } from '../shared/ErrorsShow'
import { ApplicationComponent } from '../ApplicationComponent';

export class New extends MixinFormableTrait(BaseReactComponent) {

    state: {
      personToCounterPartyLinkReason: PersonToCounterPartyLinkReason
    } = {
      personToCounterPartyLinkReason: new PersonToCounterPartyLinkReason()
    }

    render(){
        return <div className="persontocounterpartylinkreasons-New">
          <h2>
            Create new person to counter party link reason
          </h2>
          {this.state.personToCounterPartyLinkReason.getErrorsFor('general') &&
              <ErrorsShow errors={this.state.personToCounterPartyLinkReason.getErrorsFor('general')}/>
          }
          <PlainInputElement
            model={this.state.personToCounterPartyLinkReason}
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
      
      let currentPersonToCounterPartyLinkReason = this.state.personToCounterPartyLinkReason
      currentPersonToCounterPartyLinkReason.validate()
      if (!currentPersonToCounterPartyLinkReason.isValid()) {
        this.setState({personToCounterPartyLinkReason: currentPersonToCounterPartyLinkReason})
        return
      }

      currentPersonToCounterPartyLinkReason.create().then((personToCounterPartyLinkReason)=>{
        if (!personToCounterPartyLinkReason.isValid()) {
              this.setState({personToCounterPartyLinkReason})
              return
        } 
        ApplicationComponent.instance.flashMessageQueue.addMessage(
          "link reason successfully created"
        )
        this.setState({personToCounterPartyLinkReason})
      })
      
    }

}
