import autobind from 'autobind-decorator';
import { ModelCollection } from '../../../modelLayer/ModelCollection';
import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react';
import { Person } from '../../models/Person';
import { Router, Route, Link, match, Switch } from 'react-router-dom';
import { PersonsComponents } from './PersonsComponents'
import { PersonToCounterPartyLinksComponents } from '../persontocounterpartylinks/PersonToCounterPartyLinksComponents'

export class ShowMain extends BaseReactComponent {

    props: {
      match: match<any>
    }

    state: {
      person: Person
      activeMenuItem: string
    } = {
      person: null,
      activeMenuItem: null
    }

    componentDidMount() {
      let id = this.props.match.params.id
      let activeMenuItem = this.getActiveMenuItemName()
      Person.showMinimal({wilds: {id}}).then((person) => {
        this.setState({person, activeMenuItem})
      })
    }

    componentWillReceiveProps(newProps: any) {
      this.updateActiveMenuItemIfNeeded()
    }

    @autobind
    getActiveMenuItemName(): string {
      let activeMenuItem = window.location.pathname.split("/")[4] || null
      return activeMenuItem
    }

    @autobind
    updateActiveMenuItemIfNeeded() {
      let activePart = this.getActiveMenuItemName()
      if (this.state.activeMenuItem !== activePart) {
        this.setState({activeMenuItem: activePart})
      }
    }


    render(){
        return <div>
          <h3>
            {this.state.person 
              ? this.state.person.name
              : "loading"
            }
          </h3>
          <div className="pure-menu pure-menu-horizontal">
            <ul className="pure-menu-list">
                <li className="pure-menu-item">
                  <Link to={`/dashboards/persons/${this.props.match.params.id}`} className={`pure-menu-link${this.isActiveMenuItem(null)}`}>
                    general info
                  </Link>
                </li>
                <li className="pure-menu-item">
                  <Link to={`/dashboards/persons/${this.props.match.params.id}/contacts`} className={`pure-menu-link${this.isActiveMenuItem("contacts")}`}>
                    contacts
                  </Link>
                </li>
                <li className="pure-menu-item">
                  <Link to={`/dashboards/persons/${this.props.match.params.id}/personToCounterPartyLinks`} className={`pure-menu-link${this.isActiveMenuItem("personToCounterPartyLinks")}`}>
                    linked counter parties
                  </Link>
                </li>
                <li className="pure-menu-item">
                  <Link to={`/dashboards/persons/${this.props.match.params.id}/edit`} className={`pure-menu-link${this.isActiveMenuItem("edit")}`}>
                    edit
                  </Link>
                </li>
            </ul>
          </div>
          <Switch>
              <Route path="/dashboards/persons/:id/edit" component={PersonsComponents.EditMain}/>
              <Route exact path={`/dashboards/persons/:id`} component={ PersonsComponents.ShowGeneralInfo }/>
              <Route exact path={`/dashboards/persons/:id/contacts`} component={ PersonsComponents.contacts.Index }/>
              <Route path={`/dashboards/persons/:id/personToCounterPartyLinks`} component={ PersonToCounterPartyLinksComponents.forPerson.Main }/>
              {/*<Route exact path={`/dashboards/persons/new`} component={ PersonsComponents.New } />
              <Route exact path="/dashboards/persons/:id" component={ PersonsComponents.Show } />
              <Route exact path="/dashboards/persons/:id/edit" component={ PersonsComponents.Edit } />*/}
          </Switch>
        </div>
    }

    @autobind
    isActiveMenuItem(partName: string): string {
      console.log(partName)
      console.log(this.state.activeMenuItem)
      if (partName === this.state.activeMenuItem) {
        console.log("MATCHES")
        return " pure-menu-active"
      }
      return ""
    }

}
