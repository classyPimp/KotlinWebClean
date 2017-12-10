import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { ContactType } from '../../models/ContactType';
import { match } from 'react-router-dom';

export class Show extends BaseReactComponent {

    props: {
        match: match<any>,
        contactType?: ContactType
    }

    state: {
        contactType: ContactType
    } = {
        contactType: null
    }

    componentDidMount(){
        if (this.props.contactType) {
            return
        }

        let id = this.props.match.params.id
        
        ContactType.get({wilds: {id}}).then((contactType)=>{
            this.setState({contactType})
        })
    }

    render(){
        return <div>
            {this.state.contactType &&
                <p> {this.state.contactType.name}</p>                
            }
        </div>
    }

}
