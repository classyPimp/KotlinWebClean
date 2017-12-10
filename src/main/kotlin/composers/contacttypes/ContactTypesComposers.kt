package composers.contacttypes

import utils.requestparameters.IParam

object ContactTypesComposers {

    fun create(requestParams: IParam): Create {
        return Create(requestParams)
    }

    fun update(requestParams: IParam, id: Long?): Update {
        return Update(requestParams, id)
    }

    fun destroy(id: Long?): Destroy {
        return Destroy(id)
    }

}
