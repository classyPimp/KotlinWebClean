import { New } from './New'
import { Index } from './Index'
import { New as ArbitraryNew } from './arbitrary/New'
import { Main } from './Main'
import { SettingsMain } from './SettingsMain'

export let DocumentTemplateComponents = {
  New,
  Index,
  Main,
  SettingsMain,
  arbitrary: {
    New: ArbitraryNew
  }
}