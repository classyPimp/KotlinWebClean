import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { Person } from '../../models/Person';
import { match } from 'react-router-dom';


export class Show extends BaseReactComponent {

    props: {
      match: match<any>,
      person?: Person
    }

    state: {
      person: Person
    } = {
      person: null
    }

    componentDidMount(){
      if (this.props.person) {
        return
      }

      let id = this.props.match.params['id']

      Person.get({wilds: {id}}).then((person)=>{
        this.setState({person})
      })
    }

    render(){
        return <div>
          {this.state.person &&
            <p>{this.state.person.name}</p>
          }
        </div>
    }

}
