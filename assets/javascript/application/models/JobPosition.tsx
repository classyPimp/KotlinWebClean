import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { HasOne } from '../../modelLayer/annotations/HasOne'
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { ModelCollection } from '../../modelLayer/ModelCollection'
import { RequestOptions, Route } from '../../modelLayer/annotations/ModelRoute'


export class JobPosition extends BaseModel {

    static className = "jobPosition"

    @Property
    id: number

    @Property
    updatedAt: string

    @Property
    createdAt: string

    @Property
    parentJobPositionId: number

    @Property
    name: string

    @Property
    isDepartment: string

    @Property
    isDepartmentHead: boolean

    @Property
    isUniquePosition: boolean

    @Property
    uniquenessIdentifier: string

    @HasMany("JobPosition")
    subordinatePositions: ModelCollection<JobPosition>

    @HasOne("JobPosition")
    parentPosition: JobPosition

    @Route("POST", {url: "/api/jobPosition"})
    create: (options?: RequestOptions) => Promise<JobPosition>

    @Route("GET", {url: "/api/jobPosition"})
    static index: (options?: RequestOptions) => Promise<ModelCollection<JobPosition>>


}

export class JobPositionTreeBuilder {
  
  private initialCollection: ModelCollection<JobPosition> = null
  private resultCollection: ModelCollection<JobPosition> = new ModelCollection<JobPosition>()
  private idToJobPositionMap: {[id: number]: JobPosition} = {}

  constructor(jobPositions: ModelCollection<JobPosition>) {
    this.initialCollection = jobPositions
    this.buildResultCollection()
  }

  getResult(): ModelCollection<JobPosition> {
    return this.resultCollection
  }

  private buildResultCollection() {
    this.buildIdToJobPositionMap()
    this.populateParentsWithChildren()
    this.populateResultExtractingJobPositionsWithNoParentJobPosition()    
  }

  private buildIdToJobPositionMap() {
    this.initialCollection.forEach((it)=>{
      this.idToJobPositionMap[it.id] = it
    })
  }

  private populateParentsWithChildren() {
    Object.keys(this.idToJobPositionMap).forEach((key)=>{
      let jobPositionAtKey = this.idToJobPositionMap[key as any]
      let parentJobPositionId = jobPositionAtKey.parentJobPositionId
      if (parentJobPositionId) {
        let parentJobPosition = this.idToJobPositionMap[parentJobPositionId]
        if (parentJobPosition) {
          parentJobPosition.subordinatePositions.push(jobPositionAtKey)
        }
      }
    })
  }

  private populateResultExtractingJobPositionsWithNoParentJobPosition() {
    this.initialCollection.forEach((it)=>{
      if (!it.parentJobPositionId) {
        this.resultCollection.push(it)
      }
    })
  }

}