import { IModelProperties } from '../../modelLayer/interfaces/IModelProperties';
import { HasOne } from '../../modelLayer/annotations/HasOne';
import { Promise } from 'es6-promise';
import { RequestOptions, Route } from '../../modelLayer/annotations/ModelRoute';
import { Property } from '../../modelLayer/annotations/Property';
import { BaseModel } from '../../modelLayer/BaseModel';
import {Account} from './Account'
import { Avatar } from './Avatar'


export class User extends BaseModel {
   
    static className = "user"

    @Property
    id: number

    @Property
    name: string
 
    @Route("POST", {url: "/api/users/create"})
    create: (options?: RequestOptions) => Promise<any>

    beforeCreateRequest(options: RequestOptions){
        options.params = this.getPureProperties() 
    }

    afterCreateRequest(options: RequestOptions){
        options.deferredPromise.then((resp)=>{
            let newUser = new User(resp)
            newUser.validate()
            return newUser
        })
    }

    @Route("POST", {url: "/api/sessions"})
    login: (options?: RequestOptions) => Promise<any>

    beforeLoginRequest(options: RequestOptions) {
      options.params = this.getPureProperties()
    }

    afterLoginRequest(options: RequestOptions) {
        options.deferredPromise.then((resp)=>{
            let newUser = new User(resp)
            newUser.validate()
            return newUser
        })
    }    

    @Route("DELETE", {url: "/api/sessions"})
    logout: (options?: RequestOptions) => Promise<any>

    afterLogoutReuqest(options: RequestOptions) {
        options.deferredPromise.then((resp) => {
            let newUser = new User(resp)
            newUser.validate()
            return newUser
        })
    }

    @HasOne(Account)
    account: Account


    @HasOne(Avatar)
    avatar: Avatar
    // @HasMany(Account, ["fooAcs"])
    // accounts: ModelCollection<Account>

    nameValidator(){
        // if (!(this.name)) {
        //     this.addErrorFor("name", "no name given")
        // }
    }
}