import autobind from 'autobind-decorator';
import { BaseReactComponent } from '../../../reactUtils/BaseReactComponent';
import { CurrentUser, LoginStatusListener } from '../../services/CurrentUser';
import { Link } from 'react-router-dom';
import * as React from 'react';
import { User } from '../../models/User';


export class LoginStatusComponent extends BaseReactComponent implements LoginStatusListener {

    state = {
        currentUser: CurrentUser.instance
    }

    componentDidMount(){
        CurrentUser.instance.subscribeLoginStatusListener(this)
    }

    componentWillUnmount(){
        CurrentUser.instance.unsubcsribeLoginStatusListener(this)
    }

    render(){
        return <div className="login-status">
            { this.state.currentUser.loggedIn ?
            <div>
                {this.state.currentUser.loggedInInstance.name}
                <button onClick={this.logout}>
                    logout
                </button>
            </div> :
            <div>
                <div>
                    <Link to="/users/registration/create">
                        register
                    </Link>
                </div>
                <div>
                    <Link to="/users/sessions/new">
                        login
                    </Link>
                </div>
            </div>
            }
        </div>
    }

    @autobind
    logout(){
        this.state.currentUser.loggedInInstance.logout().then(()=>{
            this.state.currentUser.logOut(this.state.currentUser.loggedInInstance)
            this.forceUpdate()
        })      
    }

    onLoggedInEvent(user: User) {
        this.forceUpdate()
    }

    onLoggedOutEvent(user: User) {
        this.forceUpdate()
    }

}