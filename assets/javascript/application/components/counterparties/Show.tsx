import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { CounterParty } from '../../models/CounterParty';
import { match } from 'react-router-dom';

export class Show extends BaseReactComponent {

    props: {
        match: match<any>,
        counterParty?: CounterParty
    }

    state: {
        counterParty: CounterParty
    } = {
        counterParty: null
    }

    componentDidMount(){
        if (this.props.counterParty) {
            return
        }

        let id = this.props.match.params.id
        
        CounterParty.show({wilds: {id}}).then((counterParty)=>{
            this.setState({counterParty})
        })
    }

    render(){
        return <div>
            {this.state.counterParty &&
                <div>
                  <p>name: {this.state.counterParty.name}</p>
                  <p> short name: {this.state.counterParty.nameShort} </p>                
                  <p>incorporation form: {this.state.counterParty.incorporationForm.nameShort}({this.state.counterParty.incorporationForm.name})</p>
                </div>
            }
        </div>
    }

}
