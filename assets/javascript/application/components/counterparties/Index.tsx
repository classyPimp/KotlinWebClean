import autobind from 'autobind-decorator';
import { ModelCollection } from '../../../modelLayer/ModelCollection';
import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react';
import { CounterParty } from '../../models/CounterParty';
import { Link } from 'react-router-dom';

export class Index extends BaseReactComponent {

    state: {
      counterParties: ModelCollection<CounterParty>
    } = {
      counterParties: new ModelCollection<CounterParty>()
    }

    componentDidMount(){
      CounterParty.index().then((counterParties: ModelCollection<CounterParty>)=>{
        this.setState({counterParties})
      })
    }

    render(){
        return <div>
          {this.state.counterParties.map((counterParty, index)=>{
            return <div key={counterParty.id}>

              <Link to={"/dashboards/counterParties/" + counterParty.id}>
                  {counterParty.name} 
              </Link>
              <p>
                short name: {counterParty.nameShort}
              </p>
              <p>
                incorporation form: {counterParty.incorporationForm.name} ({counterParty.incorporationForm.nameShort})
              </p>
              <Link to={`/dashboards/counterParties/${counterParty.id}/edit`}>
                  <button>
                      edit
                  </button>
              </Link>
              <button onClick={()=>{this.deleteCounterParty(counterParty)}}>
                  delete 
              </button>
            </div>
          })

          }
        </div>
    }

    @autobind
    deleteCounterParty(counterParty: CounterParty){
      counterParty.destroy().then((returnedCounterParty)=>{
        if (returnedCounterParty.isValid()){
          this.state.counterParties.filter((it)=>{
            let res = (it !== counterParty) 
            return res
          })
          this.setState({counterParties: this.state.counterParties})
        } else {
          alert("could not be deleted")
        }
      })
    }

}
