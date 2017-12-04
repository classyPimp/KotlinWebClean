import { Route } from '../../../javascript/modelLayer/annotations/ModelRoute';
import { Property } from '../../../javascript/modelLayer/annotations/Property';
import { BaseModel } from "../../../javascript/modelLayer/BaseModel";
import { RequestOptions } from 'https';




export class Migration extends BaseModel {

    @Property
    name: string

    @Property
    options: string

    @Route("POST", {url: "/hilfhund/migrations/create"})
    createMigration: (options?: RequestOptions) => Promise<any>

    @Route("POST", {url: "/hilfhund/migrations/migrate"})
    static migrate: (options?: RequestOptions)=> Promise<any>

    @Route("POST", {url: "/hilfhund/migrations/rollback"})
    static rollback: (options?: RequestOptions) => Promise<any>

}