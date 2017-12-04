import { User } from '../models/User';
import autobind from 'autobind-decorator';
import { PlainInputElement } from '../../reactUtils/plugins/formable/formElements/PlainInput';

import { BaseReactComponent } from '../../reactUtils/BaseReactComponent';

import * as React from 'react';
import { MixinFormableTrait } from '../../reactUtils/plugins/formable/MixinFormableTrait';

export class DemoComponent extends MixinFormableTrait(BaseReactComponent) {

    state: {user: User}

    constructor(...args: any[]){
        super(...args)
        this.state = {
            user: new User()
        }
    }

    @autobind
    handleSubmit(event: any){
        let user = new User()
        user.name = "joe"
        user.id = 10
        
        
        
        user.properties = {
            name: "joe",
            id: 3,
            fruits: ["apple", "pear"],
            friend: {
                id: 4,
                name: "paul"
            },
            friends: [
                {
                    id: 5,
                    name: "yolo",
                    fruits: ["banana", "coconut"]
                },
                {
                    id: 6,
                    name: "foo"
                }
            ]
        }
        user.create().then((user)=>{
            console.log("done")
        }).catch((err)=>{
          console.log(err)
        })
    }

    render(){
        return (
            <div>
                <PlainInputElement model={this.state.user} propertyName="name" registerInput={this.registerInput} optional={{showPlaceholder: "name"}}/>
                <button onClick={this.handleSubmit}>
                    submit
                </button>
            </div>
        )
    }


}