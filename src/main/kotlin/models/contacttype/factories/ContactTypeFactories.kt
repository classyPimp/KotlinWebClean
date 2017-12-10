package models.contacttype.factories

import models.contacttype.ContactType
import utils.requestparameters.IParam

object ContactTypeFactories {

    fun create(params: IParam): ContactType {
        return Create.create(params)
    }

}