import { ModelCollection } from '../../../../javascript/modelLayer/ModelCollection';
import autobind from 'autobind-decorator';
import { MixinFormableTrait } from '../../../../javascript/reactUtils/plugins/formable/MixinFormableTrait';
import { BaseReactComponent } from '../../../../javascript/reactUtils/BaseReactComponent';
import { Model } from '../../models/Model'
import { TableField } from '../../models/TableField';
import {AssociatedModel} from "../../models/AssociatedModel"
import {Link} from 'react-router-dom'
import { PlainInputElement } from '../../../../javascript/reactUtils/plugins/formable/formElements/PlainInput';
import * as React from 'react';

export class ModelGenerationIndexComponent extends MixinFormableTrait(BaseReactComponent) {

    state: {model: Model}

    init(){
        let model = new Model()
        this.state = {model}
    }

    render() {
        return <div>
            {this.renderErrorsIfAny()}
            <div >
                <div className="model-props">
                    <PlainInputElement model={this.state.model} propertyName="className" registerInput={this.registerInput} optional={{ placeholder: "class name" }} />
                    <PlainInputElement model={this.state.model} propertyName="pluralClassname" registerInput={this.registerInput} optional={{ placeholder: "plural class name" }} />
                </div>
                <div className="model-tableFields">
                    {this.state.model.tableFields.isNotEmpty() &&
                        <div>
                            {this.state.model.tableFields.map((tableField, index) => {
                                return <div key={index}>
                                    <PlainInputElement model={tableField} propertyName="name" registerInput={this.registerInput} optional={{ placeholder: "field name" }} />
                                    <PlainInputElement model={tableField} propertyName="isPrimaryKey" registerInput={this.registerInput} optional={{ placeholder: "is primary key" }} />
                                    <PlainInputElement model={tableField} propertyName="type" registerInput={this.registerInput} optional={{ placeholder: "field type" }} />
                                </div>
                            })}
                        </div>
                    }
                    <button onClick={this.addField}>
                        add field
                </button>
                </div>
                <div className="model-associatedModels">
                    {this.state.model.associatedModels &&
                        this.state.model.associatedModels.map((associatedModel, index) => {
                            return <div key={index}>
                                <PlainInputElement model={associatedModel} propertyName="className" registerInput={this.registerInput} optional={{ placeholder: "class name" }} />
                                <PlainInputElement model={associatedModel} propertyName="associationType" registerInput={this.registerInput} optional={{ placeholder: "association type" }} />
                                <PlainInputElement model={associatedModel} propertyName="fieldOnThis" registerInput={this.registerInput} optional={{ placeholder: "field on this" }} />
                                <PlainInputElement model={associatedModel} propertyName="fieldOnThat" registerInput={this.registerInput} optional={{ placeholder: "field on that" }} />
                                <PlainInputElement model={associatedModel} propertyName="pluralClassName" registerInput={this.registerInput} optional={{ placeholder: "plural class name" }} />
                                <PlainInputElement model={associatedModel} propertyName="property" registerInput={this.registerInput} optional={{ placeholder: "property name" }} />
                            </div>

                        })
                    }
                    <button onClick={this.addAssociatedModel}>
                        add associated model
                  </button>
                </div>
                <button onClick={this.generateModel}>
                    generate model
                </button>
                <div className="factory">
                    <PlainInputElement model={this.state.model} propertyName="factoryName" registerInput={this.registerInput} optional={{placeholder: "factory name"}}/>
                    <button onClick={this.generateFactory}>
                        generate factory
                    </button>
                </div>
                <div className="jsonSerializer">
                    <PlainInputElement model={this.state.model} propertyName="toJsonSerializerName" registerInput={this.registerInput} optional={{placeholder: "toJsonSerialier name"}}/>
                    <button onClick={this.generateToJsonSerializer}>
                        generate factory
                    </button>
                </div>

                <div className="jsComponent">
                <PlainInputElement model={this.state.model} propertyName="reactComponentName" registerInput={this.registerInput} optional={{placeholder: "react component name"}}/>
                <button onClick={this.generateReactComponent}>
                    generate react component
                </button>
                <button onClick={this.generateJsModel}>
                    generate js model
                </button>

                <div className="jsComponent">
                <PlainInputElement model={this.state.model} propertyName="composerName" registerInput={this.registerInput} optional={{placeholder: "composer name"}}/>
                <button onClick={this.generateComposer}>
                    generate composer
                </button>
                </div>

            </div>

            </div>
        </div>
    }


    @autobind
    renderErrorsIfAny(){
        let model = this.state.model
        
        if (!model.isValid()) {
            
            return <div>
              
                <p>Achtung! errors:</p>
                
                {Object.keys(this.state.model.errors).map((key: string)=>{
                    let value: string[] = this.state.model.errors[key]
                    
                    return <div> 
                        {value.map((it)=>{
                            return <p>
                               {it}
                            </p>
                        })}
                    </div>
                })}   

            </div>
        }  

        return null              
    }

    @autobind
    addField(){
        let model = this.state.model
        model.tableFields.push(new TableField())
        this.setState({model})
    }

    @autobind
    addAssociatedModel(){
        let model = this.state.model
        model.associatedModels.push(new AssociatedModel())
        this.setState({model})
    }

    @autobind
    generateModel(){
        this.collectInputs()
        this.state.model.generateModel().then(()=>{
            this.setState({})
        })
    }

    @autobind
    generateFactory(){
        this.collectInputs() 
        this.state.model.generateFactory().then(()=>{
            this.setState({})
        })
    }

    @autobind
    generateToJsonSerializer(){
        this.collectInputs()
        this.state.model.generateToJsonSerializer().then(()=>{
            this.setState({})
        })
    }

    @autobind
    generateReactComponent() {
        this.collectInputs()
        this.state.model.generateReactComponent().then(()=>{
            this.setState({})
        })
    }

    @autobind
    generateJsModel() {
        this.collectInputs()
        this.state.model.generateJsModel().then(()=>{
            this.setState({})
        })
    }

    @autobind
    generateComposer(){
        this.collectInputs()
        this.state.model.generateComposer().then(()=>{
            this.setState({})
        }) 
    }

}