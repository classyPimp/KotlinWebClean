
import { Main } from './Main';
import { New } from './New'
import { Edit } from './Edit'
import { Show } from './Show'
import { Index } from './Index'

import { Edit as ContactsEdit } from './contacts/Edit'
import { Index as ContactsIndex } from './contacts/Index'
import { New as ContactsNew } from './contacts/New'
import { Show as ContactsShow } from './contacts/Show'

export let CounterPartiesComponents =  {
    
    main: Main,
    New,
    Edit,
    Show,
    Index,
    contacts: {
      Edit: ContactsEdit,
      Index: ContactsIndex,
      New: ContactsNew,
      Show: ContactsShow
    }

}