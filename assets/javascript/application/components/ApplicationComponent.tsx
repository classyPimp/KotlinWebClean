import autobind from 'autobind-decorator';
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
import { FlashMessageQueue } from './shared/FlashMessageQueue';
import { ModelRegistry } from '../../modelLayer/ModelRegistry'

export class ApplicationComponent extends BaseReactComponent {

    static instance: ApplicationComponent

    flashMessageQueue: FlashMessageQueue

    props: {match?: match<any>, history?: any}

    constructor(props: any) {
        super(props)
        CurrentUser.instance.tryLoginFromCookie()
        XhrRequestMaker.onFailHandler = this.xhrOnFailHandler
        ApplicationComponent.instance = this
    }

    @autobind
    xhrOnFailHandler(xhr: XMLHttpRequest) {
        if (xhr.status === 404) {
            this.props.history.replace("/404", {})
        }
    }

    componentWillMount() {
      console.log(this.props.match.url)
      if (!this.userIsLoggedIn() && this.props.match.url !== "/404") {
        this.props.history.push("/users/sessions/new")
      } else {
        if (window.location.pathname === "/") {
          this.props.history.push("/dashboards")
        }
      }
    }

    @autobind
    userIsLoggedIn(): Boolean {
      return CurrentUser.instance.loggedIn
    }


    render() {
        return <div>
            <FlashMessageQueue ref={(it)=>{this.flashMessageQueue = it}}/>
            {/*<TopMenu/>*/}
            <Switch>
                <Route path={`${this.props.match.url}demo`} component={ DemoComponent }/>
                <Route path="/users" component={UsersComponents.main} />
                <Route path="/dashboards" component={DashboardsComponents.main}/>
                <Route path="/404" component={NotFound}/>
            </Switch>
        </div>
    }

}
