import { ContactTypesComponents } from '../contacttypes/ContactTypesComponents';
import { CounterPartiesComponents } from '../counterparties/CounterPartiesComponents';
import { DashboardsComponents } from './DashboardsComponents';
import { BaseReactComponent } from '../../../reactUtils/BaseReactComponent';
import * as React from 'react';
import { Component } from 'react';
import { Route, match, Switch } from 'react-router-dom';
import { CurrentUser } from '../../services/CurrentUser';
import { ApplicationComponent } from '../ApplicationComponent';
import { Show } from '../contacttypes/Show';
import { PersonsComponents } from '../persons/PersonsComponents';
import { PersonToCounterPartyLinkReasonsComponents } from '../persontocounterpartylinkreasons/PersonToCounterPartyLinkReasonsComponents'
import { IncorporationFormsComponents } from '../incorporationforms/IncorporationFormsComponents'


export class Main extends BaseReactComponent {

    props: {
        match?: match<any>
        location?: Location,
        history?: any
    }

    componentWillMount(){
        if(!CurrentUser.instance.loggedIn) {
            ApplicationComponent.instance.flashMessageQueue.addMessage(
                <div>please login first</div>
            )
            this.props.history.push(
                "/users/sessions/new",
                {}
            )
        }
    }

    render(){
        return <div className="dashboards-main pure-g">
            <div className="pure-u-1-5">
                <DashboardsComponents.sideBar/>
            </div>
            <div className="dashboards-index pure-u-4-5">
                <Switch>
                    <Route exact path={`${this.props.match.url}/contactTypes/new`} component={ContactTypesComponents.New}/>
                    <Route exact path={`${this.props.match.url}/contactTypes`} component={ContactTypesComponents.Index}/>
                    <Route exact path={`${this.props.match.url}/contactTypes/:id/edit`} component={ContactTypesComponents.Edit}/>
                    <Route exact path={`${this.props.match.url}/contactTypes/:id`} component={ContactTypesComponents.Show}/>
                    
                    <Route exact path={`${this.props.match.url}/persons/new`} component={PersonsComponents.New}/>
                    <Route exact path={`${this.props.match.url}/persons`} component={PersonsComponents.Index}/>
                    <Route exact path={`${this.props.match.url}/persons/:id/edit`} component={PersonsComponents.Edit}/>
                    <Route exact path={`${this.props.match.url}/persons/:id`} component={PersonsComponents.Show}/>
                    
                    <Route exact path={`${this.props.match.url}/persontocounterpartylinkreasons/new`} component={PersonToCounterPartyLinkReasonsComponents.New}/>
                    <Route exact path={`${this.props.match.url}/persontocounterpartylinkreasons`} component={PersonToCounterPartyLinkReasonsComponents.Index}/>
                    <Route exact path={`${this.props.match.url}/persontocounterpartylinkreasons/:id/edit`} component={PersonToCounterPartyLinkReasonsComponents.Edit}/>
                    <Route exact path={`${this.props.match.url}/persontocounterpartylinkreasons/:id`} component={PersonToCounterPartyLinkReasonsComponents.Show}/>

                    <Route exact path={`${this.props.match.url}/incorporationforms/new`} component={IncorporationFormsComponents.New}/>
                    <Route exact path={`${this.props.match.url}/incorporationforms`} component={IncorporationFormsComponents.Index}/>
                    <Route exact path={`${this.props.match.url}/incorporationforms/:id/edit`} component={IncorporationFormsComponents.Edit}/>
                    <Route exact path={`${this.props.match.url}/incorporationforms/:id`} component={IncorporationFormsComponents.Show}/>    

                    <Route exact path={`${this.props.match.url}/counterParties/new`} component={CounterPartiesComponents.New}/>
                    <Route exact path={`${this.props.match.url}/counterParties`} component={CounterPartiesComponents.Index}/>
                    <Route exact path={`${this.props.match.url}/counterParties/:id/edit`} component={CounterPartiesComponents.Edit}/>
                    <Route exact path={`${this.props.match.url}/counterParties/:id`} component={CounterPartiesComponents.Show}/>                  
                </Switch>
            </div>
        </div>
    }

}