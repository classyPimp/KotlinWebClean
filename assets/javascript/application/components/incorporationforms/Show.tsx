import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { IncorporationForm } from '../../models/IncorporationForm';
import { match } from 'react-router-dom';

export class Show extends BaseReactComponent {

    props: {
        match: match<any>,
        incorporationForm?: IncorporationForm
    }

    state: {
        incorporationForm: IncorporationForm
    } = {
        incorporationForm: null
    }

    componentDidMount(){
        if (this.props.incorporationForm) {
            return
        }

        let id = this.props.match.params.id
        
        IncorporationForm.show({wilds: {id}}).then((incorporationForm)=>{
            this.setState({incorporationForm})
        })
    }

    render(){
        return <div>
            {this.state.incorporationForm &&
                <div>
                  <p> name: {this.state.incorporationForm.name}</p>
                  <p> short name: {this.state.incorporationForm.nameShort} </p>
                </div>                
            }
        </div>
    }

}
