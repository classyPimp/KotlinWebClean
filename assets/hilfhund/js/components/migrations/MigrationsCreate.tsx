import autobind from 'autobind-decorator';
import { MixinFormableTrait } from '../../../../javascript/reactUtils/plugins/formable/MixinFormableTrait';
import { BaseReactComponent } from '../../../../javascript/reactUtils/BaseReactComponent';
import { Migration } from '../../models/Migration'
import {Link} from 'react-router-dom'
import { PlainInputElement } from '../../../../javascript/reactUtils/plugins/formable/formElements/PlainInput';
import * as React from 'react'

export class MigrationsCreate extends  MixinFormableTrait(BaseReactComponent) {

    constructor(props: any) {
        super(props)
    }

    state = {
        migration: new Migration()
    }

    render() {
        return <div>
            <div>
                <div>
                    {this.renderErrorsIfAny()}  
                    <PlainInputElement model={this.state.migration} propertyName="name" optional={{showPlaceholder: "name"}} registerInput={this.registerInput}/>
                </div>
                <div>
                    <PlainInputElement model={this.state.migration} propertyName="options" optional={{showPlaceholder: "options"}} registerInput={this.registerInput}/>
                </div>
                <button onClick={this.submit}>
                    generate migration
                </button>
            </div>
        </div>
    }

    @autobind
    submit(): any{
        console.log("submitting")
        this.collectInputs()
        let migration = this.state.migration
        migration.createMigration().then((newMigration)=>{
            newMigration.validate()
            if (newMigration.isValid()) {
                alert("success")
                this.setState({migration: new Migration()})
            } else {
                this.setState({migration: newMigration})
            }
        })
    }

    @autobind
    renderErrorsIfAny(): any {
        if (!this.state.migration.isValid()) {
            return <div>
                <p>errors:</p>
                {this.renderErrorsContainer()}
            </div>
        } else {
            return null
        }
    }

    @autobind
    renderErrorsContainer(): any {
        return Object.keys(this.state.migration.errors).map((key: string)=>{
            let value: string[] = this.state.migration.errors[key]
            return <p>{value.join("\n")}</p>
        })
    }
}