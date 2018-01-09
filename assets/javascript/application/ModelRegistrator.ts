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
    }
  }

}

