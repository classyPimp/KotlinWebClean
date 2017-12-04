import { User } from './User';
import { Property } from '../../modelLayer/annotations/Property';
import { BaseModel } from '../../modelLayer/BaseModel';
import { HasOne } from '../../modelLayer/annotations/HasOne';

export class Account extends BaseModel {

    static className = "account"

    @Property
    id: number

    @Property
    email: string

    @Property
    password: string

    @HasOne(User)
    user: User

}