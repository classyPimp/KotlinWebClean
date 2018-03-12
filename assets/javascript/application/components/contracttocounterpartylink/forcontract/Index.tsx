import { BaseReactComponent } from "../../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { ApplicationComponent } from '../../ApplicationComponent';
import { match, Link } from 'react-router-dom';
import { PlainInputElement } from '../../../../reactUtils/plugins/formable/formElements/PlainInput';
import { MixinFormableTrait } from '../../../../reactUtils/plugins/formable/MixinFormableTrait';
import autobind from 'autobind-decorator';
import { FlashMessageQueue } from '../../shared/FlashMessageQueue';
import { Modal } from '../../shared/Modal';
import { ContractToCounterPartyLinkComponents } from '../ContractToCounterPartyLinkComponents'
import { ContractToCounterPartyLink } from '../../../models/ContractToCounterPartyLink'
import { ModelCollection } from '../../../../modelLayer/ModelCollection'
import { DropDownSelectServerFed } from '../../formelements/DropDownSelectServerFed'

export class Index extends MixinFormableTrait(BaseReactComponent) {

    props: {
      match: match<any>
    } 

    modal: Modal

    state: {
      contractToCounterPartyLinks: ModelCollection<ContractToCounterPartyLink>
    } = {
      contractToCounterPartyLinks: new ModelCollection()
    }

    componentDidMount() {
      let contractId = this.props.match.params.contractId
      ContractToCounterPartyLink.forContractIndex({wilds: {contractId}}).then((contractToCounterPartyLinks)=>{
        this.setState({contractToCounterPartyLinks})
      })
    }

    render(){
        let contractToCounterPartyLinks = this.state.contractToCounterPartyLinks

        return <div className="persontocounterpartylinkreasons-Edit">
          {contractToCounterPartyLinks.map((it)=>{
            return <div key = {it.id}>
              <p>
                role: {it.roleAccordingToContract} 
              </p>
              <p>
                <Link to = {`/dashboards/counterParties/${it.counterParty.id}`}>
                  {`${it.counterParty.incorporationForm.name} ${it.counterParty.name}`}   
                </Link>
              </p>
            </div>
          })}
        </div>
    }


}
