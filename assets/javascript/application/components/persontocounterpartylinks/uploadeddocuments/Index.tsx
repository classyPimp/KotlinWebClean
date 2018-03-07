import { BaseReactComponent } from "../../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { ModelCollection } from '../../../../modelLayer/ModelCollection';
import autobind from 'autobind-decorator';
import { PersonToCounterPartyLinkToUploadedDocumentLink } from '../../../models/PersonToCounterPartyLinkToUploadedDocumentLink'
import {Modal} from '../../shared/Modal'
import { PersonToCounterPartyLinksComponents } from '../PersonToCounterPartyLinksComponents'
import { Router, Route, Link, match, Switch } from 'react-router-dom';


export class Index extends BaseReactComponent {

    props: {
      match?: match<any>
      personToCounterPartyLinkId?: number
    }  

    state: {
      personToCounterPartyLinkToUploadedDocumentLinks: ModelCollection<PersonToCounterPartyLinkToUploadedDocumentLink> 
    } = {
      personToCounterPartyLinkToUploadedDocumentLinks: new ModelCollection()
    }

    modal: Modal

    componentDidMount(){
      console.log("INDEX")
      let personToCounterPartyLinkId: any
      if (this.props.personToCounterPartyLinkId) {
        personToCounterPartyLinkId = this.props.personToCounterPartyLinkId
      } else {
        personToCounterPartyLinkId = this.props.match.params.personToCounterPartyLinkId
      }
      PersonToCounterPartyLinkToUploadedDocumentLink.index(
        {
          wilds: {personToCounterPartyLinkId}
        }
      ).then((personToCounterPartyLinkToUploadedDocumentLinks)=>{
        this.setState({personToCounterPartyLinkToUploadedDocumentLinks})
      })
    }

    render(){
      return <div>
        {this.state.personToCounterPartyLinkToUploadedDocumentLinks.map((it, index)=>{
          return <div key={it.id}>
            {it.personToCounterPartyLinkToUploadedDocLinkReason &&
              <p>
                reason: {it.personToCounterPartyLinkToUploadedDocLinkReason.reasonName}
              </p>
            }
            <p>
              file:  
              <a href={it.uploadedDocument.fileUrl()} className="pure-menu-link">
                {it.uploadedDocument.fileName}
              </a>
            </p>
          </div>
        })}
      </div>
    }

}

// export class Index extends BaseReactComponent {

//     props: {
//       personToCounterPartyLinkId: number
//       editMode: boolean
//       match: match<any>
//     }  

//     state: {
//       personToCounterPartyLinkToUploadedDocumentLinks: ModelCollection<PersonToCounterPartyLinkToUploadedDocumentLink> 
//     } = {
//       personToCounterPartyLinkToUploadedDocumentLinks: new ModelCollection()
//     }

//     modal: Modal

//     componentDidMount(){
//       PersonToCounterPartyLinkToUploadedDocumentLink.index(
//         {
//           wilds: {personToCounterPartyLinkId: this.props.match.params.personToCounterPartyLinkId}
//         }
//       ).then((personToCounterPartyLinkToUploadedDocumentLinks)=>{
//         this.setState({personToCounterPartyLinkToUploadedDocumentLinks})
//       })
//     }

//     render(){
//         return <div>
//           <Modal ref={(it)=>{this.modal = it}}/>
//           {
//             this.state.personToCounterPartyLinkToUploadedDocumentLinks.map((it, index)=>{
//               return <div key={it.id}>
//                 reason name: /*it.personToCounterPartyLinkToUploadedDocLinkReason.reasonName*/
//                 file:  
//                 <a href={it.uploadedDocument.fileUrl()} className="pure-menu-link">
//                   {it.uploadedDocument.fileName}
//                 </a>
//                 <button onClick={()=>{this.delete(it)}}>
//                   delete
//                 </button>
//               </div>
//             })
//           }
//           {this.props.editMode &&
//             <p>
//               <button onClick={this.initDocumentLinkAddition}>
//                 add new file
//               </button>
//             </p>
//           }
//         </div>
//     }

//     @autobind
//     initDocumentLinkAddition() {
//       this.modal.open(
//         <PersonToCounterPartyLinksComponents.uploadedDocuments.New
//           personToCounterPartyLinkId={this.props.personToCounterPartyLinkId}
//           onCreateSuccess={this.onDocumentLinkCreateSuccess}
//           onCancel={this.onDocumentLinkAdditionCancel}
//         />
//       )
//     }

//     @autobind
//     onDocumentLinkAdditionCancel(){
//       this.modal.close()
//     }

//     @autobind
//     onDocumentLinkCreateSuccess(link: PersonToCounterPartyLinkToUploadedDocumentLink) {
//       this.state.personToCounterPartyLinkToUploadedDocumentLinks.push(link)
//       this.modal.close()
//       this.componentDidMount()
//     }

//     @autobind
//     delete(link: PersonToCounterPartyLinkToUploadedDocumentLink) {
//       link.destroy({wilds: {personToCounterPartyLinkId: this.props.personToCounterPartyLinkId.toString()}}).then((destroyedLink)=>{
//         if (destroyedLink.isValid()) {
//           this.state.personToCounterPartyLinkToUploadedDocumentLinks.filter((it)=>{
//             return it !== link
//           })
//           this.setState({})
//         }
//       })
//     }

// }
