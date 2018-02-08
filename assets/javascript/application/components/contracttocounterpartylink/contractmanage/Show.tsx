import { BaseReactComponent } from "../../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { ApplicationComponent } from '../../ApplicationComponent';
import { match } from 'react-router-dom';
import { PlainInputElement } from '../../../../reactUtils/plugins/formable/formElements/PlainInput';
import { MixinFormableTrait } from '../../../../reactUtils/plugins/formable/MixinFormableTrait';
import autobind from 'autobind-decorator';
import { FlashMessageQueue } from '../../shared/FlashMessageQueue';
import { Modal } from '../../shared/Modal';
import { ContractToCounterPartyLinkComponents } from '../ContractToCounterPartyLinkComponents'
import { ContractToCounterPartyLink } from '../../../models/ContractToCounterPartyLink'

import { DropDownSelectServerFed } from '../../formelements/DropDownSelectServerFed'

export class Show extends MixinFormableTrait(BaseReactComponent) {

    props: {
      contractToCounterPartyLink: ContractToCounterPartyLink
      canBeDeleted: boolean,
      onRemoved: (contractToCounterPartyLink: ContractToCounterPartyLink)=>any
      onReplaced: (contractToCounterPartyLink: ContractToCounterPartyLink)=>any
    } 

    modal: Modal

    render(){
        let contractToCounterPartyLink = this.props.contractToCounterPartyLink

        return <div className="persontocounterpartylinkreasons-Edit">
           <p>
             role: {contractToCounterPartyLink.roleAccordingToContract}
           </p>
           <p>
             {"${contractToCounterPartyLink.counterParty.IncorporationForm.name} ${contractToCounterPartyLink.counterParty.name}"}
           </p>
           {this.props.canBeDeleted &&
             <button>
               remove
             </button>
           }
           <button>
             replace
           </button>
        </div>
    }


}
