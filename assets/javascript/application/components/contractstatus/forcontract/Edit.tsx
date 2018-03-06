import { BaseReactComponent } from "../../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { Router, Route, Link, match, Switch } from 'react-router-dom';
import { DropDownSelectServerFed } from '../../formelements/DropDownSelectServerFed'
import { FlashMessageQueue } from '../../shared/FlashMessageQueue';
import { PlainInputElement } from '../../../../reactUtils/plugins/formable/formElements/PlainInput';
import { MixinFormableTrait } from '../../../../reactUtils/plugins/formable/MixinFormableTrait';
import autobind from 'autobind-decorator';
import { ContractStatus } from '../../../models/ContractStatus'
import { PlainCheckbox } from '../../formelements/PlainCheckbox'

export class Edit extends MixinFormableTrait(BaseReactComponent) {

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
      ContractStatus.editForContract({wilds: {contractId}}).then((contractStatus)=>{
        this.setState({contractStatus})
      })
    }


    render(){
        let contractStatus = this.state.contractStatus

        if (!contractStatus) {
          return <div>
            ...loading
          </div>
        }

        return <div>
          <PlainCheckbox
            model = {this.state.contractStatus}
            propertyName = "isProject"
            registerInput = {(it)=>{this.registerInput(it)}}
            optional = {{
              placeholder: "is project"
            }}
          />
          <button onClick = {this.update}>
            update
          </button>
        </div>
    }

    @autobind
    update() {
      this.collectInputs()
      this.state.contractStatus.forContractUpdate().then((contractStatus)=>{
        if (contractStatus.isValid()) {
          alert("successfully updated")
        } else {
          this.setState({contractStatus})
        }
      })
    }

}
