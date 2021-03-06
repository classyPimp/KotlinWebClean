import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { HasOne } from '../../modelLayer/annotations/HasOne'
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { ModelCollection } from '../../modelLayer/ModelCollection'
import { RequestOptions, Route } from '../../modelLayer/annotations/ModelRoute'
import { Contract } from './Contract'
import { MonetaryObligationPart } from './MonetaryObligationPart'

export class MonetaryObligation extends BaseModel {

    static className = "monetaryObligation"

    @Property
    id: number

    @Property
    totalAmount: number

    @Property
    description: string

    @Property
    isCredit: Boolean

    @Property
    contractId: number

    @Property
    updatedAt: string

    @Property
    createdAt: string

    @HasOne("Contract")
    contract: Contract

    @HasMany("MonetaryObligationPart")
    monetaryObligationParts: ModelCollection<MonetaryObligationPart>

    @Route("POST", {url: "/api/contracts/:contractId/manage/monetaryObligations", defaultWilds: ["contractId"]})
    create: (options?: RequestOptions) => Promise<MonetaryObligation>

    @Route("GET", {url: "/api/contracts/:contractId/manage/monetaryObligations"}) 
    static forContractManageIndex: (options?: RequestOptions) => Promise<MonetaryObligation>

    static afterForContractManageIndexRequest(options: RequestOptions) {
      this.afterIndexRequest(options)
    }
    
    @Route("PUT", {url: "/monetaryObligations/:id", defaultWilds: ["id"]})
    update: (options?: RequestOptions) => Promise<MonetaryObligation>
    
}