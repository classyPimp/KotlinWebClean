import autobind from 'autobind-decorator';
import { ModelCollection } from '../../../modelLayer/ModelCollection';
import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react';
import { Person } from '../../models/Person';
import { Link } from 'react-router-dom';
import { PlainInputElement } from '../../../reactUtils/plugins/formable/formElements/PlainInput';
import { MixinFormableTrait } from '../../../reactUtils/plugins/formable/MixinFormableTrait';

export class FormSelectIndex extends MixinFormableTrait(BaseReactComponent) {

    props: {
      onSelected: (selectedModel: Person) => any
    }

    state: {
      persons: ModelCollection<Person>
      formDummyPerson: Person
    } = {
      persons: new ModelCollection<Person>(),
      formDummyPerson: new Person()
    }

    render(){
        return <div>
          <div className="pure-form">
            <PlainInputElement
              model={this.state.formDummyPerson}
              propertyName="name"
              registerInput = {(it)=>{this.registerInput(it)}}
              optional ={{
                placeholder: "name to search"
              }}
            />
            <button onClick={this.search} className="pure-button pure-button-primary">
              search
            </button>
          </div>
          {this.state.persons.map((person, index)=>{
            return <div key={person.id}>
              <Link to={"/dashboards/persons/" + person.id}>
                  {person.name} 
                  
              </Link>
              <button onClick={()=>{this.props.onSelected(person)}}>
                  select 
              </button>
            </div>
          })

          }
        </div>
    }

    @autobind
    search() {
      this.collectInputs()
      let query = this.state.formDummyPerson.name
      Person.index({params: {query}}).then((persons)=>{
        this.setState({persons})
      })
    }


}
