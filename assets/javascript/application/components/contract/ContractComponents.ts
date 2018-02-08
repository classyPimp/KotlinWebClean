import { New } from './New'
import { Show } from './Show'
import { Edit } from './Edit'
import { Index } from './Index'
import { Show as ManageShow } from './manage/Show'

export let ContractComponents = {
  New,
  Show,
  Index,
  Edit,
  manage: {
    Show: ManageShow
  }
}