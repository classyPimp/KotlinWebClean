import { BaseReactComponent } from "../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { Discussion } from '../../models/Discussion'
import { DiscussionMessage } from '../../models/DiscussionMessage'
import { Router, Route, Link, match, Switch } from 'react-router-dom';
import { MixinFormableTrait } from '../../../reactUtils/plugins/formable/MixinFormableTrait';
import { PlainInputElement } from '../../../reactUtils/plugins/formable/formElements/PlainInput'
import { ModelCollection } from '../../../modelLayer/ModelCollection'
import autobind from 'autobind-decorator'
import { Modal } from '../shared/Modal'

export class Show extends MixinFormableTrait(BaseReactComponent) {

    props: {
      match: match<any>
      history: any
    }

    state: {
      discussion: Discussion
      replyTo: number
      replyMessageFormModel: DiscussionMessage
    } = {
      discussion: null,
      replyTo: null,
      replyMessageFormModel: new DiscussionMessage
    }

    componentDidMount() {
      let discussionId = this.props.match.params.discussionId
      Discussion.show({wilds: {discussionId}}).then((discussion)=>{
        this.setState({discussion})
      })
    }

    render(){
        if (!this.state.discussion) {
          return <div>
            ...loading
          </div>
        }
        return <Modal
            isOpen = {true}
            onClose = {this.onModalClose}
          >
            <div>
              <div>
                <p>
                  topic: {this.state.discussion.topic}
                </p>
              </div>
              <div>
                {this.state.discussion.discussionMessages.map((discussionMessage)=>{
                  return this.renderMessage(discussionMessage)
                })}  
              </div>    
              {!this.state.replyTo &&
                <div>
                  {this.renderReplyBox()}
                </div>
              }    
            </div>
        </Modal>
    }

    @autobind
    onModalClose() {
      this.props.history.goBack()
    }

    @autobind
    renderMessage(discussionMessage: DiscussionMessage): any {
      return <div key={discussionMessage.id}>
        <p>
          aithor: {discussionMessage.user.name}
        </p>
        <p>
          {discussionMessage.text}
        </p>
        {!!discussionMessage.discussionMessageId && 
          <p>
            reply to {discussionMessage.discussionMessageId}
          </p>
        }
        <button onClick={()=>{this.setReplyTo(discussionMessage.id)}}>
          reply
        </button>
        {(this.state.replyTo === discussionMessage.id) &&
          this.renderReplyBox(true)
        }
        {discussionMessage.childMessages.map((childMessage)=>{
            return this.renderMessage(childMessage)
          })
        }
      </div>
    }

    @autobind
    setReplyTo(id: number) {
      let replyMessageFormModel = this.state.replyMessageFormModel
      replyMessageFormModel.discussionMessageId = id
      this.setState({replyTo: id, replyMessageFormModel})
    }

    @autobind
    renderReplyBox(replyMode: Boolean = false): any {
      return <div>
        <PlainInputElement
          model = {this.state.replyMessageFormModel}
          propertyName = "text"
          registerInput = {(it)=>{this.registerInput(it)}}
          optional = {{
            placeholder: "enter text"
          }}
        />
        <button onClick={this.submitMessage}>
          submit
        </button>
        {replyMode &&
          <button onClick={this.cancelReply}>
            cancel
          </button>
        }
      </div>      
    }

    @autobind
    cancelReply() {
      this.setState({replyTo: null})
    }

    @autobind
    submitMessage() {
      this.collectInputs()
      let discussionId = this.props.match.params.discussionId
      this.state.replyMessageFormModel.ofApprovalRejectionOfContractCreate({wilds: {discussionId}}).then((discussionMessage)=>{
        if (!discussionMessage.isValid()) {
          this.setState({discussionMessageForm: discussionMessage})
          return
        }
        let replyMessageFormModel = this.state.replyMessageFormModel
        replyMessageFormModel.properties = {}

        if (discussionMessage.discussionMessageId) {
          this.findAndAddChildToParentMessage(this.state.discussion.discussionMessages, discussionMessage)
        } else {
          this.state.discussion.discussionMessages.push(discussionMessage)
        }
        this.setState({replyMessageFormModel, replyTo: null})
      })
    }

    @autobind
    findAndAddChildToParentMessage(discussionMessages: ModelCollection<DiscussionMessage>, discussionMessage: DiscussionMessage): Boolean {
      for (let containedDiscussionMessage of discussionMessages.array) {
        if (discussionMessage.discussionMessageId === containedDiscussionMessage.id) {
          containedDiscussionMessage.childMessages.push(discussionMessage)
          console.log("found and pushed")
          return true
        }
        if (containedDiscussionMessage.childMessages.array.length > 0) {
          let found = this.findAndAddChildToParentMessage(containedDiscussionMessage.childMessages, discussionMessage)
          if (found) {
            return true
          } 
        }
      }
    }



}
