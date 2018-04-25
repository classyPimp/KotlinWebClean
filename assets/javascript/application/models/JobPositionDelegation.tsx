import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { HasOne } from '../../modelLayer/annotations/HasOne'
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { ModelCollection } from '../../modelLayer/ModelCollection'
import { RequestOptions, Route } from '../../modelLayer/annotations/ModelRoute'

import { User } from './User'
import { JobPosition } from './JobPosition'

export class JobPositionDelegation extends BaseModel {

    static className = "jobPositionDelegation"

    @Property
    id: number

    @Property
    updatedAt: string

    @Property
    createdAt: string

    @Property
    delegatedPositionId: number
    @Property
    delegatedFromUserId: number
    @Property
    delegatedToUserId: number
    @Property
    delegatedSince: string
    @Property
    delegatedTill: string

        @HasOne("User")
        delegatedFromUser: User
        @HasOne("User")
        delegatedToUser: User
        @HasOne("JobPosition")
        delegatedPosition: JobPosition

}