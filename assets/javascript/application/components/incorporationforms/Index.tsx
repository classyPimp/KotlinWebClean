import autobind from 'autobind-decorator';
import { ModelCollection } from '../../../modelLayer/ModelCollection';
import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react';
import { IncorporationForm } from '../../models/IncorporationForm';
import { Link } from 'react-router-dom';

export class Index extends BaseReactComponent {

    state: {
        incorporationForms: ModelCollection<IncorporationForm>
    } = {
        incorporationForms: new ModelCollection<IncorporationForm>()
    }

    componentDidMount(){
        IncorporationForm.index().then((incorporationForms: ModelCollection<IncorporationForm>)=>{
            this.setState({
                incorporationForms
            })
        })
    }

    render(){
        return <div className="incorporationForms-Index">
            {this.state.incorporationForms.map((incorporationForm, index)=>{
                return <div key={incorporationForm.id}>
                    <Link to={"/dashboards/incorporationForms/" + incorporationForm.id}>
                        name: {incorporationForm.name} 
                        <br/>
                        short name: {incorporationForm.nameShort}
                    </Link>
                    <Link to={`/dashboards/incorporationForms/${incorporationForm.id}/edit`}>
                        <button>
                            edit
                        </button>
                    </Link>
                    <button onClick={()=>{this.deleteIncorporationForm(incorporationForm)}}>
                        delete 
                    </button>
                </div>
            })}
        </div>
    }

    @autobind
    deleteIncorporationForm(incorporationForm: IncorporationForm){
        incorporationForm.destroy().then((returnedIncorporationForm)=>{
            if (returnedIncorporationForm.isValid()) {
                this.state.incorporationForms.filter((it)=>{
                    let res = (it !== incorporationForm)
                    return res
                })                
                this.setState({incorporationForms: this.state.incorporationForms})
            } else {
                alert(`could not be deleted`)
            }
        })
    }

}
