import {Edit} from './Edit'
import { New as ContractManageNew } from './contractmanage/New'
import { Index as ContractManageIndex } from './contractmanage/Index'
 
export let ContractToCounterPartyLinkComponents = {
  Edit,
  New: ContractManageNew,
  Index: ContractManageIndex
}