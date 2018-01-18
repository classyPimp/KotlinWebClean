package composers.documenttemplatevariables

import utils.requestparameters.IParam

/**
 * Created by Муса on 18.01.2018.
 */
object DocumentTemplateVariablesComposers {

    fun create(params: IParam) : CreateComposer {
        return CreateComposer(params)
    }

    fun update(params: IParam, id: Long?): UpdateComposer {
        return UpdateComposer(params, id)
    }

    fun destroy(id: Long?): DestroyComposer {
        return DestroyComposer(id)
    }

}

