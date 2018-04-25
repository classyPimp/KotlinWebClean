import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { JobPosition } from '../../models/JobPosition'
import { ModelCollection } from '../../../modelLayer/ModelCollection'
import autobind from 'autobind-decorator'
import { JobPositionTreeBuilder } from '../../models/JobPosition'

export class IndexEdit extends BaseReactComponent {

    state: {
      jobPositions: ModelCollection<JobPosition>
    } = {
      jobPositions: new ModelCollection()
    }

    componentDidMount() {
      JobPosition.index().then((jobPositions)=>{
        jobPositions = this.buildTree(jobPositions)
        this.setState({jobPositions})
      })
    }

    @autobind
    buildTree(jobPositions: ModelCollection<JobPosition>): ModelCollection<JobPosition> {
      return new JobPositionTreeBuilder(jobPositions).getResult()
    }

    render(){
        return <div>

        </div>
    }

    @autobind
    renderJobPosition(jobPosition: JobPosition): any {
      return <div>
        <p>
          {jobPosition.name}
        </p>
        {jobPosition.subordinatePositions.isNotEmpty() &&
          jobPosition.subordinatePositions.forEach((childPosition)=>{
            return <div key={childPosition.id}>
              {this.renderJobPosition(childPosition)}  
            </div>
          })
        }
      </div>
    }

}
