import { BaseReactComponent } from '../../../reactUtils/BaseReactComponent';
import { MixinFormableTrait } from '../../../reactUtils/plugins/formable/MixinFormableTrait';
import { Company } from '../../models/Company';
import { PlainInputElement } from '../../../reactUtils/plugins/formable/formElements/PlainInput';
import * as React from 'react';


export class New extends MixinFormableTrait(BaseReactComponent) {

    state: {company: Company} = {
        company: new Company()
    }

    render(){
        return <div className="contragents-new">
            <div>
                <p>
                    new contragent
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
                <PlainInputElement 
                    model={this.state.company} 
                    propertyName="nameShort"
                    registerInput={(input)=>{this.registerInput(input)}}
                    optional={{
                        placeholder: "short name"
                    }}
                />
                <PlainInputElement 
                    model={this.state.company} 
                    propertyName="adressLegal"
                    registerInput={(input)=>{this.registerInput(input)}}
                    optional={{
                        placeholder: "legal address"
                    }}
                />
                <PlainInputElement 
                    model={this.state.company} 
                    propertyName="adressPostal"
                    registerInput={(input)=>{this.registerInput(input)}}
                    optional={{
                        placeholder: "postal address"
                    }}
                />
                <PlainInputElement 
                    model={this.state.company} 
                    propertyName="bin"
                    registerInput={(input)=>{this.registerInput(input)}}
                    optional={{
                        placeholder: "BIN"
                    }}
                />
            </div>
        </div>
    }

}