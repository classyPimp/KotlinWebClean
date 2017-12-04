import {BaseReactComponent} from "../../../reactUtils/BaseReactComponent"
import {Route} from "react-router-dom"
import * as React from "react"
import {match} from "react-router-dom"
import {UsersComponents} from './UsersComponents'

export class Main extends BaseReactComponent {

    props: {
      match?: match<any>
    }

    render() {
      return <div>
        <Route path={`${this.props.match.url}/registration/create`} component={UsersComponents.registration.create}/>
        <Route path={`${this.props.match.url}/sessions/new`} component={UsersComponents.sessions.new}/>
      </div>

    }

}