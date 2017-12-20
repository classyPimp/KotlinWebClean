import autobind from 'autobind-decorator';
import { ModelCollection } from '../../../modelLayer/ModelCollection';
import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react';
import { Person } from '../../models/Person';
import { Link } from 'react-router-dom';

export class Index extends BaseReactComponent {

    state: {
      persons: ModelCollection<Person>
    } = {
      persons: new ModelCollection<Person>()
    }

    componentDidMount(){
      Person.index().then((persons: ModelCollection<Person>)=>{
        this.setState({persons})
      })
    }

    render(){
        return <div>
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
