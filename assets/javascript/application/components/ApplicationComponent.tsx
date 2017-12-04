import { NotFound } from './shared/NotFound';
import { CurrentUser } from '../services/CurrentUser';
import { DashboardsComponents } from './dashboards/DashboardsComponents';
import { DemoComponent } from './Demo';
import * as React from 'react';
import { BaseReactComponent } from '../../reactUtils/BaseReactComponent'
import { Router, Route, BrowserRouter, Link, match, Switch } from 'react-router-dom';
import {TopMenu} from "./shared/TopMenu"
import {UsersComponents} from './users/UsersComponents'

export class ApplicationComponent extends BaseReactComponent {

    props: {match?: match<any>}

    constructor(props: any) {
        super(props)
        CurrentUser.instance.tryLoginFromCookie()
    }

    render() {
        return <div>
            <TopMenu/>
            <Switch>
                <Route path={`${this.props.match.url}demo`} component={ DemoComponent }/>
                <Route path="/users" component={UsersComponents.main} />
                <Route path="/dashboards" component={DashboardsComponents.main}/>
                <Route component={NotFound}/>
            </Switch>
        </div>
    }

}
