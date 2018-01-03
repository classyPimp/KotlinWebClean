import { ApplicationComponent } from '../ApplicationComponent';
import { CounterParty } from '../../models/CounterParty';
import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react';
import { match } from 'react-router-dom';
import { PlainInputElement } from '../../../reactUtils/plugins/formable/formElements/PlainInput';
import { MixinFormableTrait } from '../../../reactUtils/plugins/formable/MixinFormableTrait';
import autobind from 'autobind-decorator';
import { FlashMessageQueue } from '../shared/FlashMessageQueue';
import { ErrorsShow } from '../shared/ErrorsShow'
import { DropDownSelectServerFed } from '../formelements/DropdownSelectServerFed'
import { IncorporationForm } from '../../models/IncorporationForm'

export class New extends MixinFormableTrait(BaseReactComponent) {

    props: {
      match: match<any>
    }

    state: {
      counterParty: CounterParty
    } = {
      counterParty: new CounterParty()
    }


    render(){
        return <div className="counterParties-New">
          <h2>
            create new counter party
          </h2>
          {this.state.counterParty.getErrorsFor("general") &&
            <ErrorsShow errors={this.state.counterParty.getErrorsFor("general")}/>
          }
          <PlainInputElement 
            model={this.state.counterParty}
            propertyName="name"
            registerInput={(it)=>{this.registerInput(it)}}
            optional={{placeholder: "name"}}
           />
           <PlainInputElement 
            model={this.state.counterParty}
            propertyName="nameShort"
            registerInput={(it)=>{this.registerInput(it)}}
            optional={{placeholder: "short name"}}
           />
           <DropDownSelectServerFed 
              model={this.state.counterParty}
              propertyName="incorporationFormId"
              queryingFunction = { IncorporationForm.formFeedsIndex.bind(IncorporationForm) }
              propertyToSelect="id" 
              propertyToShow="nameShort"
              registerInput={(it)=>{this.registerInput(it)}}
              optional={{placeholder: "incorporation form"}}
            />
           <button onClick={this.submit}>
             submit
           </button>
        </div>
    }

    @autobind
    submit(){
      this.collectInputs()
      this.state.counterParty.create().then((counterParty)=>{
        if (!counterParty.isValid()){
          this.setState({counterParty})
        } else {
          ApplicationComponent.instance.flashMessageQueue.addMessage(
            <p>
              counter party successfully created
            </p>
          )
        }
      })
    }

}
