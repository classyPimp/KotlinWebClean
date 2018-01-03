import { ApplicationComponent } from '../ApplicationComponent';
import { CounterParty } from '../../models/CounterParty';
import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react';
import { match } from 'react-router-dom';
import { PlainInputElement } from '../../../reactUtils/plugins/formable/formElements/PlainInput';
import { MixinFormableTrait } from '../../../reactUtils/plugins/formable/MixinFormableTrait';
import autobind from 'autobind-decorator';
import { FlashMessageQueue } from '../shared/FlashMessageQueue';
import { DropDownSelectServerFed } from '../formelements/DropdownSelectServerFed'
import { IncorporationForm } from '../../models/IncorporationForm'

export class Edit extends MixinFormableTrait(BaseReactComponent) {

    props: {
        match: match<any>
    }

    state: {
        counterParty: CounterParty
    } = {
        counterParty: null
    }

    componentDidMount(){
        let id = this.props.match.params.id
        CounterParty.edit({wilds: {id}}).then((counterParty)=>{
            this.setState({counterParty})
        })
    }

    render(){
        return <div className="counterParties-Edit">
            {this.state.counterParty && 
                <div>
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
                        preselected = {this.state.counterParty.incorporationFormId}
                        registerInput={(it)=>{this.registerInput(it)}}
                        optional={{placeholder: "incorporation form"}}
                    />
                    <button onClick={this.submit}>
                        update
                    </button>
                </div>
            }
        </div>
    }


    @autobind
    submit(){
        this.collectInputs()

        this.state.counterParty.validate()
        if (!this.state.counterParty.isValid()) {
            this.setState({})
            return
        }

        this.state.counterParty.update().then((counterParty)=>{
            if (counterParty.isValid()){
                ApplicationComponent.instance.flashMessageQueue.addMessage(
                  "counter party successfully updated"
                )
            }
            this.setState({counterParty})
        })
    }

}
