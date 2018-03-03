import autobind from 'autobind-decorator';
import { ModelCollection } from '../../../modelLayer/ModelCollection';
import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react';
import { DocumentTemplate } from '../../models/DocumentTemplate';
import { Link } from 'react-router-dom';
import { PlainInputElement } from '../../../reactUtils/plugins/formable/formElements/PlainInput';
import { MixinFormableTrait } from '../../../reactUtils/plugins/formable/MixinFormableTrait';

export class Index extends MixinFormableTrait(BaseReactComponent) {

    state: {
        documentTemplates: ModelCollection<DocumentTemplate>
        formDummyDocumentTemplate: DocumentTemplate
    } = {
        documentTemplates: new ModelCollection<DocumentTemplate>(),
        formDummyDocumentTemplate: new DocumentTemplate()
    }

    render(){
        return <div className="documentTemplates-Index">
            <div className="pure-form">
              <PlainInputElement
                model={this.state.formDummyDocumentTemplate}
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
            {this.state.documentTemplates.map((documentTemplate, index)=>{
                return <div key={documentTemplate.id}>
                    <p>
                      {documentTemplate.name}
                    </p>
                    <Link to={"/dashboards/documentTemplates/arbitrary/new/" + documentTemplate.id}>
                      create document using it
                    </Link>
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
    search() {
      this.collectInputs()
      let query = this.state.formDummyDocumentTemplate.name
      DocumentTemplate.index({params: {query}}).then((documentTemplates)=>{
          this.setState({documentTemplates})
      })
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
