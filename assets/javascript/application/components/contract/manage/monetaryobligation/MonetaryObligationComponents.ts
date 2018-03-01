import { New } from './New'
import { New as MonetaryObligationPartsNew } from './monetaryobligationparts/New'
import { Edit as MonetaryObligationPartEdit } from './monetaryobligationparts/Edit'
import { Edit } from './Edit'
import { Index } from './Index'

export let MonetaryObligationComponents = {
  New,
  Edit,
  Index,
  MonetaryObligationParts: {
    New: MonetaryObligationPartsNew,
    Edit: MonetaryObligationPartEdit,
  }
}