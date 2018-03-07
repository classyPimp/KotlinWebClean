import { BaseReactComponent } from "../../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { CounterParty } from '../../../models/CounterParty';
import { PersonToCounterPartyLink } from '../../../models/PersonToCounterPartyLink'
import { ModelCollection } from '../../../../modelLayer/ModelCollection';
import autobind from 'autobind-decorator';
import { Person } from '../../../models/Person'
import { Link } from 'react-router-dom';
import { match } from 'react-router-dom';
import { PersonToCounterPartyLinksComponents } from '../PersonToCounterPartyLinksComponents'
import { PlainInputElement } from '../../../../reactUtils/plugins/formable/formElements/PlainInput';
import { MixinFormableTrait } from '../../../../reactUtils/plugins/formable/MixinFormableTrait';

export class Index extends MixinFormableTrait(BaseReactComponent) {

    props: {
      match: match<any>
    }

    state: {
      personToCounterPartyLinks: ModelCollection<PersonToCounterPartyLink>
      formDummy: CounterParty
    } = {
      personToCounterPartyLinks: new ModelCollection<PersonToCounterPartyLink>(),
      formDummy: new CounterParty()
    }

    componentDidMount(){  
      let personId = this.props.match.params.personId
      PersonToCounterPartyLink.indexForPerson({wilds: {personId}}).then((personToCounterPartyLinks)=>{
        this.setState({personToCounterPartyLinks})
      })
    }

    refresh(){
      console.log("should refresh")
      this.componentDidMount()
    }

    render(){
        return <div>
          <PlainInputElement
            model = {this.state.formDummy}
            propertyName = "name"
            registerInput = {(it)=>{this.registerInput(it)}}
            optional = {{
              placeholder: "company name"
            }}
          />
          <button>
            search
          </button>
          {this.state.personToCounterPartyLinks.map((it, index)=>{
            return <PersonToCounterPartyLinksComponents.forPerson.Show
              personToCounterPartyLink = {it}
              key = {it.id}
            />            
          })}
        </div>
    }

}
