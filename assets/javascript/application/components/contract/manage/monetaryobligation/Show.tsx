import { BaseReactComponent } from "../../../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { MonetaryObligation } from '../../../../models/MonetaryObligation'

export class Show extends BaseReactComponent {

    props: {
      monetaryObligation: MonetaryObligation
    }

    render(){
        let monetaryObligation = this.props.monetaryObligation

        return <div>
          <h4>
            {monetaryObligation.description}
          </h4>
          {monetaryObligation.monetaryObligationParts.map((monetaryObligationPart)=>{
            return <div>
              <p>  
                {`${monetaryObligationPart.dueDate}: ${monetaryObligationPart.amount}`}
              </p>
            </div>
          })}          
        </div>
    }

}
