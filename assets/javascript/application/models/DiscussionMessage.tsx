import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { HasOne } from '../../modelLayer/annotations/HasOne'
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { ModelCollection } from '../../modelLayer/ModelCollection'
import { RequestOptions, Route } from '../../modelLayer/annotations/ModelRoute'
import { Discussion } from './Discussion'
import { User } from './User'

export class DiscussionMessage extends BaseModel {

    static className = "discussionMessage"

    @Property
    id: number

    @Property
    updatedAt: string

    @Property
    createdAt: string

    @Property
    discussionId: number

    @Property
    discussionMessageId: number

    @Property
    userId: number

    @Property
    nestLevel: number

    @Property
    text: string

    @HasOne("Discussion")
    discussion: Discussion

    @HasMany("DiscussionMessage")
    childMessages: ModelCollection<DiscussionMessage>

    @HasOne("ParentMessage")
    parentMessage: DiscussionMessage

    @HasOne("User")
    user: User

}