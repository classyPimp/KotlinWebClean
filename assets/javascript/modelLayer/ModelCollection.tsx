import { Callback } from 'awesome-typescript-loader/dist/paths-plugin';
import { BaseModel } from './BaseModel';

export class ModelCollection<T extends BaseModel> {

    array: Array<T>

    constructor(...values: Array<T>){
        this.array = values
    }

    push(value: T) {
        this.array.push(value)
    }

    pop(value: T) {
        this.array.pop()
    }

    forEach(lambda: (value: T)=>any){
        this.array.forEach((value)=>{
            lambda(value)
        })
    }

    map(callback: (it: T, index: number)=>any){
        return this.array.map(callback)
    }

    isNotEmpty(): boolean {
        return (this.array.length > 0) 
    }

    filter(lambda: (it: T)=>boolean) {
        this.array = this.array.filter(lambda)
    }

}

