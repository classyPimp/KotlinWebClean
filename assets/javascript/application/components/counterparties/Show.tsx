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
import { CounterPartiesComponents } from './CounterPartiesComponents'

export class Show extends BaseReactComponent {

    props: {
        match: match<any>,
        counterParty?: CounterParty
    }

    state: {
        counterParty: CounterParty
    } = {
        counterParty: null,  
    }

    modal: Modal

    componentDidMount(){
        if (this.props.counterParty) {
            return
        }

        let id = this.props.match.params.id

        console.log(this.props.match)
        
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
              </div>    
            }
        </div>
    }



}
