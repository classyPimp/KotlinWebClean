package orm.services

/**
 * Created by classypimp on 10/27/17.
 */
interface ModelValidationManagerTrait {
    var errors: MutableMap<String, MutableList<String>>?

    fun isValid(): Boolean {
        return (errors == null)
    }

    fun nonNullableErrors(): MutableMap<String, MutableList<String>> {
        val errorsList = errors ?: mutableMapOf()
        errors = errorsList
        return errorsList
    }

    fun nonNullableErrors(property: String): MutableList<String> {
        val errorsList = errors ?: mutableMapOf()
        errors = errorsList
        val propertyErrors = errorsList[property] ?: mutableListOf()
        errorsList[property] = propertyErrors
        return propertyErrors
    }

    fun addError(propertyName: String, message: String){
        val errorList = nonNullableErrors()[propertyName]
        if (errorList == null) {
            nonNullableErrors()[propertyName] = mutableListOf(message)
        } else {
            errorList.add(message)
        }
    }

    fun markAsHasNestedErrors() {
        val errorList = nonNullableErrors()["nestedErrors"]
        if (errorList == null) {
            nonNullableErrors()["nestedErrors"] = mutableListOf("has nested errors")
        }
    }

    fun addUniqueError(propertyName: String, message: String) {
        val errorList = nonNullableErrors()[propertyName]
        if (errorList  == null) {
            nonNullableErrors()[propertyName] = mutableListOf(message)
        } else {
            if (!errorList.contains(message)) {
                errorList.add(message)
            }
        }
    }

}