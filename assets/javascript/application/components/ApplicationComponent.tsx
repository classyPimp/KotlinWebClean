import { NotFound } from './shared/NotFound';
import { CurrentUser } from '../services/CurrentUser';
import { DashboardsComponents } from './dashboards/DashboardsComponents';
import { DemoComponent } from './Demo';
import * as React from 'react';
import { BaseReactComponent } from '../../reactUtils/BaseReactComponent'
import { Router, Route, BrowserRouter, Link, match, Switch } from 'react-router-dom';
import {TopMenu} from "./shared/TopMenu"
import {UsersComponents} from './users/UsersComponents'
import {XhrRequestMaker} from '../../modelLayer/utils/XhrRequestMaker'

export class ApplicationComponent extends BaseReactComponent {

    props: {match?: match<any>, history?: History}

    constructor(props: any) {
        super(props)
        CurrentUser.instance.tryLoginFromCookie()
        XhrRequestMaker.onFailHandler = this.xhrOnFailHandler
    }

    xhrOnFailHandler(xhr: XMLHttpRequest) {
        if (xhr.status === 404) {
            this.props.history.pushState(null, "not found", "/404")
        }
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
