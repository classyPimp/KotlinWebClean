import { New } from './New'
import { Show } from './Show'
import { Index } from './Index'
import { Edit } from './Edit'
import {Edit as UploadedDocumentsEdit} from './uploadeddocuments/Edit'
import {New as UploadedDocumentsNew} from './uploadeddocuments/New'
import {Index as UploadedDocumentsIndex} from './uploadeddocuments/Index'
import { Show as ForPersonShow } from './forPerson/Show'
import { Index as ForPersonIndex } from './forPerson/Index'
import { IndexEdit as ForCounterPartyIndexEdit } from './forcounterparty/IndexEdit'
import { New as ForCounterPartyNew } from './forCounterParty/New'
import { Main as ForCounterPartyMain } from './forCounterParty/Main'
import { ShowMain as ForCounterPartyShowMain } from './forCounterParty/ShowMain'
import { Index as ForCounterPartyIndex } from './forCounterParty/Index'
import { Show as ShowForCounterParty } from './forCounterParty/Show'
import { EditMain as ForCounterPartyEditMain } from './forCounterParty/EditMain'
import { Edit  as ForCounterPartyEdit } from './forCounterParty/Edit'
import { IndexEdit as UploadedDocumentsIndexEdit } from './uploadeddocuments/IndexEdit'
import { Main as ForPersonMain } from './forPerson/Main'
import { New as ForPersonNew } from './forPerson/New'


export let PersonToCounterPartyLinksComponents = {
  New,
  Show,
  Index,
  Edit,
  uploadedDocuments: {
    Index: UploadedDocumentsIndex,
    New: UploadedDocumentsNew,
    Edit: UploadedDocumentsEdit,
    IndexEdit: UploadedDocumentsIndexEdit
  },
  forPerson: {
    Index: ForPersonIndex,
    Show: ForPersonShow,
    New: ForPersonNew,
    Main: ForPersonMain,
  },
  forCounterParty: {
    IndexEdit: ForCounterPartyIndexEdit,
    New: ForCounterPartyNew,
    Main: ForCounterPartyMain,
    ShowMain: ForCounterPartyShowMain,
    Index: ForCounterPartyIndex,
    Show: ShowForCounterParty,
    EditMain: ForCounterPartyEditMain,
    Edit: ForCounterPartyEdit
  }
}