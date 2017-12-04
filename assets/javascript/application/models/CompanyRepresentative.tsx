import { HasMany } from '../../modelLayer/annotations/HasMany';
import { ModelCollection } from '../../modelLayer/ModelCollection';
import { Company } from './Company';
import { BaseModel } from '../../modelLayer/BaseModel';
import { Property } from '../../modelLayer/annotations/Property';
import { HasOne } from '../../modelLayer/annotations/HasOne';
import { PowerDocument } from './PowerDocument';


export class CompanyRepresentative extends BaseModel {

    static className = "companyRepresentative"

    @Property
    id: number

    @Property
    name: string

    @Property
    bin: string

    @Property
    identifyingDocument: string

    @Property
    companyId: string

    @HasOne(Company)
    company: Company

    @HasMany(PowerDocument)
    powerDocuments: ModelCollection<PowerDocument>

}