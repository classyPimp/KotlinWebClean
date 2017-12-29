package composers.incorporationforms

import utils.requestparameters.IParam

/**
 * Created by Муса on 29.12.2017.
 */
object IncorporationFormsComposers {

    fun create(params: IParam): Create {
        return Create(params)
    }

    fun update(params: IParam, id: Long?): Update {
        return Update(params, id)
    }

    fun destroy(id: Long?): Destroy {
        return Destroy(id)
    }

}