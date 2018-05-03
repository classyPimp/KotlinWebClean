import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { Router, Route, Link, match, Switch } from 'react-router-dom';
import { JobPositionComponents } from './JobPositionComponents'

export class Main extends BaseReactComponent {

  props: {
    match: match<any>
  }

  render(){
    return <div>
      <h3>
        company structure
      </h3>        
      <JobPositionComponents.IndexEdit />      
    </div>
  }

}
