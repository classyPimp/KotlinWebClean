import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { HasOne } from '../../modelLayer/annotations/HasOne'
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { ModelCollection } from '../../modelLayer/ModelCollection'
import { RequestOptions, Route } from '../../modelLayer/annotations/ModelRoute'
import { User } from './User'
import { DiscussionMessage } from './DiscussionMessage'
import { ApprovalRejection } from './ApprovalRejection'
 
export class Discussion extends BaseModel {

    static className = "discussion"

    @Property
    id: number

    @Property
    updatedAt: string

    @Property
    createdAt: string

    @Property
    topic: string

    @Property
    userId: number

    @Property
    discussableId: number

    @Property
    discussableType: string

    @HasOne("User")
    user: User

    @HasOne("ApprovalRejection")
    approvalRejection: ApprovalRejection

    @HasMany("DiscussionMessage")
    discussionMessage: DiscussionMessage

}