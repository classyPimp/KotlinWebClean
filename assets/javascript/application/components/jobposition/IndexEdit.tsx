import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { JobPosition } from '../../models/JobPosition'
import { ModelCollection } from '../../../modelLayer/ModelCollection'
import autobind from 'autobind-decorator'
import { JobPositionTreeBuilder } from '../../models/JobPosition'
import { JobPositionComponents } from './JobPositionComponents'
import { Modal } from '../shared/Modal'

export class IndexEdit extends BaseReactComponent {

    state: {
      initialJobPositions: ModelCollection<JobPosition>
      jobPositionsToPreparedForRender: ModelCollection<JobPosition>
    } = {
      initialJobPositions: null,
      jobPositionsToPreparedForRender: new ModelCollection()
    }

    modal: Modal = null

    componentDidMount() {
      JobPosition.index().then((initialJobPositions)=>{
        let jobPositionsToPreparedForRender = this.buildTree(initialJobPositions)
        this.setState({initialJobPositions, jobPositionsToPreparedForRender})
      })
    }

    @autobind
    buildTree(jobPositions: ModelCollection<JobPosition>): ModelCollection<JobPosition> {
      return new JobPositionTreeBuilder(jobPositions).getResult()
    }

    render(){
        if (!this.state.initialJobPositions) {
          return <p>
            ...loading
          </p>
        }
        return <div>
          <Modal ref={(it)=>{this.modal = it}}/>
          {!this.state.jobPositionsToPreparedForRender.isNotEmpty() &&
            <button onClick={this.initDeparmentCreate}>
              add top level department
            </button>
          }
          {this.state.jobPositionsToPreparedForRender.map((jobPosition)=>{
            return this.renderJobPosition(jobPosition)
          })}
        </div>
    }

    @autobind
    renderJobPosition(jobPosition: JobPosition): any {
      if (jobPosition.isDepartment) {
        return this.renderDepartment(jobPosition)
      }
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

    @autobind
    renderDepartment(jobPosition: JobPosition) {
      let departmentHead: JobPosition = null
      let subordinateJobPositions = new ModelCollection<JobPosition>()
      let subordinateDepartments = new ModelCollection<JobPosition>()
      jobPosition.subordinatePositions.forEach((it)=>{
        if (it.isDepartmentHead) {
          departmentHead = it
        } else if (it.isDepartment) {
          subordinateDepartments.push(it)
        } else {
          subordinateJobPositions.push(it)
        }
      })
      return <div>
        <p>
          department head:
        </p>
        {departmentHead
          ? {this.renderPlain(departmentHead)} 
          : <div>
            <button>
              create department head position
            <button>
          </div>
        }

        <p>
          subordinates:
        </p>
      </div>
    }

    @autobind
    renderPlain(jobPosition: JobPosition): any {
      return <div>
        {jobPosition.name}
      </div>
    }


    @autobind
    initDeparmentCreate() {
       this.modal.open(
         <JobPositionComponents.New onCreateSuccess={this.onJobPositionCreateSuccess}/>
       )
    }

    @autobind
    onJobPositionCreateSuccess(jobPosition: JobPosition) {
      let initialJobPositions = this.state.initialJobPositions
      initialJobPositions.push(jobPosition)
      let jobPositionsToPreparedForRender = this.buildTree(this.state.initialJobPositions)
      this.setState({initialJobPositions, jobPositionsToPreparedForRender})
    }


}
