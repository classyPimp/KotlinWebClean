import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { Router, Route, Link, match, Switch } from 'react-router-dom';
import { ContractStatus } from '../../models/ContractStatus'

export class ShowMain extends BaseReactComponent {

    props: {
      match: match<any>
    }

    state: {
      contractStatus: ContractStatus
    } = {
      contractStatus: null
    }

    componentDidMount() {
      let contractId = this.props.match.params.contractId
      ContractStatus.forContractShow({wilds: {contractId}}).then((contractStatus)=>{
        this.setState({contractStatus})
      })
    }

    render(){
        let contractStatus = this.state.contractStatus
        return <div>
          {contractStatus &&
            <div>
              <p>
                is committed: {contractStatus.isCommited 
                  ? "true"
                  : "false"
                }
              </p>
              <p>
                is project: {contractStatus.isProject 
                  ? "true"
                  : "false"
                }
              </p>
              <p>
                is cancelled: {contractStatus.isCancelled 
                  ? "true"
                  : "false"
                }
              </p>
              <p>
                is completed: {contractStatus.isCompleted 
                  ? "true"
                  : "false"
                }
              </p>
            </div>
          }
        </div>
    }

}
