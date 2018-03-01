import { BaseReactComponent } from "../../../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { MonetaryObligation } from "../../../../models/MonetaryObligation"
import { ModelCollection } from '../../../../../modelLayer/ModelCollection'
import { MonetaryObligationComponents } from './MonetaryObligationComponents'

export class Index extends BaseReactComponent {

    props: {
      contractId: number
    }

    state: {
      monetaryObligations: ModelCollection<MonetaryObligation>
    } = {
      monetaryObligations: new ModelCollection<MonetaryObligation>()
    }

    componentDidMount() {
      MonetaryObligation.forContractManageIndex({wilds: {contractId: this.props.contractId.toString()}}).then((monetaryObligations)=>{
        this.setState({monetaryObligations})
      })
    }

    render(){
        return <div>
          {this.state.monetaryObligations.map((monetaryObligation)=>{
            return <MonetaryObligationComponents.Edit
              monetaryObligation = {monetaryObligation}
              key = {monetaryObligation.id}
            />
          })}
        </div>
    }

}
