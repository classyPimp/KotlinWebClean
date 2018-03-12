import {Edit} from './Edit'
import { New as ContractManageNew } from './contractmanage/New'
import { Index as ContractManageIndex } from './contractmanage/Index'
import { Index as ForContractIndex } from './forcontract/Index'
import { IndexEdit as ForContractIndexEdit } from './forcontract/IndexEdit'
 
export let ContractToCounterPartyLinkComponents = {
  Edit,
  New: ContractManageNew,
  Index: ContractManageIndex,
  forContract: {
    Index: ForContractIndex,
    IndexEdit: ForContractIndexEdit
  }
}