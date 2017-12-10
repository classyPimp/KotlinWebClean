import autobind from 'autobind-decorator';
import { BaseReactComponent } from '../../../reactUtils/BaseReactComponent';
import * as React from 'react';
import { IFormElement } from '../../../reactUtils/plugins/formable/IFormElement';

export class FlashMessageQueue extends BaseReactComponent {

    state: {
        messages: Array<FlashMessage>
    } = {
        messages: new Array<FlashMessage>()
    }

    render(){
        return <div className="flashMessageQueue">
            {
                this.state.messages.map((message, index)=>{
                    return <div className="flashMessage" key={index}>
                        {message.element}
                        <button onClick={()=>{this.removeMessage(message)}}>
                            X
                        </button>
                    </div>
                })
            }
        </div>
    }

    @autobind
    addMessage(element: any){
        if (typeof element === 'string') {
            element = <div>
                {element}
            </div>
        }
        let message = new FlashMessage(element)
        this.state.messages.push(message)
        this.forceUpdate()
        message.timeOutFunction = window.setTimeout(()=>{
            this.removeMessage(message)
        }, 10000)
    }

    @autobind
    removeMessage(message: FlashMessage){
        window.clearTimeout(message.timeOutFunction)
        let messages = this.state.messages.filter((it)=>{
           return (it !== message)
        })
        this.setState({messages})
    }

}


class FlashMessage {

    element: any

    constructor(element: any) {
        this.element = element
    }

    timeOutFunction: any

}