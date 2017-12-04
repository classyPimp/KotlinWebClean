import { ContragentsComponents } from '../contragents/ContragentsComponents';
import { DashboardsComponents } from './DashboardsComponents';
import { BaseReactComponent } from '../../../reactUtils/BaseReactComponent';
import * as React from 'react';
import { Component } from 'react';
import { Route, match } from 'react-router-dom';
import { CurrentUser } from '../../services/CurrentUser';

export class Main extends BaseReactComponent {

    props: {
        match?: match<any>
        location?: Location,
        history?: History
    }

    componentWillMount(){
        if(!CurrentUser.instance.loggedIn) {
            this.props.history.pushState(
                null,
                null,
                "/users/sessions/new"
            )
        }
    }

    render(){
        return <div className="dashboards-main pure-g">
            <div className="pure-u-1-5">
                <DashboardsComponents.sideBar/>
            </div>
            <div className="dashboards-index pure-u-4-5">
                <Route path={`${this.props.match.url}/contragents/new`} component={ContragentsComponents.new} />                
            </div>
        </div>
    }

}