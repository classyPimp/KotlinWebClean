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
import { PlainInputElement } from '../../../../reactUtils/plugins/formable/formElements/PlainInput'
import { MixinFormableTrait } from '../../../../reactUtils/plugins/formable/MixinFormableTrait';

export class IndexEdit extends MixinFormableTrait(BaseReactComponent) {

    props: {
      match: match<any>
    }

    state: {
      personToCounterPartyLinks: ModelCollection<PersonToCounterPartyLink>
      formDummy: Person
    } = {
      personToCounterPartyLinks: new ModelCollection<PersonToCounterPartyLink>(),
      formDummy: new Person()
    }

    modal: Modal

    componentDidMount(){
      
    }

    refresh(){
      this.componentDidMount()
    }

    render(){
      return <div>
        <Modal ref={(it)=>{this.modal = it}} />
        <PlainInputElement
          model = {this.state.formDummy}
          propertyName = "name"
          registerInput = {(it)=>{this.registerInput(it)}}
          optional = {{
            placeholder: "enter name"
          }}
        />
        <button onClick={this.search}>
          search
        </button>
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
    search() {
      this.collectInputs()
      let name = this.state.formDummy.name
      let counterPartyId = this.props.match.params.id 
      PersonToCounterPartyLink.forCounterPartyIndexEdit(
        {
          wilds: {counterPartyId: counterPartyId.toString()},
          params: {query: name}
        }
      ).then((personToCounterPartyLinks)=>{
        this.setState({personToCounterPartyLinks})
      })
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
      // this.modal.open(
      //   <PersonToCounterPartyLinksComponents.forCounterParty.New 
      //     counterPartyId = {this.props.match.params.id}
      //     onCreateSuccess = {this.onCreateSuccess} 
      //     onCancel = {this.modal.close}
      //   />
      // )
    }

    @autobind
    onCreateSuccess(personToCounterPartyLink: PersonToCounterPartyLink) {
      this.state.personToCounterPartyLinks.push(personToCounterPartyLink)
      this.forceUpdate()
    }

}
