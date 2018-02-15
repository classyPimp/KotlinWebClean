import { BaseReactComponent } from "../../../../reactUtils/BaseReactComponent"
import * as React from 'react'
import { ApplicationComponent } from '../../ApplicationComponent';
import { match } from 'react-router-dom';
import { PlainInputElement } from '../../../../reactUtils/plugins/formable/formElements/PlainInput';
import { MixinFormableTrait } from '../../../../reactUtils/plugins/formable/MixinFormableTrait';
import autobind from 'autobind-decorator';
import { FlashMessageQueue } from '../../shared/FlashMessageQueue';
import { Modal } from '../../shared/Modal';
import { ContractToUploadedDocumentLinkComponents } from '../ContractToUploadedDocumentLinkComponents'
import { ContractToUploadedDocumentLink } from '../../../models/ContractToUploadedDocumentLink'
import { Link } from 'react-router-dom';
import { DropDownSelectServerFed } from '../../formelements/DropDownSelectServerFed'
import { CounterParty } from '../../../models/CounterParty'

export class Edit extends MixinFormableTrait(BaseReactComponent) {

    props: {
      contractToUploadedDocumentLink: ContractToUploadedDocumentLink
      onDelete: (contractToUploadedDocumentLink: ContractToUploadedDocumentLink) => any
    }

    state = {
      formDummy: new ContractToUploadedDocumentLink()
    }

    modal: Modal


    render(){

        let contractToUploadedDocumentLink = this.props.contractToUploadedDocumentLink
        
        return <div className="persontocounterpartylinkreasons-Edit">
          <Modal ref={(it)=>{this.modal = it}}/>
          <p>
            <Link to={contractToUploadedDocumentLink.uploadedDocument.fileUrl()}>
              {`${contractToUploadedDocumentLink.uploadedDocument.fileName}`}
            </Link>
          </p>
          <PlainInputElement
            model = {this.props.contractToUploadedDocumentLink}
            propertyName = "description"
            registerInput = {(it)=>{this.registerInput(it)}}
            optional = {{
              placeholder: "file description"
            }}
          />
          <button onClick={this.update}>
            update
          </button>
          <button onClick={this.delete}>
            destroy
          </button>
        </div>
    }
    

    @autobind
    update() {
      this.collectInputs()
      let currentContractToUploadedDocumentLink = this.props.contractToUploadedDocumentLink
      this.props.contractToUploadedDocumentLink.forContractManageUpdate().then((contractToUploadedDocumentLink)=>{
        if (contractToUploadedDocumentLink.isValid()) {
          currentContractToUploadedDocumentLink.description = contractToUploadedDocumentLink.description
        } else {
          currentContractToUploadedDocumentLink.errors = contractToUploadedDocumentLink.errors
        }
        this.forceUpdate()
      })

    }

    @autobind
    delete() {
      let contractToUploadedDocumentLink = this.props.contractToUploadedDocumentLink
      contractToUploadedDocumentLink.forContractManageDestroy().then((it)=>{
        if (it.isValid()) {
          this.props.onDelete(contractToUploadedDocumentLink) 
        } else {
          let errors = ""
          Object.keys(it.errors).forEach((key)=>{
            let error = it.errors[key]
            errors += (error + "; ") 
          })
          alert("could not be deleted: " + errors)
        }
      })
    }


}
