package models.${classNameLowerCase}.tojsonserializers${jsonSerializerPackage!""}

import models.${classNameLowerCase}.${className}
import orm.${classNameLowerCase}generatedrepository.${className}ToJsonSerializer

object ${toJsonSerializerName} {

    fun onSuccess(${decapitalizedClassName}: ${className}): String {
        ${className}ToJsonSerializer(${decapitalizedClassName}).let {

            return it.serializeToString()
        }
    }

    fun onError(${decapitalizedClassName}: ${className}): String {
        ${className}ToJsonSerializer(${decapitalizedClassName}). let {


            it.includeErrors()
            return it.serializeToString()
        }
    }

}