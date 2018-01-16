import { BaseReactComponent } from "../../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { ModelCollection } from '../../../../modelLayer/ModelCollection';
import autobind from 'autobind-decorator';
import { PersonToCounterPartyLinkToUploadedDocumentLink } from '../../../models/PersonToCounterPartyLinkToUploadedDocumentLink'
import {Modal} from '../../shared/Modal'
import { PersonToCounterPartyLinksComponents } from '../PersonToCounterPartyLinksComponents'


export class Index extends BaseReactComponent {

    props: {
      personToCounterPartyLinkId: number
      editMode: boolean
    }  

    state: {
      personToCounterPartyLinkToUploadedDocumentLinks: ModelCollection<PersonToCounterPartyLinkToUploadedDocumentLink> 
    } = {
      personToCounterPartyLinkToUploadedDocumentLinks: new ModelCollection()
    }

    modal: Modal

    componentDidMount(){
      PersonToCounterPartyLinkToUploadedDocumentLink.index({wilds: {personToCounterPartyLinkId: this.props.personToCounterPartyLinkId.toString()}}).then((personToCounterPartyLinkToUploadedDocumentLinks)=>{
        this.setState({personToCounterPartyLinkToUploadedDocumentLinks})
      })
    }

    render(){
        return <div>
          <Modal ref={(it)=>{this.modal = it}}/>
          {
            this.state.personToCounterPartyLinkToUploadedDocumentLinks.map((it, index)=>{
              return <div>
                reason name: /*it.personToCounterPartyLinkToUploadedDocLinkReason.reasonName*/
                file: {it.uploadedDocument.fileName} : {it.uploadedDocument.fileUrl()}}
                <button onClick={()=>{this.delete(it)}}>
                  delete
                </button>
              </div>
            })
          }
          {this.props.editMode &&
            <p>
              <button onClick={this.initDocumentLinkAddition}>
                add new file
              </button>
            </p>
          }
        </div>
    }

    @autobind
    initDocumentLinkAddition() {
      this.modal.open(
        <PersonToCounterPartyLinksComponents.uploadedDocuments.New
          personToCounterPartyLinkId={this.props.personToCounterPartyLinkId}
          onCreateSuccess={this.onDocumentLinkCreateSuccess}
          onCancel={this.onDocumentLinkAdditionCancel}
        />
      )
    }

    @autobind
    onDocumentLinkAdditionCancel(){
      this.modal.close()
    }

    @autobind
    onDocumentLinkCreateSuccess(link: PersonToCounterPartyLinkToUploadedDocumentLink) {
      this.state.personToCounterPartyLinkToUploadedDocumentLinks.push(link)
      this.modal.close()
      this.componentDidMount()
    }

    @autobind
    delete(link: PersonToCounterPartyLinkToUploadedDocumentLink) {
      link.destroy({wilds: {personToCounterPartyLinkId: this.props.personToCounterPartyLinkId.toString()}}).then((destroyedLink)=>{
        if (destroyedLink.isValid()) {
          this.state.personToCounterPartyLinkToUploadedDocumentLinks.filter((it)=>{
            return it !== link
          })
          this.setState({})
        }
      })
    }

}
