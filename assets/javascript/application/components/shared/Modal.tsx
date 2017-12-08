import autobind from 'autobind-decorator';
import { BaseReactComponent } from '../../../reactUtils/BaseReactComponent';
import * as React from 'react';

class Modal extends BaseReactComponent {

    static zIndexTracker = 1

    state: {
      zIndex: number,
      isOpen: boolean
    }



    render(){
        return <div className="modal">
          {this.props.children}
        </div>
    }

    open(children: Array<any>){
        Modal.zIndexTracker += 1
        this.setState({zIndex: Modal.zIndexTracker})
    }

    close(){
        Modal.zIndexTracker -= 1
        this.setState({isOpen: false})
    }

    componentWillMount(){

    }

    componentWillUnmount(){

    }
   

}