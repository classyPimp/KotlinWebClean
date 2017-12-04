import { HasOne } from '../../modelLayer/annotations/HasOne';
import { BaseModel } from '../../modelLayer/BaseModel';
import { CompanyRepresentative } from './CompanyRepresentative';
import { Property } from '../../modelLayer/annotations/Property';


export class PowerDocument extends BaseModel {

    static className = "powerDocument"
    
    @Property
    id: number

    @Property
    companyRepresentativeId: number

    @Property
    fileName: string

    @Property
    fileMime: string

    @Property
    fileSize: string

    @HasOne(CompanyRepresentative)
    companyRepresentative: CompanyRepresentative


}