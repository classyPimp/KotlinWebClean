import { BaseReactComponent } from '../../../reactUtils/BaseReactComponent';
import { MixinFormableTrait } from '../../../reactUtils/plugins/formable/MixinFormableTrait';
import { CounterParty } from '../../models/CounterParty';
import { PlainInputElement } from '../../../reactUtils/plugins/formable/formElements/PlainInput';
import * as React from 'react';


export class New extends MixinFormableTrait(BaseReactComponent) {

    state: {company: CounterParty} = {
        company: new CounterParty()
    }

    render(){
        return <div className="counterParties-new">
            <div>
                <p>
                    new counterparty
                </p>
            </div>
            <div className="companies-new">
                <PlainInputElement 
                    model={this.state.company} 
                    propertyName="name"
                    registerInput={(input)=>{this.registerInput(input)}}
                    optional={{
                        placeholder: "name"
                    }}
                />
            </div>
        </div>
    }

}