import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { ContractCategory } from '../../models/ContractCategory'
import { match } from 'react-router-dom'

export class Show extends BaseReactComponent {

    state: {
      contractCategory: ContractCategory
    } = {
      contractCategory: null
    }

    props: {
      match: match<any>
    }

    componentDidMount(){
      let id = this.props.match.params.id
      ContractCategory.show({wilds: {id: `${id}`}}).then((contractCategory)=>{
         this.setState({contractCategory})
      })
    }

    render(){
        return <div className="persontocounterpartylinkreasons-Show">
          {this.state.contractCategory &&
            <div>
              <h3>
                {
                  this.state.contractCategory.name
                }
              </h3>
              <p>
                {
                  this.state.contractCategory.description
                }
              </p>
            </div>
          }
        </div>
    }

}
