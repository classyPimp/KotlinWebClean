import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { IModelProperties } from '../../modelLayer/interfaces/IModelProperties';
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
    discussionMessages: ModelCollection<DiscussionMessage>

    @Route("GET", {url: "/api/discussion/:discussionId"})
    static show: (options?: RequestOptions) => Promise<Discussion>

    static afterShowRequest(options: RequestOptions) {
      options.deferredPromise.then((it)=>{
        let discussion = new Discussion(it['discussion'] as IModelProperties);
        let users = new ModelCollection<User>();
        (it['users'] as Array<IModelProperties>).forEach((userProps)=>{
          users.push(new User(userProps))
        })
        discussion.discussionMessages = Discussion.buildMessageTree(discussion.discussionMessages, users)
        return discussion
      })
    }

    static buildMessageTree(messages: ModelCollection<DiscussionMessage>, users: ModelCollection<User>): ModelCollection<DiscussionMessage> {
      let idToMessageMap: {[id:number]: DiscussionMessage} = {}
      let idToUserMap: {[id: number]: User} = {}
      let messagesToAssign = new ModelCollection<DiscussionMessage>()

      messages.forEach((it)=>{
        console.log(it)
        idToMessageMap[it.id] = it
        if (it.nestLevel === 0) {
          messagesToAssign.array.push(it)
        }
      })
      
      users.forEach((it)=>{
        idToUserMap[it.id] = it
      })
      
      messages.forEach((it)=>{
        let parentMessageId = it.discussionMessageId
        if (parentMessageId) {
          let parentMessage = idToMessageMap[parentMessageId]
          if (parentMessage) {
            parentMessage.childMessages.push(it)
          }
        }
        let user = idToUserMap[it.userId]
        if (user) {
          it.user = user
        }
      })
      return messagesToAssign
    }

}
