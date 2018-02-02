import { Property } from '../../modelLayer/annotations/Property'
import { BaseModel } from '../../modelLayer/BaseModel'
import { HasOne } from '../../modelLayer/annotations/HasOne'
import { HasMany } from '../../modelLayer/annotations/HasMany'
import { ModelCollection } from '../../modelLayer/ModelCollection'
import { RequestOptions, Route } from '../../modelLayer/annotations/ModelRoute'
import { Contract } from './Contract'

export class ContractCategory extends BaseModel {

    static className = "contractCategory"

    @Property
    id: number

    @Property
    name: string

    @Property
    description: string

    @Property
    updatedAt: string

    @Property
    createdAt: string

    @HasMany("Contract")
    contracts: ModelCollection<Contract>

    @Route("POST", {url: "/api/contractCategories"})
    create: (options?: RequestOptions) => Promise<ContractCategory>

    @Route("GET", {url: "/api/contractCategories"})
    static index: (options?: RequestOptions) => Promise<ModelCollection<ContractCategory>>

    @Route("GET", {url: "/api/contractCategories/:id"})
    static show: (options?: RequestOptions) => Promise<ContractCategory>

    @Route("GET", {url: "/api/contractCategories/:id/edit"})
    static edit: (options?: RequestOptions) => Promise<ContractCategory>

    @Route("PUT", {url: "/api/contractCategories/:id", defaultWilds: ["id"]})
    update: (options?: RequestOptions) => Promise<ContractCategory>

    @Route("DELETE", {url: "/api/contractCategories/:id", defaultWilds: ["id"]})
    destroy: (options?: RequestOptions) => Promise<ContractCategory>

    @Route("GET", {url: "/api/contractCategories/inputFeeds"})
    static inputFeedsIndex: (options?: RequestOptions)=>Promise<ModelCollection<ContractCategory>>

    static afterInputFeedsIndexRequest(options: RequestOptions) {
      this.afterIndexRequest(options)
    }

}