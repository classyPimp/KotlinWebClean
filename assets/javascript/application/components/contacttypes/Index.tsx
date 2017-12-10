import autobind from 'autobind-decorator';
import { ModelCollection } from '../../../modelLayer/ModelCollection';
import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react';
import { ContactType } from '../../models/ContactType';
import { Link } from 'react-router-dom';

export class Index extends BaseReactComponent {

    state: {
        contactTypes: ModelCollection<ContactType>
    } = {
        contactTypes: new ModelCollection<ContactType>()
    }

    componentDidMount(){
        ContactType.index().then((contactTypes: ModelCollection<ContactType>)=>{
            this.setState({
                contactTypes
            })
        })
    }

    render(){
        return <div className="contactTypes-Index">
            {this.state.contactTypes.map((contactType, index)=>{
                return <div key={contactType.id}>
                    <Link to={"/dashboards/contactTypes/" + contactType.id}>
                        {contactType.name} 
                        
                    </Link>
                    <Link to={`/dashboards/contactTypes/${contactType.id}/edit`}>
                        <button>
                            edit
                        </button>
                    </Link>
                    <button onClick={()=>{this.deleteContactType(contactType)}}>
                        delete 
                    </button>
                </div>
            })}
        </div>
    }

    @autobind
    deleteContactType(contactType: ContactType){
        contactType.delete().then((returnedContactType)=>{
            if (returnedContactType.isValid()) {
                this.state.contactTypes.filter((it)=>{
                    let res = (it !== contactType)
                    return res
                })                
                this.setState({contactTypes: this.state.contactTypes})
            } else {
                alert(`could not be deleted`)
            }
        })
    }

}
