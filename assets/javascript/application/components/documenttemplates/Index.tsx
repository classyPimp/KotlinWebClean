import autobind from 'autobind-decorator';
import { ModelCollection } from '../../../modelLayer/ModelCollection';
import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react';
import { DocumentTemplate } from '../../models/DocumentTemplate';
import { Link } from 'react-router-dom';

export class Index extends BaseReactComponent {

    state: {
        documentTemplates: ModelCollection<DocumentTemplate>
    } = {
        documentTemplates: new ModelCollection<DocumentTemplate>()
    }

    componentDidMount(){
        DocumentTemplate.index().then((documentTemplates: ModelCollection<DocumentTemplate>)=>{
            try {
            this.setState({
                documentTemplates

            })
            } catch(error) {
              console.log(error)
            }
        })
    }

    render(){
        return <div className="documentTemplates-Index">
            {this.state.documentTemplates.map((documentTemplate, index)=>{
                return <div key={documentTemplate.id}>
                    <p>
                      {documentTemplate.name}
                    </p>
                    {/*<Link to={"/dashboards/documentTemplates/" + documentTemplate.id}>
                        {documentTemplate.name} 
                        
                    </Link>
                    <Link to={`/dashboards/documentTemplates/${documentTemplate.id}/edit`}>
                        <button>
                            edit
                        </button>
                    </Link>
                    */}
                    <button onClick={()=>{this.deleteDocumentTemplate(documentTemplate)}}>
                        delete 
                    </button>
                </div>
            })}
        </div>
    }

    @autobind
    deleteDocumentTemplate(documentTemplate: DocumentTemplate){
        documentTemplate.destroy().then((returnedDocumentTemplate)=>{
            if (returnedDocumentTemplate.isValid()) {
                this.state.documentTemplates.filter((it)=>{
                    let res = (it !== documentTemplate)
                    return res
                })                
                this.setState({documentTemplates: this.state.documentTemplates})
            } else {
                alert(`could not be deleted`)
            }
        })
    }

}
