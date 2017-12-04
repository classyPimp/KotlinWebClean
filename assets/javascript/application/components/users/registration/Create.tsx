import autobind from 'autobind-decorator';
import { PlainInputElement } from '../../../../reactUtils/plugins/formable/formElements/PlainInput';
import { User } from '../../../models/User';
import { BaseReactComponent } from '../../../../reactUtils/BaseReactComponent';
import { MixinFormableTrait } from '../../../../reactUtils/plugins/formable/MixinFormableTrait';
import * as React from 'react';
import { Account } from 'application/models/Account';
import { Avatar } from 'application/models/Avatar'

export class Create extends MixinFormableTrait(BaseReactComponent) {

    state: {user: User} = {
      user: new User({account: {}, avatar: {}})
    }

    render(){
        return <div>
            <PlainInputElement model={this.state.user} propertyName="name" registerInput={this.registerInput}/>
            <PlainInputElement model={this.state.user.account} propertyName="password" registerInput={this.registerInput}/>
            <PlainInputElement model={this.state.user.account} propertyName="passwordConfirmation" registerInput={this.registerInput}/>

            <button onClick={this.submit}>
                submit
            </button>

            <button onClick={this.login}>  
              log in 
            </button>
        </div>
    }

    @autobind
    submit(){
        this.collectInputs()
        let user = this.state.user
        user.create().then((newUser)=>{
            console.log(newUser)
        })
    }

    @autobind
    login(){
      this.collectInputs()
      let user = this.state.user
      user.login().then((returnedUser)=>{
          console.log(returnedUser)
      })
    }

}