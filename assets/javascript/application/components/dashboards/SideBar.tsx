import { BaseReactComponent } from '../../../reactUtils/BaseReactComponent';
import * as React from 'react'
import { Link } from 'react-router-dom';

export class SideBar extends BaseReactComponent {

    render() {
        return <div className="dashboards-Sidebar pure-menu custom-restricted-width">
            <ul className="pure-menu-list">
                <li className="pure-menu-item">
                    <Link to="/dashboards/contactTypes/new" className="pure-menu-link">
                        new contact  type
                    </Link>
                </li>
                <li className="pure-menu-item">
                    <Link to="/dashboards/contactTypes" className="pure-menu-link">
                        index contact  types
                    </Link>
                </li>
                <li className="pure-menu-item">
                    <Link to="/dashboards/persons/new" className="pure-menu-link">
                        new person
                    </Link>
                </li>                
                <li className="pure-menu-item">
                    <Link to="/dashboards/persons" className="pure-menu-link">
                        index persons
                    </Link>
                </li>
                <li className="pure-menu-item">
                    <Link to="/dashboards/persontocounterpartylinkreasons/new" className="pure-menu-link">
                        new person to counter party link reason
                    </Link>
                </li>
                <li className="pure-menu-item">
                    <Link to="/dashboards/persontocounterpartylinkreasons" className="pure-menu-link">
                        index person to counter party link reasons
                    </Link>
                </li>
                <li className="pure-menu-item">
                    <Link to="/dashboards/PersonToCounterPartyLinkToUploadedDocLinkReasons/new" className="pure-menu-link">
                        new person to counter party to uploaded document link reason
                    </Link>
                </li>
                <li className="pure-menu-item">
                    <Link to="/dashboards/PersonToCounterPartyLinkToUploadedDocLinkReasons" className="pure-menu-link">
                        index person to counter party to uploaded document link reason
                    </Link>
                </li>
                <li className="pure-menu-item">
                    <Link to="/dashboards/incorporationforms/new" className="pure-menu-link">
                        new incorporation form
                    </Link>
                </li>
                <li className="pure-menu-item">
                    <Link to="/dashboards/incorporationforms" className="pure-menu-link">
                        index incorporation forms
                    </Link>
                </li>

                <li className="pure-menu-item">
                    <Link to="/dashboards/counterParties/new" className="pure-menu-link">
                        new counter party
                    </Link>
                </li>
                <li className="pure-menu-item">
                    <Link to="/dashboards/counterParties" className="pure-menu-link">
                        index counter parties
                    </Link>
                </li>
                <li className="pure-menu-item">
                    <Link to="/dashboards/documentTemplateVariables/new" className="pure-menu-link">
                        new document template variable
                    </Link>
                </li>
                <li className="pure-menu-item">
                    <Link to="/dashboards/documentTemplateVariables" className="pure-menu-link">
                        index documentTemplateVariables
                    </Link>
                </li>

                <li className="pure-menu-item">
                    <Link to="/dashboards/documentTemplates" className="pure-menu-link">
                        index document templates
                    </Link>
                </li>

                <li className="pure-menu-item">
                    <Link to="/dashboards/documentTemplates/new" className="pure-menu-link">
                        create new document template
                    </Link>
                </li>


                 <li className="pure-menu-item">
                    <Link to="/dashboards/documentTemplateCategories" className="pure-menu-link">
                        index document template categories
                    </Link>
                </li>

                <li className="pure-menu-item">
                    <Link to="/dashboards/documentTemplateCategories/new" className="pure-menu-link">
                        create new document template category
                    </Link>
                </li>


                <li className="pure-menu-item">
                    <Link to="/dashboards/contractCategories" className="pure-menu-link">
                        index document contract categories
                    </Link>
                </li>

                <li className="pure-menu-item">
                    <Link to="/dashboards/contractCategories/new" className="pure-menu-link">
                        create new contract category
                    </Link>
                </li>
            </ul>
        </div>
    }

}