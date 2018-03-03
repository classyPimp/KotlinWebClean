import { New } from './New'
import { ShowGeneralInfo } from './ShowGeneralInfo'
import { Edit } from './Edit'
import { Index } from './Index'
import { Show as ManageShow } from './manage/Show'
import { Main } from './Main'
import { SettingsMain } from './SettingsMain'
import { ShowMain } from './ShowMain'

export let ContractComponents = {
  New,
  ShowGeneralInfo,
  ShowMain,
  Index,
  Edit,
  Main,
  SettingsMain,
  manage: {
    Show: ManageShow
  }
}