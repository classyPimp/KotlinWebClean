import { Show as ManageShow } from './manage/Show'
import { New as ManageNew } from './manage/New'
import { Edit as ManageEdit } from './manage/Edit'
import { Index as ManageIndex } from './manage/Index'

export let ContractToUploadedDocumentLinkComponents = {
  manage: {
    Show: ManageShow,
    Edit: ManageEdit,
    New: ManageNew,
    Index: ManageIndex
  }
}