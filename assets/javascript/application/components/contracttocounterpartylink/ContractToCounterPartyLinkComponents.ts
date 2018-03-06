import {Edit} from './Edit'
import { New as ContractManageNew } from './contractmanage/New'
import { Index as ContractManageIndex } from './contractmanage/Index'
import { Index as ForContractIndex } from './forcontract/Show'

 
export let ContractToCounterPartyLinkComponents = {
  Edit,
  New: ContractManageNew,
  Index: ContractManageIndex,
  forContract: {
    Index: ForContractIndex,
  }
}