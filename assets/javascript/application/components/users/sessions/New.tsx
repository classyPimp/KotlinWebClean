import { CurrentUser } from '../../../services/CurrentUser';
import autobind from 'autobind-decorator';
import { User } from '../../../models/User';
import { MixinFormableTrait } from '../../../../reactUtils/plugins/formable/MixinFormableTrait';
import { BaseReactComponent } from '../../../../reactUtils/BaseReactComponent';
import { PlainInputElement } from '../../../../reactUtils/plugins/formable/formElements/PlainInput';
import * as React from 'react';
import { Account } from '../../../models/Account';


export class New extends MixinFormableTrait(BaseReactComponent) {

    state: {
        user: User
    } = {
        user: new User({account: {}})
    }

    render(){
        return <div className="sessions-new">
            <PlainInputElement model={this.state.user} propertyName="name" registerInput={(it)=>{this.registerInput(it)}} optional={{placeholder: "name"}}/>
            <PlainInputElement model={this.state.user.account} propertyName="password" registerInput={(it)=>{this.registerInput(it)}} optional={{placeholder: "password"}}/>
            <button onClick={this.submit}>
                login
            </button>
        </div>
    }

    @autobind
    submit(){
        let user = this.state.user
        this.collectInputs()
        user.validate()
        if(user.isValid()) {
            user.login().then((returnedUser: User)=>{
                if (returnedUser.isValid()) {
                    CurrentUser.instance.logIn(returnedUser)
                } else {
                    user.mergeWith(returnedUser)
                }
                this.setState({user})
            })
        }  
    }

}