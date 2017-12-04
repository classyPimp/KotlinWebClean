import { User } from '../../models/User';
import { LoginStatusComponent } from './LoginStatus';
import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import { Link } from "react-router-dom"
import * as React from 'react';
import {UsersComponents} from '../users/UsersComponents'
import { CurrentUser, LoginStatusListener } from '../../services/CurrentUser';

export class TopMenu extends BaseReactComponent implements LoginStatusListener {

    onLoggedInEvent(user: User) {
        this.forceUpdate()
    }

    onLoggedOutEvent(user: User) {
        this.forceUpdate()
    }

    componentDidMount(){
        CurrentUser.instance.subscribeLoginStatusListener(this)
    }

    componentWillUnmount(){
        CurrentUser.instance.unsubcsribeLoginStatusListener(this)
    }

    render() {
        return <div className="pure-menu pure-menu-horizontal">
            <Link to="/" className="pure-menu-heading pure-menu-link">
                Home
            </Link>
            <ul className="pure-menu-list">
                <li className="pure-menu-item">
                    <Link to="/demo" className="pure-menu-link">
                        demo
                    </Link>
                </li>
                {CurrentUser.instance.loggedIn &&
                    <li className="pure-menu-item">
                        <Link to="/dashboards" className="pure-menu-link">
                            dashboard
                        </Link>
                    </li>
                }
                <li className="pure-menu-item">
                    <LoginStatusComponent/>
                </li>
            </ul>
        </div>
    }


}