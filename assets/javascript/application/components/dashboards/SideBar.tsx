import { BaseReactComponent } from '../../../reactUtils/BaseReactComponent';
import * as React from 'react'
import { Link } from 'react-router-dom';
import { LoginStatusComponent } from '../shared/LoginStatus'


export class SideBar extends BaseReactComponent {

    render() {
        return <div className="dashboards-Sidebar pure-menu custom-restricted-width">
            <LoginStatusComponent />
            <ul className="pure-menu-list">           
                <li className="pure-menu-item">
                    <Link to="/dashboards/persons" className="pure-menu-link">
                        persons
                    </Link>
                </li>
               <li className="pure-menu-item">
                    <Link to="/dashboards/counterParties" className="pure-menu-link">
                        counter parties
                    </Link>
                </li>
                <li className="pure-menu-item">
                    <Link to="/dashboards/documentTemplates" className="pure-menu-link">
                        document templates
                    </Link>
                </li>
                <li className="pure-menu-item">
                    <Link to="/dashboards/contracts" className="pure-menu-link">
                        contracts
                    </Link>
                </li>
                <li className="pure-menu-item">
                  <Link to="/dashboards/jobPosition">
                    company structure
                  </Link>
                </li>
            </ul>
        </div>
    }

}