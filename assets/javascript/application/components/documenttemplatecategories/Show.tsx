import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { DocumentTemplateCategory } from '../../models/DocumentTemplateCategory'
import { match } from 'react-router-dom'

export class Show extends BaseReactComponent {

    state: {
      documentTemplateCategory: DocumentTemplateCategory
    } = {
      documentTemplateCategory: null
    }

    props: {
      match: match<any>
    }

    componentDidMount(){
      let id = this.props.match.params.id
      DocumentTemplateCategory.show({wilds: {id: `${id}`}}).then((documentTemplateCategory)=>{
         this.setState({documentTemplateCategory})
      })
    }

    render(){
        return <div className="persontocounterpartylinkreasons-Show">
          {this.state.documentTemplateCategory &&
            <div>
              <h3>
                {
                  this.state.documentTemplateCategory.name
                }
              </h3>
              <p>
                {
                  this.state.documentTemplateCategory.description
                }
              </p>
            </div>
          }
        </div>
    }

}
