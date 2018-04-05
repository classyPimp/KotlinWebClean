import autobind from 'autobind-decorator';
import { ModelCollection } from '../../../../modelLayer/ModelCollection';
import { BaseReactComponent } from "../../../../reactUtils/BaseReactComponent"
import * as React from 'react';
import { Person } from '../../../models/Person';
import { Router, Route, Link, match, Switch } from 'react-router-dom';
import { ApprovalComponents } from '../ApprovalComponents';
import { Approval } from '../../../models/Approval';

export class Main extends BaseReactComponent {

    props: {
      match: match<any>
    }

    state: {
      approval: Approval,
      loaded: Boolean
    } = {
      approval: null,
      loaded: false
    }


    componentDidMount() {
      let contractId = this.props.match.params.contractId
      Approval.ofContractShow({wilds: {contractId}}).then((approval)=>{
        let loaded = true
        approval.validate()
        if (approval.isValid()) {
          console.log("isValid")
          this.setState({approval, loaded: true})
          return  
        } else {
          this.setState({loaded: true, approval: null})        
        }
      })
    }

    render(){
        if (!this.state.loaded) {
          return <div>
            ...loading
          </div>
        }
        return <div>
          <h3>
            approval
          </h3>
          <div className="pure-menu pure-menu-horizontal">
            <ul className="pure-menu-list">
              {this.state.approval 
                ? null
                : <li className="pure-menu-item">
                  <Link to={`/dashboards/contracts/${this.props.match.params.contractId}/approval/new`} className="pure-menu-link">
                    initialize
                  </Link>
                </li>
              }
            </ul>
          </div>
          <Switch>
              <Route path = { `/dashboards/contracts/:contractId/approval/new` } component = { ApprovalComponents.ofContract.New } />
              {this.state.approval &&
                <Route path = {`/dashboards/contracts/:contractId/approval`} component = { ApprovalComponents.ofContract.Show } />
              }
          </Switch>
        </div>
    }

}
