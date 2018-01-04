import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { CounterParty } from '../../models/CounterParty';
import { PersonToCounterPartyLink } from '../../models/PersonToCounterPartyLink';
import { match } from 'react-router-dom';
import autobind from 'autobind-decorator';
import { PersonToCounterPartyLinksComponents } from '../persontocounterpartylinks/PersonToCounterPartyLinksComponents'
import { Modal } from '../shared/Modal';
import { FlashMessageQueue } from '../shared/FlashMessageQueue';
import { ApplicationComponent } from '../ApplicationComponent';

export class Show extends BaseReactComponent {

    props: {
        match: match<any>,
        counterParty?: CounterParty
    }

    state: {
        counterParty: CounterParty
        linkedPersonsExpanded: boolean
    } = {
        counterParty: null,
        linkedPersonsExpanded: false
    }

    modal: Modal

    componentDidMount(){
        if (this.props.counterParty) {
            return
        }

        let id = this.props.match.params.id
        
        CounterParty.show({wilds: {id}}).then((counterParty)=>{
            this.setState({counterParty})
        })
    }


    render(){
        return <div>
            <Modal ref={(it)=>{this.modal = it}}/>
            {this.state.counterParty &&
              <div>
                  <p>name: {this.state.counterParty.name}</p>
                  <p> short name: {this.state.counterParty.nameShort} </p>                
                  <p>incorporation form: {this.state.counterParty.incorporationForm.nameShort}({this.state.counterParty.incorporationForm.name})</p>
                  <div>
                    <p>
                      <button onClick={this.initPersonToCounterPartyLinkAddition}>
                        add link to person
                      </button>
                    </p>
                    {!this.state.linkedPersonsExpanded
                      ? <div>
                         <button onClick={this.toggleExpandedLinkedPersons}> 
                           show linked persons
                         </button>
                      </div>
                      : <div>
                        <PersonToCounterPartyLinksComponents.Index counterParty={this.state.counterParty} />
                        <button onClick={this.toggleExpandedLinkedPersons}>
                          fold
                        </button>
                      </div>
                    }
                  </div>
              </div>
            }
        </div>
    }

    @autobind
    toggleExpandedLinkedPersons(){
      this.setState({linkedPersonsExpanded: !this.state.linkedPersonsExpanded})
    }

    @autobind
    initPersonToCounterPartyLinkAddition(){
      this.modal.open(
        <PersonToCounterPartyLinksComponents.New 
          counterParty={this.state.counterParty}
          onCreateSuccess={this.onPersonToCounterPartyLinkCreateSuccess}
          onCancel={this.onPersonToCounterPartyLinkCreateCancel}
        />
      )
    }

    @autobind
    onPersonToCounterPartyLinkCreateSuccess(personToCounterPartyLink: PersonToCounterPartyLink){
      this.state.counterParty.personToCounterPartyLinks.push(personToCounterPartyLink)
      this.modal.close()
      this.setState({})
      ApplicationComponent.instance.flashMessageQueue.addMessage(
        "link to person successfully created"
      )
    }

    @autobind
    onPersonToCounterPartyLinkCreateCancel(){
      this.modal.close()
    }

}
