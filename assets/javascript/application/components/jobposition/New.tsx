import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { JobPosition } from '../../models/JobPosition'
import { MixinFormableTrait } from '../../../reactUtils/plugins/formable/MixinFormableTrait';
import { PlainInputElement } from '../../../reactUtils/plugins/formable/formElements/PlainInput'
import autobind from 'autobind-decorator'
import { PlainCheckbox } from '../formelements/PlainCheckbox'

export class New extends MixinFormableTrait(BaseReactComponent) {

    state: {
      jobPosition: JobPosition
    } = {
      jobPosition: new JobPosition({isUniquePosition: true})
    }

    props: {
      onCreateSuccess: (jobPosition: JobPosition)=>any
      parentJobPositionId?: number
      isUniquePosition?: boolean
    }

    constructor(...args: Array<any>) {
      super(...args)
      if (this.props.isUniquePosition) {
        this.state.jobPosition.isUniquePosition = this.props.isUniquePosition
      }
      if (this.props.parentJobPositionId) {
        this.state.jobPosition.parentJobPositionId = this.props.parentJobPositionId
      }
    }

    render(){
      return <div>
        <h2>
          create new job position
        </h2>
        <PlainInputElement
          model={this.state.jobPosition}
          propertyName="name"
          registerInput={(it)=>{this.registerInput(it)}}
          optional={{placeholder: "title"}}
        />
        <PlainCheckbox
          model={this.state.jobPosition}
          propertyName="isUniquePosition"
          registerInput={(it)=>{this.registerInput(it)}}
          optional = {{
            placeholder: "is unique position"
          }}
        />
        <button onClick={this.submit}>
          submit
        </button>
      </div>
    }

    @autobind
    submit() {
      this.collectInputs()
      this.state.jobPosition.create().then((jobPosition)=>{
        if (jobPosition.isValid()) {
          this.props.onCreateSuccess(jobPosition)
          return
        }
        this.setState({jobPosition})
      })
    }


}
