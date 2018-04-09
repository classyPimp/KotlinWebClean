import autobind from 'autobind-decorator';
import { BaseReactComponent } from '../../../reactUtils/BaseReactComponent';
import * as React from 'react';
import { JsxElement } from 'typescript';

export class Modal extends BaseReactComponent {

    static zIndexTracker = 1

    props: {
      isOpen?: Boolean
      childDiv?: Boolean
      onClose?: ()=>any
      children?: any
    }

    state: {
        zIndex?: number,
        isOpen: boolean
        childDiv?: any,
        modal: Modal,
    } = {
        isOpen: false,
        modal: null
    }

    componentWillMount(){
        
    }

    componentWillUnmount(){

    }

    constructor(...args: Array<any>) {
      super(...args)
      if (this.props.isOpen) {
        this.state.isOpen = true
      }
      if (this.props.childDiv) {
        this.state.childDiv = this.props.childDiv
      }
    }
 
    @autobind
    isVisible(): string {
        if(this.state.isOpen){
            return "block"
        } else {
            return "none"
        }
    }

    render(){
        return <div className="modal-wrapper" style={{display: this.isVisible(), zIndex: this.state.zIndex}}>
            <div className="modal">
                <div className="modal-head">
                    <button onClick={this.close}>
                        close
                    </button>
                </div>
                <div className="modal-body">
                    {this.state.childDiv}
                    {this.props.children}
                </div>
            </div>
        </div>
    }

    open(childDiv: any){
        Modal.zIndexTracker += 1
        this.setState({zIndex: Modal.zIndexTracker, isOpen: true, childDiv})
    }

    @autobind
    close(){
        Modal.zIndexTracker -= 1
        this.setState({isOpen: false, childDiv: null})
        if (this.props.onClose) {
          this.props.onClose()
        }
    }


   

}