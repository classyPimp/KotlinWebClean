import {New} from './New'
import {Edit} from './Edit'
import {Index} from './Index'
import { ShowGeneralInfo } from './ShowGeneralInfo';
import { New as ContactsNew } from './contacts/New'
import { Edit as ContactsEdit } from './contacts/Edit'
import { Main } from './Main'
import { ShowMain } from './ShowMain'
import { EditMain } from './EditMain'
import { IndexEdit as ContactIndexEdit } from './contacts/IndexEdit'
import { Show as ContactShow } from './contacts/Show'
import { Index as ContactIndex } from './contacts/Index'
import { SettingsMain } from './SettingsMain'

export let PersonsComponents = {
  New,
  Edit,
  Index,
  ShowGeneralInfo,
  Main,
  EditMain,
  ShowMain,
  SettingsMain,
  contacts: {
    New: ContactsNew,
    Edit: ContactsEdit,
    IndexEdit: ContactIndexEdit,
    Show: ContactShow,
    Index: ContactIndex,
  }
}