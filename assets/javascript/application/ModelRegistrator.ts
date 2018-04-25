import {ModelRegistry} from '../modelLayer/ModelRegistry'

import { CounterPartyToContactLink } from './models/CounterPartyToContactLink'
import { Account } from './models/Account'
import { Avatar } from './models/Avatar'
import { Contact } from './models/Contact'
import { ContactType } from './models/ContactType'
import { CounterParty } from './models/CounterParty'
import { IncorporationForm } from './models/IncorporationForm'
import { Person } from './models/Person'
import { PersonToContactLink } from './models/PersonToContactLink'
import { PersonToCounterPartyLink } from './models/PersonToCounterPartyLink'
import { PersonToCounterPartyLinkReason } from './models/PersonToCounterPartyLinkReason'
import { PersonToCounterPartyLinkToUploadedDocLinkReason } from './models/PersonToCounterPartyLinkToUploadedDocLinkReason'
import { PersonToCounterPartyLinkToUploadedDocumentLink } from './models/PersonToCounterPartyLinkToUploadedDocumentLink'
import { UploadedDocument } from './models/UploadedDocument'
import { User } from './models/User'
import { Contract } from './models/Contract'
import { ContractToUploadedDocumentLink } from './models/ContractToUploadedDocumentLink'
import { DocumentTemplate } from './models/DocumentTemplate'
import { DocumentTemplateToDocumentVariableLink } from './models/DocumentTemplateToDocumentVariableLink'
import { DocumentTemplateVariable } from './models/DocumentTemplateVariable'
import  { DocumentTemplateCategory } from './models/DocumentTemplateCategory'
import { ContractNumber } from './models/ContractNumber'
import { ContractCategory } from './models/ContractCategory'
import { ContractStatus } from './models/ContractStatus'
import { ContractToCounterPartyLink } from './models/ContractToCounterPartyLink'
import { ContractToUploadedDocumentLinkReason } from './models/ContractToUploadedDocumentLinkReason'
import { MonetaryObligation } from './models/MonetaryObligation'
import { MonetaryObligationPart } from './models/MonetaryObligationPart'
import { Discussion } from './models/Discussion'
import { DiscussionMessage } from './models/DiscussionMessage'
import { Approval } from './models/Approval'
import { ApprovalStep } from './models/ApprovalStep'
import { ApprovalStepToApproverLink } from './models/ApprovalStepToApproverLink'
import { ApprovalRejection } from './models/ApprovalRejection'
import { ApprovalStepToUploadedDocumentLink } from './models/ApprovalStepToUploadedDocumentLink'  
import { ApprovalRejectionToUploadedDocumentLink } from './models/ApprovalRejectionToUploadedDocumentLink'
import { JobPosition } from './models/JobPosition'

export class ModelRegistrator {
  //THIS UGLY HACK SOLVES SOME NASTY CIRCULAR DEPENDENCIES BUGS!
  static run(){
    ModelRegistry.registeredModels = {
      Account,
      Avatar,
      Contact,
      ContactType,
      CounterParty,
      CounterPartyToContactLink,
      IncorporationForm,
      Person,
      PersonToContactLink,
      PersonToCounterPartyLink,
      PersonToCounterPartyLinkReason,
      PersonToCounterPartyLinkToUploadedDocLinkReason,
      PersonToCounterPartyLinkToUploadedDocumentLink,
      UploadedDocument,
      User,
      Contract,
      ContractToUploadedDocumentLink,
      DocumentTemplate,
      DocumentTemplateToDocumentVariableLink,
      DocumentTemplateVariable,
      DocumentTemplateCategory,
      ContractNumber,
      ContractCategory,
      ContractStatus,
      ContractToCounterPartyLink,
      ContractToUploadedDocumentLinkReason,
      MonetaryObligation,
      MonetaryObligationPart,
      Discussion,
      DiscussionMessage,
      Approval,
      ApprovalStep,
      ApprovalStepToApproverLink,
      ApprovalRejection,
      ApprovalStepToUploadedDocumentLink,
      ApprovalRejectionToUploadedDocumentLink, 
      JobPosition,    
                  
    }
  }

}

