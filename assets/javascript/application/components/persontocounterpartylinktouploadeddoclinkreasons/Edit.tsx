import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { ApplicationComponent } from '../ApplicationComponent';
import { match } from 'react-router-dom';
import { PlainInputElement } from '../../../reactUtils/plugins/formable/formElements/PlainInput';
import { MixinFormableTrait } from '../../../reactUtils/plugins/formable/MixinFormableTrait';
import autobind from 'autobind-decorator';
import { FlashMessageQueue } from '../shared/FlashMessageQueue';
import { Modal } from '../shared/Modal';
import { PersonToCounterPartyLinkToUploadedDocLinkReasonsComponents } from './PersonToCounterPartyLinkToUploadedDocLinkReasonsComponents'
import { PersonToCounterPartyLinkToUploadedDocLinkReason } from '../../models/PersonToCounterPartyLinkToUploadedDocLinkReason'


export class Edit extends MixinFormableTrait(BaseReactComponent) {

    props: {
      match: match<any>
    }

    state: {
      linkReason: PersonToCounterPartyLinkToUploadedDocLinkReason
    } = {
      linkReason: null
    }

    modal: Modal

    componentDidMount(){
      let id = this.props.match.params.id
      PersonToCounterPartyLinkToUploadedDocLinkReason.edit({wilds: {id: `${id}`}}).then((linkReason)=>{
        this.setState({linkReason})
      })
    }

    render(){
        return <div className="persontocounterpartylinkreasons-Edit">
          {this.state.linkReason &&
            <div>
              <PlainInputElement 
                  model={this.state.linkReason}
                  propertyName="reasonName"
                  registerInput={(it)=>{this.registerInput(it)}}
                  optional={{
                    placeholder: "reason name"
                  }}
              />
              <button onClick={this.submit}>
                update
              </button>
            </div>
          }
        </div>
    }

    @autobind
    submit(){
      this.state.linkReason.update().then((linkReason)=>{
        if (linkReason.isValid()) {
          ApplicationComponent.instance.flashMessageQueue.addMessage(
            "link reason successfully updated"
          )
          this.state.linkReason.mergeWith(linkReason)
          this.setState({linkReason: this.state.linkReason})
        } else {
          this.state.linkReason.mergeWith(linkReason)
          this.setState({linkReason: this.state.linkReason})
          return
        }
      })
    }

}
