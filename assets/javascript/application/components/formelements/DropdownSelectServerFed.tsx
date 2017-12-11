import { BaseReactComponent } from '../../../reactUtils/BaseReactComponent';
import { IFormElement, IFormElementProps } from '../../../reactUtils/plugins/formable/IFormElement';
import { BaseModel } from '../../../modelLayer/BaseModel';

class DropDownSelectServerFed extends BaseReactComponent<IFormElementProps> implements IFormElement {

    id: number

    props: {
        model: BaseModel,
        propertyName: string,
        registerInput: (element: IFormElement)=>void,
        propertyToShow: string,
        selectProperty?: string
        ref?: (arg: any)=> void,
        optional?: {
            placeholder?: string
            [id:string]:any
        }  
    }

    isValid(): boolean{
        return true
    }

    render(){
        return <div className="formelements-DropDowndSelectServerFed">
            
        </div>
    }

    collect(){

    }

    clearInputs(){

    }


}

class BaseModelSelectChoiseWrapeer {
    model: BaseModel

    isSelected: boolean = false

    constructor(
        model: BaseModel, 
        propertyToShow: string,
        propertyToSelect: string,
        isSelected?: boolean
    ){
        this.propertyToShow = propertyToShow
        this.model = model
        if (isSelected) {
            this.isSelected = isSelected
        }
        this.propertyToSelect = propertyToSelect
    }

    propertyToSelect: string
    propertyToShow: string

    showProperty(): any{
        return this.model.properties[this.propertyToShow]
    }
    
    select(){
        this.isSelected = false
    }

    getSelectedValue(): any {
        if (this.isSelected) {
            return this.model.properties[this.propertyToSelect]
        }
    }
}

