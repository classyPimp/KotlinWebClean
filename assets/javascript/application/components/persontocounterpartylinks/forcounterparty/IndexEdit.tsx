import { BaseReactComponent } from "../../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { CounterParty } from '../../../models/CounterParty';
import { PersonToCounterPartyLink } from '../../../models/PersonToCounterPartyLink'
import { ModelCollection } from '../../../../modelLayer/ModelCollection';
import autobind from 'autobind-decorator';
import { Person } from '../../../models/Person'
import {Modal} from '../../shared/Modal'
import { Router, Route, Link, match, Switch } from 'react-router-dom';
import { PersonToCounterPartyLinksComponents } from '../PersonToCounterPartyLinksComponents'

export class IndexEdit extends BaseReactComponent {

    props: {
      match: match<any>
    }

    state: {
      personToCounterPartyLinks: ModelCollection<PersonToCounterPartyLink>
    } = {
      personToCounterPartyLinks: new ModelCollection<PersonToCounterPartyLink>()
    }

    modal: Modal

    componentDidMount(){
      let counterPartyId = this.props.match.params.id 
      PersonToCounterPartyLink.forCounterPartyIndexEdit({wilds: {counterPartyId: counterPartyId.toString()}}).then((personToCounterPartyLinks)=>{
        this.setState({personToCounterPartyLinks})
      })
    }

    refresh(){
      this.componentDidMount()
    }

    render(){
      return <div>
        <Modal ref={(it)=>{this.modal = it}} />
        {this.state.personToCounterPartyLinks.map((it, index)=>{
          return <PersonToCounterPartyLinksComponents.Edit
            personToCounterPartyLink = {it}
            key = {it.id}
            onDeleteSuccsess = {this.onDeleteSuccsess}
          />  
        })}
        <button onClick = {this.initializeNew}>
          create new link
        </button>        
      </div>
    }

    @autobind
    onDeleteSuccsess(personToCounterPartyLink: PersonToCounterPartyLink) {
      this.state.personToCounterPartyLinks.filter((it)=>{
        return it != personToCounterPartyLink
      })
      this.forceUpdate()
    }

    @autobind
    initializeNew() {
      this.modal.open(
        <PersonToCounterPartyLinksComponents.forCounterParty.New 
          counterPartyId = {this.props.match.params.id}
          onCreateSuccess = {this.onCreateSuccess} 
          onCancel = {this.modal.close}
        />
      )
    }

    @autobind
    onCreateSuccess(personToCounterPartyLink: PersonToCounterPartyLink) {
      this.state.personToCounterPartyLinks.push(personToCounterPartyLink)
      this.forceUpdate()
    }

}
