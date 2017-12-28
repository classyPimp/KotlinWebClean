import { BaseReactComponent } from '../../../reactUtils/BaseReactComponent';
import * as React from 'react'
import { Link } from 'react-router-dom';

export class SideBar extends BaseReactComponent {

    render() {
        return <div className="dashboards-sidebar pure-menu custom-restricted-width">
            <ul className="pure-menu-list">
                <li className="pure-menu-item">
                    <Link to="/dashboards/contactTypes/new" className="pure-menu-link">
                        new contact  type
                    </Link>
                    <Link to="/dashboards/contactTypes" className="pure-menu-link">
                        index contact  types
                    </Link>

                    <Link to="/dashboards/persons/new" className="pure-menu-link">
                        new person
                    </Link>
                    <Link to="/dashboards/persons" className="pure-menu-link">
                        index persons
                    </Link>

                    <Link to="/dashboards/persontocounterpartylinkreasons/new" className="pure-menu-link">
                        new person
                    </Link>
                    <Link to="/dashboards/persontocounterpartylinkreasons" className="pure-menu-link">
                        index persons
                    </Link>

                </li>
            </ul>
        </div>
    }

}