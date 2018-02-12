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
import { PersonToCounterPartyLinkToUploadedDocLinkReasonsComponents } from '../persontocounterpartylinktouploadeddoclinkreasons/PersonToCounterPartyLinkToUploadedDocLinkReasonsComponents' 
import { PersonToCounterPartyLinksComponents } from '../persontocounterpartylinks/PersonToCounterPartyLinksComponents'
import { DocumentTemplateVariablesComponents } from '../documenttemplatevariables/DocumentTemplateVariablesComponents'
import { DocumentTemplateComponents } from '../documenttemplates/DocumentTemplateComponents'
import { DocumentTemplateCategoriesComponents } from '../documenttemplatecategories/DocumentTemplateCategoriesComponents'
import { ContractCategoryComponents } from '../contractcategories/ContractCategoryComponents'
import { ContractToUploadedDocumentLinkReasonComponents } from '../contracttouploadeddocumentlinkreason/ContractToUploadedDocumentLinkReasonComponents';
import { ContractComponents } from '../contract/ContractComponents'

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
                    
                    <Route exact path={`${this.props.match.url}/PersonToCounterPartyLinkToUploadedDocLinkReasons/new`} component={PersonToCounterPartyLinkToUploadedDocLinkReasonsComponents.New}/>
                    <Route exact path={`${this.props.match.url}/PersonToCounterPartyLinkToUploadedDocLinkReasons`} component={PersonToCounterPartyLinkToUploadedDocLinkReasonsComponents.Index}/>
                    <Route exact path={`${this.props.match.url}/PersonToCounterPartyLinkToUploadedDocLinkReasons/:id/edit`} component={PersonToCounterPartyLinkToUploadedDocLinkReasonsComponents.Edit}/>
                    <Route exact path={`${this.props.match.url}/PersonToCounterPartyLinkToUploadedDocLinkReasons/:id`} component={PersonToCounterPartyLinkToUploadedDocLinkReasonsComponents.Show}/>                    

                    <Route exact 
                      path={`${this.props.match.url}/personToCounterPartyLinks/:id/edit`} 
                      component={PersonToCounterPartyLinksComponents.Edit}
                    />

                    <Route exact path={`${this.props.match.url}/documentTemplateVariables/new`} component={DocumentTemplateVariablesComponents.New}/>
                    <Route exact path={`${this.props.match.url}/documentTemplateVariables`} component={DocumentTemplateVariablesComponents.Index}/>
                    <Route exact path={`${this.props.match.url}/documentTemplateVariables/:id/edit`} component={DocumentTemplateVariablesComponents.Edit}/>
                    <Route exact path={`${this.props.match.url}/documentTemplateVariables/:id`} component={DocumentTemplateVariablesComponents.Show}/>
                    
                    <Route exact path={`${this.props.match.url}/documentTemplates/new`} component={DocumentTemplateComponents.New}/>
                    <Route exact path={`${this.props.match.url}/documentTemplates`} component={DocumentTemplateComponents.Index}/>

                    <Route exact path={`${this.props.match.url}/documentTemplates/arbitrary/new/:id`} component={DocumentTemplateComponents.arbitrary.New}/>

                    <Route exact path={`${this.props.match.url}/documentTemplateCategories/new`} component={DocumentTemplateCategoriesComponents.New}/>
                    <Route exact path={`${this.props.match.url}/documentTemplateCategories`} component={DocumentTemplateCategoriesComponents.Index}/>
                    <Route exact path={`${this.props.match.url}/documentTemplateCategories/:id/edit`} component={DocumentTemplateCategoriesComponents.Edit}/>
                    <Route exact path={`${this.props.match.url}/documentTemplateCategories/:id`} component={DocumentTemplateCategoriesComponents.Show}/>

                    <Route exact path={`${this.props.match.url}/contractCategories/new`} component={ContractCategoryComponents.New}/>
                    <Route exact path={`${this.props.match.url}/contractCategories`} component={ContractCategoryComponents.Index}/>
                    <Route exact path={`${this.props.match.url}/contractCategories/:id/edit`} component={ContractCategoryComponents.Edit}/>
                    <Route exact path={`${this.props.match.url}/contractCategories/:id`} component={ContractCategoryComponents.Show}/>

                    <Route exact path={`${this.props.match.url}/contractToUploadedDocumentLinkReasons/new`} component={ContractToUploadedDocumentLinkReasonComponents.New}/>
                    <Route exact path={`${this.props.match.url}/contractToUploadedDocumentLinkReasons`} component={ContractToUploadedDocumentLinkReasonComponents.Index}/>
                    <Route exact path={`${this.props.match.url}/contractToUploadedDocumentLinkReasons/:id/edit`} component={ContractToUploadedDocumentLinkReasonComponents.Edit}/>
                    <Route exact path={`${this.props.match.url}/contractToUploadedDocumentLinkReasons/:id`} component={ContractToUploadedDocumentLinkReasonComponents.Show}/>

                    <Route exact path={`${this.props.match.url}/contracts/new`} component={ContractComponents.New}/>
                    <Route exact path={`${this.props.match.url}/contracts`} component={ContractComponents.Index}/>
                    <Route exact path={`${this.props.match.url}/contracts/:id/edit`} component={ContractComponents.Edit}/>
                    <Route exact path={`${this.props.match.url}/contracts/manage/:id`} component={ContractComponents.manage.Show}/>
                    
                </Switch>
            </div>
        </div>
    }

}