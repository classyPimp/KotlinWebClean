package models.contractcategory.tojsonserializers

import models.contractcategory.ContractCategory
import orm.contractcategorygeneratedrepository.ContractCategoryToJsonSerializer

object UpdateSerializer {

    fun onSuccess(contractCategory: ContractCategory): String {
        ContractCategoryToJsonSerializer(contractCategory).let {

            return it.serializeToString()
        }
    }

    fun onError(contractCategory: ContractCategory): String {
        ContractCategoryToJsonSerializer(contractCategory). let {


            it.includeErrors()
            return it.serializeToString()
        }
    }

}