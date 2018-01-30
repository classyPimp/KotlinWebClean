package composers.documenttemplates

import composers.documenttemplates.arbitrary.DocumentTemplateArbitraryCreateComposer
import composers.documenttemplates.prevalidations.PrevalidationsCreateComposer
import utils.requestparameters.IParam

/**
 * Created by Муса on 22.01.2018.
 */
object DocumentTemplateComposers {

    object Prevalidations {
        fun create(params: IParam): PrevalidationsCreateComposer {
            return PrevalidationsCreateComposer(params)
        }
    }

    object Arbitrary {
        fun create(params: IParam): DocumentTemplateArbitraryCreateComposer {
            return DocumentTemplateArbitraryCreateComposer(params)
        }
    }

    fun create(params: IParam): CreateComposer {
        return CreateComposer(params)
    }

    fun update(params: IParam, id: Long?): UpdateComposer {
        return UpdateComposer(params, id)
    }

    fun destroy(id: Long?): DestroyComposer {
        return DestroyComposer(id)
    }

}