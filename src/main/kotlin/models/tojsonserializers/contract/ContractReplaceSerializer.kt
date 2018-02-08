package models..tojsonserializers.contract

import models..
import orm.generatedrepository.ToJsonSerializer

object ContractReplaceSerializer {

    fun onSuccess(: ): String {
        ToJsonSerializer().let {

            return it.serializeToString()
        }
    }

    fun onError(: ): String {
        ToJsonSerializer(). let {


            it.includeErrors()
            return it.serializeToString()
        }
    }

}