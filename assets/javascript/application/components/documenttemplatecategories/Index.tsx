import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { ModelCollection } from '../../../modelLayer/ModelCollection';
import { DocumentTemplateCategory } from '../../models/DocumentTemplateCategory'
import { Link } from 'react-router-dom';
import autobind from 'autobind-decorator';

export class Index extends BaseReactComponent {

    state: {
      documentTemplateCategories: ModelCollection<DocumentTemplateCategory>
    } = {
      documentTemplateCategories: new ModelCollection()
    }

    componentDidMount(){
      DocumentTemplateCategory.index().then((documentTemplateCategories: ModelCollection<DocumentTemplateCategory>)=>{
        this.setState({documentTemplateCategories})
      })
    }

    render(){
        return <div className="documentTemplateCategories-Index">
          {
            this.state.documentTemplateCategories.map((documentTemplateCategory, index)=>{
              return <div className="individual" key={index}>
                <p>
                  <Link to={`/dashboards/documentTemplateCategories/${documentTemplateCategory.id}`}>
                    {
                      documentTemplateCategory.name
                    }
                  </Link>
                </p>
                <Link to={`/dashboards/documentTemplateCategories/${documentTemplateCategory.id}`}>
                  <button>
                    show
                  </button>
                </Link>
                <Link to={`/dashboards/documentTemplateCategories/${documentTemplateCategory.id}/edit`}>
                  <button>
                    edit
                  </button>
                </Link>
                <button onClick={()=>{this.destroyLinkReason(documentTemplateCategory)}}>
                  delete
                </button>
              </div>
            })
          }
        </div>
    }

    @autobind
    destroyLinkReason(documentTemplateCategory: DocumentTemplateCategory) {
        documentTemplateCategory.destroy().then((returnedDocumentTemplateCategory)=>{
          if (returnedDocumentTemplateCategory.isValid()) {
            this.state.documentTemplateCategories.filter((it)=>{
              return it !== documentTemplateCategory
            })
            this.setState({documentTemplateCategories: this.state.documentTemplateCategories})
          } else {
            alert("could not be deleted")
          }
        }) 
    }

}
