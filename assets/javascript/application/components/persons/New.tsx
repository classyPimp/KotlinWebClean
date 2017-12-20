import { ApplicationComponent } from '../ApplicationComponent';
import { Person } from '../../models/Person';
import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react';
import { match } from 'react-router-dom';
import { PlainInputElement } from '../../../reactUtils/plugins/formable/formElements/PlainInput';
import { MixinFormableTrait } from '../../../reactUtils/plugins/formable/MixinFormableTrait';
import autobind from 'autobind-decorator';
import { FlashMessageQueue } from '../shared/FlashMessageQueue';
import { ErrorsShow } from '../shared/ErrorsShow'

export class New extends MixinFormableTrait(BaseReactComponent) {

    props: {
      match: match<any>
    }

    state: {
      person: Person
    } = {
      person: new Person()
    }


    render(){
        return <div className="Persons-New">
          <h2>
            create new person
          </h2>
          {this.state.person.getErrorsFor("general") &&
            <ErrorsShow errors={this.state.person.getErrorsFor("general")}/>
          }
          <PlainInputElement 
            model={this.state.person}
            propertyName="name"
            registerInput={(it)=>{this.registerInput(it)}}
            optional={{placeholder: "name"}}
           />
           <button onClick={this.submit}>
             submit
           </button>
        </div>
    }

    @autobind
    submit(){
      this.collectInputs()
      this.state.person.create().then((person)=>{
        if (!person.isValid()){
          this.setState({person})
        } else {
          ApplicationComponent.instance.flashMessageQueue.addMessage(
            <p>
              person successfully created
            </p>
          )
        }
      })
    }

}
