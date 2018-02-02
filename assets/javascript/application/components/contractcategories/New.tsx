import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { ContractCategory } from '../../models/ContractCategory'
import { MixinFormableTrait } from '../../../reactUtils/plugins/formable/MixinFormableTrait';
import { PlainInputElement } from '../../../reactUtils/plugins/formable/formElements/PlainInput'
import autobind from 'autobind-decorator'
import { ErrorsShow } from '../shared/ErrorsShow'
import { ApplicationComponent } from '../ApplicationComponent';

export class New extends MixinFormableTrait(BaseReactComponent) {

    state: {
      contractCategory: ContractCategory
    } = {
      contractCategory: new ContractCategory()
    }

    render(){
        return <div className="persontocounterpartylinkreasons-New">
          <h2>
            Create new contract category
          </h2>
          {this.state.contractCategory.getErrorsFor('general') &&
              <ErrorsShow errors={this.state.contractCategory.getErrorsFor('general')}/>
          }
          <PlainInputElement
            model={this.state.contractCategory}
            propertyName="name"
            registerInput={(it)=>{this.registerInput(it)}}
            optional={{placeholder: "name"}}
          />
          <PlainInputElement
            model={this.state.contractCategory}
            propertyName="description"
            registerInput={(it)=>{this.registerInput(it)}}
            optional={{placeholder: "description"}}
          />
          <button onClick={this.submit}>
            submit
          </button>
        </div>
    }

    @autobind
    submit(){
      this.collectInputs()
      
      let currentContractCategory = this.state.contractCategory
      currentContractCategory.validate()
      if (!currentContractCategory.isValid()) {
        this.setState({contractCategory: currentContractCategory})
        return
      }

      currentContractCategory.create().then((contractCategory)=>{
        if (!contractCategory.isValid()) {
              this.setState({contractCategory})
              return
        } 
        ApplicationComponent.instance.flashMessageQueue.addMessage(
          "contract category successfully created"
        )
        this.setState({contractCategory})
      })
      
    }

}
