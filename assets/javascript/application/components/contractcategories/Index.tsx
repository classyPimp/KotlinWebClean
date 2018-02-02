import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { ModelCollection } from '../../../modelLayer/ModelCollection';
import { ContractCategory } from '../../models/ContractCategory'
import { Link } from 'react-router-dom';
import autobind from 'autobind-decorator';

export class Index extends BaseReactComponent {

    state: {
      contractCategories: ModelCollection<ContractCategory>
    } = {
      contractCategories: new ModelCollection()
    }

    componentDidMount(){
      ContractCategory.index().then((contractCategories: ModelCollection<ContractCategory>)=>{
        this.setState({contractCategories})
      })
    }

    render(){
        return <div className="contractCategories-Index">
          {
            this.state.contractCategories.map((documentTemplateCategory, index)=>{
              return <div className="individual" key={index}>
                <p>
                  <Link to={`/dashboards/contractCategories/${documentTemplateCategory.id}`}>
                    {
                      documentTemplateCategory.name
                    }
                  </Link>
                </p>
                <Link to={`/dashboards/contractCategories/${documentTemplateCategory.id}`}>
                  <button>
                    show
                  </button>
                </Link>
                <Link to={`/dashboards/contractCategories/${documentTemplateCategory.id}/edit`}>
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
    destroyLinkReason(documentTemplateCategory: ContractCategory) {
        documentTemplateCategory.destroy().then((returnedContractCategory)=>{
          if (returnedContractCategory.isValid()) {
            this.state.contractCategories.filter((it)=>{
              return it !== documentTemplateCategory
            })
            this.setState({contractCategories: this.state.contractCategories})
          } else {
            alert("could not be deleted")
          }
        }) 
    }

}
