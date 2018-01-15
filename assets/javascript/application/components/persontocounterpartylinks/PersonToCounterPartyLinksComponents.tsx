import { New } from './New'
import { Show } from './Show'
import { Index } from './Index'
import { Edit } from './Edit'
import {Edit as UploadedDocumentsEdit} from './uploadeddocuments/Edit'
import {New as UploadedDocumentsNew} from './uploadeddocuments/New'
import {Index as UploadedDocumentsIndex} from './uploadeddocuments/Index'

export let PersonToCounterPartyLinksComponents = {
  New,
  Show,
  Index,
  Edit,
  uploadedDocuments: {
    Index: UploadedDocumentsIndex,
    New: UploadedDocumentsNew,
    Edit: UploadedDocumentsEdit,
  },
}