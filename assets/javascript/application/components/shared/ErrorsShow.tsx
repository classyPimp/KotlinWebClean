import {BaseReactComponent} from "../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import autobind from 'autobind-decorator'

export class ErrorsShow extends BaseReactComponent<{
  errors: Array<string>
}> {

    @autobind
    ifErrors(): boolean {
      return this.props.errors ? true : false
    }

    render(){
      return this.ifErrors()
        ? <div className="ErrorsShow">
            {this.props.errors.map((value, index)=>{
                    return <div className="individualError" key={index}>
                        {value}
                    </div>
            })}
        </div>
        : null  
    }

}