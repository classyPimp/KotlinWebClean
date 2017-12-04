package orm.templateDataModels.model

import javax.lang.model.element.Element

/**
 * Created by Муса on 16.11.2017.
 */
class DelegateFromRecordPropertyBean
constructor(
        val delegatesType: String,
        val element: Element,
        val property: String,
        val type: String,
        val nonNullableType: String
) {
    var parameters = mutableMapOf<String,String>()
        get(){
            if (delegatesType != "FUNCTION") {
                throw IllegalStateException()
            }
            return field
        }
}