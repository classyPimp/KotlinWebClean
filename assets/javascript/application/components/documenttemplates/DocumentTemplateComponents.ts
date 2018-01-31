import { New } from './New'
import { Index } from './Index'
import { New as ArbitraryNew } from './arbitrary/New'

export let DocumentTemplateComponents = {
  New,
  Index,
  arbitrary: {
    New: ArbitraryNew
  }
}