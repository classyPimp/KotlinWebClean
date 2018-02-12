import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { ModelCollection } from '../../../modelLayer/ModelCollection';
import { Contract } from '../../models/Contract'
import { Link } from 'react-router-dom';
import autobind from 'autobind-decorator';

export class Index extends BaseReactComponent {

    state: {
      contracts: ModelCollection<Contract>
    } = {
      contracts: new ModelCollection()
    }

    componentDidMount(){
      Contract.index().then((contracts: ModelCollection<Contract>)=>{
        this.setState({contracts})
      })
    }

    render(){
        return <div className="contracts-Index">
          {
            this.state.contracts.map((contract, index)=>{
              return <div className="individual" key={index}>
                <p>
                  <Link to={`/dashboards/contracts/${contract.id}`}>
                    {contract.contractStatus.isProject &&
                      "project"
                    }
                  </Link>
                </p>
                <p>
                  {contract.description}
                </p>
                <p>
                  {contract.contractToCounterPartyLinks.map((contractToCounterPartyLink)=>{
                    return <span key={contractToCounterPartyLink.id}>
                      {contractToCounterPartyLink.counterParty.incorporationForm.name} {contractToCounterPartyLink.counterParty.name}
                    </span>
                  })}
                </p>
                <Link to={`/dashboards/contracts/${contract.id}`}>
                  <button>
                    show
                  </button>
                </Link>
                <Link to={`/dashboards/contracts/manage/${contract.id}`}>
                  <button>
                    edit
                  </button>
                </Link>
                <button onClick={()=>{this.destroyContract(contract)}}>
                  delete
                </button>
              </div>
            })
          }
        </div>
    }

    @autobind
    destroyContract(contract: Contract) {
        contract.destroy().then((returnedContract)=>{
          if (returnedContract.isValid()) {
            this.state.contracts.filter((it)=>{
              return it !== contract
            })
            this.setState({contracts: this.state.contracts})
          } else {
            alert("could not be deleted")
          }
        }) 
    }

}
