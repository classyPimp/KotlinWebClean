import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { JobPosition } from '../../models/JobPosition';
import { ModelCollection } from '../../../modelLayer/ModelCollection'
import autobind from 'autobind-decorator'
import { JobPositionTreeBuilder } from '../../models/JobPosition'
import { JobPositionComponents } from './JobPositionComponents'
import { Modal } from '../shared/Modal'

export class IndexEdit extends BaseReactComponent {

    state: {
      initialJobPositions: ModelCollection<JobPosition>
      jobPositionsPreparedForRender: ModelCollection<JobPosition>
    } = {
      initialJobPositions: null,
      jobPositionsPreparedForRender: new ModelCollection()
    }

    modal: Modal = null

    componentDidMount() {
      JobPosition.indexEdit().then((initialJobPositions)=>{
        let jobPositionsPreparedForRender = this.buildTree(initialJobPositions)
        this.setState({initialJobPositions, jobPositionsPreparedForRender})
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
          {!this.state.jobPositionsPreparedForRender.isNotEmpty() &&
            <button onClick={this.initDeparmentCreate}>
              add top level department
            </button>
          }
          {this.state.jobPositionsPreparedForRender.map((jobPosition)=>{
            return this.renderJobPosition(jobPosition)
          })}
        </div>
    }

    @autobind
    renderJobPosition(jobPosition: JobPosition): any {
      if (jobPosition.isDepartment) {
        return this.renderDepartment(jobPosition)
      }
      return <div key={jobPosition.id}>
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
    renderDepartment(jobPosition: JobPosition): any {
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

      return <div key={jobPosition.id}>
        <h3>
          {jobPosition.name}
        </h3>
        <p>
          department head:
        </p>
        {departmentHead
           ? <div>
             {departmentHead.name}
           </div>
           : <div>
             <button onClick={()=>{this.initDepartmentHeadCreate(jobPosition.id)}}>
               add department head
             </button>
           </div>
        }
        <p>
          subordinates:
        </p>
        <div>
          <button onClick={()=>{this.initSubordinateJobPositionToDeparmentCreate(jobPosition.id)}}>
            add subordinate
          </button>
          {subordinateJobPositions.map((subordinateJobPosition)=>{
            return this.renderJobPosition(subordinateJobPosition)
          })}
        </div>
        <button onClick={()=>{this.initSubordinateDepartmentCreate(jobPosition.id)}}>  
          add subordinate department
        </button>
        {subordinateDepartments &&
          subordinateDepartments.map((subordinateDepartment)=>{
            return this.renderJobPosition(subordinateDepartment)
          })
        }
      </div>
    }

    @autobind
    renderPlain(jobPosition: JobPosition): any {
      return <div>
        {jobPosition.name}
      </div>
    }


    @autobind
    initSubordinateJobPositionToDeparmentCreate(parentJobPositionId: number) {
      this.modal.open(
        <JobPositionComponents.New 
          onCreateSuccess={this.onJobPositionCreateSuccess}
          parentJobPositionId={parentJobPositionId}
        />
      )
    }


    @autobind
    initDeparmentCreate() {
      console.log("initDeparmentCreate")
       this.modal.open(
         <JobPositionComponents.New 
           onCreateSuccess={this.onJobPositionCreateSuccess}
           isDeparment = {true}
         />
       )
    }

    @autobind
    initDepartmentHeadCreate(parentJobPositionId: number) {
      this.modal.open(
        <JobPositionComponents.New 
          onCreateSuccess={this.onJobPositionCreateSuccess}
          parentJobPositionId={parentJobPositionId}
          isDepartmentHead = {true}
          isUniquePosition = {true}
        />
      )
    }

    @autobind
    initSubordinateDepartmentCreate(parentJobPositionId: number) {
      this.modal.open(
        <JobPositionComponents.New
          onCreateSuccess={this.onJobPositionCreateSuccess}
          parentJobPositionId={parentJobPositionId}
          isDeparment={true}        
        />
      )
    }

    @autobind
    onJobPositionCreateSuccess(jobPosition: JobPosition) {
      let initialJobPositions = this.state.initialJobPositions
      initialJobPositions.push(jobPosition)
      let jobPositionsPreparedForRender = this.buildTree(this.state.initialJobPositions)
      this.modal.close()
      this.setState({initialJobPositions, jobPositionsPreparedForRender})
    }


}
