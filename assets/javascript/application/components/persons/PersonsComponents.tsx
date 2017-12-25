import {New} from './New'
import {Edit} from './Edit'
import {Index} from './Index'
import { Show } from './Show';
import { New as ContactsNew } from './contacts/New'
import { Edit as ContactsEdit } from './contacts/Edit'

export let PersonsComponents = {
  New,
  Edit,
  Index,
  Show,
  contacts: {
    New: ContactsNew,
    Edit: ContactsEdit
  }
}