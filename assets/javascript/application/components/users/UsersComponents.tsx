import {Create} from "./registration/Create"
import {Main} from "./Main"
import {New} from "./sessions/New"
import { Index as ForSearchFormIndex } from './forSearchForm/Index'

export let UsersComponents = {

    main: Main,

    registration: {

         create: Create

    },

    sessions: {
        new: New
    },

    forsearchform: {
      Index: ForSearchFormIndex
    }

}