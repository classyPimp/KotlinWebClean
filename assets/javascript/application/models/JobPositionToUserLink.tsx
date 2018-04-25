import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { HasOne } from '../../modelLayer/annotations/HasOne'
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { ModelCollection } from '../../modelLayer/ModelCollection'
import { RequestOptions, Route } from '../../modelLayer/annotations/ModelRoute'

import { User } from './User'
import { JobPositionDelegation } from './JobPositionDelegation'

export class JobPositionToUserLink extends BaseModel {

    static className = "jobPositionToUserLink"

    @Property
    id: number

    @Property
    updatedAt: string

    @Property
    createdAt: string

    @Property
    userId: number
    @Property
    isDelegated: string
    @Property
    jobPositionDelegationId: number

        @HasOne("User")
        user: User
        @HasOne("JobPositionDelegation")
        jobPositionDelegation: JobPositionDelegation

}