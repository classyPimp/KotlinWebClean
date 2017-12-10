import autobind from 'autobind-decorator';
import { BaseReactComponent } from '../../../reactUtils/BaseReactComponent';
import * as React from 'react';
import { JsxElement } from 'typescript';

export class Modal extends BaseReactComponent {

    static zIndexTracker = 1

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
    }


   

}