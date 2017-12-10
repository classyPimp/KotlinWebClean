import { BaseReactComponent } from '../../../javascript/reactUtils/BaseReactComponent';
import {Link} from 'react-router-dom'
import * as React from 'react'

export class HilfHundApplicationComponent extends BaseReactComponent {
    
    render() {
        return <div>
            <Link to="/generators">
                generate files
            </Link>
        </div>
    }

}