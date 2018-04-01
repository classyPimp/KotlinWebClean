import { CurrentUser } from '../../../services/CurrentUser';
import autobind from 'autobind-decorator';
import { PlainInputElement } from '../../../../reactUtils/plugins/formable/formElements/PlainInput';
import { User } from '../../../models/User';
import { BaseReactComponent } from '../../../../reactUtils/BaseReactComponent';
import { MixinFormableTrait } from '../../../../reactUtils/plugins/formable/MixinFormableTrait';
import * as React from 'react';
import { Account } from '../../../models/Account';
import { Avatar } from '../../../models/Avatar'

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
        </div>
    }

    @autobind
    submit(){
        this.collectInputs()
        let user = this.state.user
        user.create().then((newUser)=>{
            if (newUser.isValid()) {
                CurrentUser.instance.logIn(newUser)
            } else {
                user.mergeWith(newUser)
            }
            this.setState({user})
        })
    }


}