import autobind from 'autobind-decorator';
import { ModelCollection } from '../../../modelLayer/ModelCollection';
import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react';
import { Person } from '../../models/Person';
import { Link } from 'react-router-dom';
import { PlainInputElement } from '../../../reactUtils/plugins/formable/formElements/PlainInput';
import { MixinFormableTrait } from '../../../reactUtils/plugins/formable/MixinFormableTrait';

export class Index extends MixinFormableTrait(BaseReactComponent) {

    state: {
      persons: ModelCollection<Person>
      formDummyPerson: Person
    } = {
      persons: new ModelCollection<Person>(),
      formDummyPerson: new Person()
    }

    // componentDidMount(){
    //   Person.index().then((persons: ModelCollection<Person>)=>{
    //     this.setState({persons})
    //   })
    // }

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
              <Link to={`/dashboards/persons/${person.id}/edit`}>
                  <button>
                      edit
                  </button>
              </Link>
              <button onClick={()=>{this.deletePerson(person)}}>
                  delete 
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

    @autobind
    deletePerson(person: Person){
      person.delete().then((returnedPerson)=>{
        if (returnedPerson.isValid()){
          this.state.persons.filter((it)=>{
            let res = (it !== person) 
            return res
          })
          this.setState({persons: this.state.persons})
        } else {
          alert("could not be deleted")
        }
      })
    }

}
