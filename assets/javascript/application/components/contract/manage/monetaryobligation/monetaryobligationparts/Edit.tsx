import { BaseReactComponent } from "../../../../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { MonetaryObligationPart } from '../../../../../models/MonetaryObligationPart'
import { CounterParty } from '../../../../../models/CounterParty'
import { MixinFormableTrait } from '../../../../../../reactUtils/plugins/formable/MixinFormableTrait';
import { PlainInputElement } from '../../../../../../reactUtils/plugins/formable/formElements/PlainInput'
import { DropDownSelectServerFed } from '../../../../formelements/DropdownSelectServerFed'
import autobind from 'autobind-decorator'
import { ErrorsShow } from '../../../../shared/ErrorsShow'
import { ApplicationComponent } from '../../../../ApplicationComponent';

export class Edit extends MixinFormableTrait(BaseReactComponent) {

    props: {
      monetaryObligationPart: MonetaryObligationPart
      onInputChange: ()=>any
      onDelete: (monetaryObligationPart: MonetaryObligationPart) => any
      onDeleteCancel: (monetaryObligationPart: MonetaryObligationPart) => any
    }



    render(){
        return <div className="persontocounterpartylinkreasons-New">         
          <h2>
            initialize new monetaryObligationPart
          </h2>
          {this.props.monetaryObligationPart.getErrorsFor('general') &&
              <ErrorsShow errors={this.props.monetaryObligationPart.getErrorsFor('general')}/>
          }
          <PlainInputElement
            model={this.props.monetaryObligationPart}
            propertyName="dueDate"
            registerInput={(it)=>{this.registerInput(it)}}
            optional={{placeholder: "date due"}}
          />
          <PlainInputElement
            model={this.props.monetaryObligationPart}
            propertyName="amount"
            registerInput={(it)=>{this.registerInput(it)}}
            onInputChange = {this.props.onInputChange}
            optional={{placeholder: "amount"}}
            parseAsInt = {true}
          />
          {this.props.monetaryObligationPart.isMarkedForDestruction()
            ? <button onClick = {this.cancelDeletion}>
              cancel deletion
            </button>
            : <button onClick = {this.delete}>
              delete
            </button>
          }
        </div>
    }


    @autobind
    cancelDeletion(){
      this.props.onDeleteCancel(this.props.monetaryObligationPart)
    }

    @autobind
    delete() {
      this.props.onDelete(this.props.monetaryObligationPart)
    }

}
