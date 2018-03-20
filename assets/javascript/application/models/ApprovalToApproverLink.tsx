import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { HasOne } from '../../modelLayer/annotations/HasOne'
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { ModelCollection } from '../../modelLayer/ModelCollection'
import { RequestOptions, Route } from '../../modelLayer/annotations/ModelRoute'
import { Approval } from './Approval'
import { User } from './User'

export class ApprovalToApproverLink extends BaseModel {

    static className = "approvalToApproverLink"

    @Property
    id: number

    @Property
    updatedAt: string

    @Property
    createdAt: string

    @Property
    approvalId: number

    @Property
    userId: number

    @HasOne("Approval")
    approval: Approval

    @HasOne("User")
    user: User

}