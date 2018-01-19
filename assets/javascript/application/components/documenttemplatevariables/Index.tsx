import autobind from 'autobind-decorator';
import { ModelCollection } from '../../../modelLayer/ModelCollection';
import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react';
import { DocumentTemplateVariable } from '../../models/DocumentTemplateVariable';
import { Link } from 'react-router-dom';

export class Index extends BaseReactComponent {

    state: {
        documentTemplateVariables: ModelCollection<DocumentTemplateVariable>
    } = {
        documentTemplateVariables: new ModelCollection<DocumentTemplateVariable>()
    }

    componentDidMount(){
        DocumentTemplateVariable.index().then((documentTemplateVariables: ModelCollection<DocumentTemplateVariable>)=>{
            this.setState({
                documentTemplateVariables
            })
        })
    }

    render(){
        return <div className="documentTemplateVariables-Index">
            {this.state.documentTemplateVariables.map((contactType, index)=>{
                return <div key={contactType.id}>
                    <Link to={"/dashboards/documentTemplateVariables/" + contactType.id}>
                        {contactType.name} 
                        
                    </Link>
                    <Link to={`/dashboards/documentTemplateVariables/${contactType.id}/edit`}>
                        <button>
                            edit
                        </button>
                    </Link>
                    <button onClick={()=>{this.deleteDocumentTemplateVariable(contactType)}}>
                        delete 
                    </button>
                </div>
            })}
        </div>
    }

    @autobind
    deleteDocumentTemplateVariable(contactType: DocumentTemplateVariable){
        contactType.destroy().then((returnedDocumentTemplateVariable)=>{
            if (returnedDocumentTemplateVariable.isValid()) {
                this.state.documentTemplateVariables.filter((it)=>{
                    let res = (it !== contactType)
                    return res
                })                
                this.setState({documentTemplateVariables: this.state.documentTemplateVariables})
            } else {
                alert(`could not be deleted`)
            }
        })
    }

}
