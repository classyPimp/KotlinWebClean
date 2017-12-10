import { User } from '../models/User';
import autobind from 'autobind-decorator';
import { PlainInputElement } from '../../reactUtils/plugins/formable/formElements/PlainInput';

import { BaseReactComponent } from '../../reactUtils/BaseReactComponent';

import * as React from 'react';
import { MixinFormableTrait } from '../../reactUtils/plugins/formable/MixinFormableTrait';
import { Modal } from './shared/Modal';
import { ApplicationComponent } from './ApplicationComponent';

export class DemoComponent extends MixinFormableTrait(BaseReactComponent) {

    state: {
        modal: Modal
    }= {
        modal: null
    }

    render(){
        return (
            <div>
                <Modal ref={(it)=>{this.state.modal = it}}/>                
                <button onClick={this.openModal}>
                    open modal
                </button>
                <button onClick={this.addFlash}>
                    add flash message
                </button>
            </div>
        )
    }

    submodal: Modal = null

    messageCounter = -1

    @autobind
    addFlash(){
        this.messageCounter  += 1
        ApplicationComponent.instance.flashMessageQueue.addMessage(
            <p>
                flash message {this.messageCounter}
            </p>
        )
    }

    @autobind
    openModal(){
        this.state.modal.open(
            <div>
            Why do we use it?
            </div>
        )
    }


}