package composers.counterparties

import composers.counterparties.contacts.CounterPartiesContactsCreateComposer
import composers.counterparties.contacts.CounterPartiesContactsDestroyComposer
import composers.counterparties.contacts.CounterPartiesContactsUpdateComposer
import utils.requestparameters.IParam

/**
 * Created by Муса on 03.01.2018.
 */
object CounterPartiesComposers {

    object Contacts {
        fun create(params: IParam, counterPartyId: Long?): CounterPartiesContactsCreateComposer {
            return CounterPartiesContactsCreateComposer(params, counterPartyId)
        }

        fun update(params: IParam, counterPartyId: Long?, id: Long?): CounterPartiesContactsUpdateComposer {
            return CounterPartiesContactsUpdateComposer(params, counterPartyId, id)
        }

        fun destroy(counterPartyId: Long?, id: Long?): CounterPartiesContactsDestroyComposer {
            return CounterPartiesContactsDestroyComposer(counterPartyId, id)
        }
    }

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