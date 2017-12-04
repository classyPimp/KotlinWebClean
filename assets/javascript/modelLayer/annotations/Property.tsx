import { BaseModel } from '../BaseModel';
export function Property(
    target: BaseModel,
    propertyName: string
) {
    let getter = function() {
        return this.properties[propertyName]
    }

    let setter = function(valueToassign: any) {
        this.properties[propertyName] = valueToassign
    }
    
    Object.defineProperty(
        target,
        propertyName,
        {
            get: getter,
            set: setter
        }
    )
}