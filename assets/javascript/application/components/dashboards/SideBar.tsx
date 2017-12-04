import { BaseReactComponent } from '../../../reactUtils/BaseReactComponent';
import * as React from 'react'
import { Link } from 'react-router-dom';

export class SideBar extends BaseReactComponent {

    render() {
        return <div className="dashboards-sidebar pure-menu custom-restricted-width">
            <ul className="pure-menu-list">
                <li className="pure-menu-item">
                    <Link to="/dashboards/contragents/new" className="pure-menu-link">
                        new contragent
                    </Link>
                </li>
            </ul>
        </div>
    }

}