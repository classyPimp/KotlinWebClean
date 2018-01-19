import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { DocumentTemplateVariable } from '../../models/DocumentTemplateVariable';
import { match } from 'react-router-dom';

export class Show extends BaseReactComponent {

    props: {
        match: match<any>,
        documentTemplateVariable?: DocumentTemplateVariable
    }

    state: {
        documentTemplateVariable: DocumentTemplateVariable
    } = {
        documentTemplateVariable: null
    }

    componentDidMount(){
        if (this.props.documentTemplateVariable) {
            return
        }

        let id = this.props.match.params.id
        
        DocumentTemplateVariable.show({wilds: {id}}).then((documentTemplateVariable)=>{
            this.setState({documentTemplateVariable})
        })
    }

    render(){
        return <div>
            {this.state.documentTemplateVariable &&
              <div>
                <p>variable identifier: {this.state.documentTemplateVariable.name}</p>                
                <p>variable name: {this.state.documentTemplateVariable.humanizedName}</p>
                <p>variable default value: {this.state.documentTemplateVariable.defaultValue}</p>
              </div>
            }
        </div>
    }

}
