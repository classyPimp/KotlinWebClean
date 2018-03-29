import { BaseReactComponent } from "../../../../reactUtils/BaseReactComponent"
import { User } from '../../../models/User'
import { ModelCollection } from '../../../../modelLayer/ModelCollection'
import { Router, Route, Link, match, Switch } from 'react-router-dom';
import * as React from 'react'
import autobind from 'autobind-decorator'
import { MixinFormableTrait } from '../../../../reactUtils/plugins/formable/MixinFormableTrait';
import { PlainInputElement } from '../../../../reactUtils/plugins/formable/formElements/PlainInput'


export class Index extends MixinFormableTrait(BaseReactComponent) {

    state: {
      formDummyUser: User
      users: ModelCollection<User>
    } = {
      formDummyUser: new User(),
      users: new ModelCollection()
    }

    props: {
      onUserSelected: (user: User)=>any
    }

    render(){
        return <div>
          <PlainInputElement
            model={this.state.formDummyUser}
            propertyName="name"
            registerInput={(it)=>{this.registerInput(it)}}
            optional={{placeholder: "search user by name"}}
          />
          <button onClick={this.submit}>
            search
          </button>
          {this.state.users.map((user)=>{
            return <div key={user.id}>
              <p>{user.name}</p>
              <button onClick={()=>{this.selectUser(user)}}>
                select
              </button>
            </div>
          })}
        </div>
    }

    @autobind
    submit() {
      this.collectInputs()
      let query = this.state.formDummyUser.name
      User.forSearchFormIndex({params: {query}}).then((users)=>{
        this.setState({users})
      })
    }

    @autobind
    selectUser(user: User) {
      this.props.onUserSelected(user)
    }

}
