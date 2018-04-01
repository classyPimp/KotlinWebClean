import { New } from './New'
import { Show } from './Show'
import { Index } from './Index'
import { Edit } from './Edit'
import {Edit as UploadedDocumentsEdit} from './uploadeddocuments/Edit'
import {New as UploadedDocumentsNew} from './uploadeddocuments/New'
import {Index as UploadedDocumentsIndex} from './uploadeddocuments/Index'
import { Show as ForPersonShow } from './forperson/Show'
import { Index as ForPersonIndex } from './forperson/Index'
import { IndexEdit as ForCounterPartyIndexEdit } from './forcounterparty/IndexEdit'
import { New as ForCounterPartyNew } from './forcounterparty/New'
import { Main as ForCounterPartyMain } from './forcounterparty/Main'
import { ShowMain as ForCounterPartyShowMain } from './forcounterparty/ShowMain'
import { Index as ForCounterPartyIndex } from './forcounterparty/Index'
import { Show as ShowForCounterParty } from './forcounterparty/Show'
import { EditMain as ForCounterPartyEditMain } from './forcounterparty/EditMain'
import { Edit  as ForCounterPartyEdit } from './forcounterparty/Edit'
import { IndexEdit as UploadedDocumentsIndexEdit } from './uploadeddocuments/IndexEdit'
import { Main as ForPersonMain } from './forperson/Main'
import { New as ForPersonNew } from './forperson/New'


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