import autobind from 'autobind-decorator';
import { ModelCollection } from '../../../modelLayer/ModelCollection';
import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react';
import { CounterParty } from '../../models/CounterParty';
import { Link } from 'react-router-dom';
import { PlainInputElement } from '../../../reactUtils/plugins/formable/formElements/PlainInput';
import { MixinFormableTrait } from '../../../reactUtils/plugins/formable/MixinFormableTrait';


export class FormSelectIndex extends MixinFormableTrait(BaseReactComponent) {

    props: {
      onSelected: (counterParty: CounterParty) => any
    }

    state: {
      counterParties: ModelCollection<CounterParty>
      dummyCounterPartyForForm: CounterParty
    } = {
      counterParties: new ModelCollection<CounterParty>(),
      dummyCounterPartyForForm: new CounterParty()
    }

    render(){
        return <div>
          <div className="pure-form">
            <PlainInputElement
              model={this.state.dummyCounterPartyForForm}
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
          {this.state.counterParties.map((counterParty, index)=>{
            return <div key={counterParty.id}>

              <Link to={"/dashboards/counterParties/" + counterParty.id}>
                  {counterParty.name} 
              </Link>
              <p>
                short name: {counterParty.nameShort}
              </p>
              <p>
                incorporation form: {counterParty.incorporationForm.name} ({counterParty.incorporationForm.nameShort})
              </p>
              <button onClick = {()=>{this.props.onSelected(counterParty)}}>
                select
              </button>
            </div>
          })

          }
        </div>
    }
    
    @autobind
    search() {
      this.collectInputs()
      let query = this.state.dummyCounterPartyForForm.name
      CounterParty.index({params: {query}}).then((counterParties)=>{
        this.setState({counterParties})
      })
    }


}
