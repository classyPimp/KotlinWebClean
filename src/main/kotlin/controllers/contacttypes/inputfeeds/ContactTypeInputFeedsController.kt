package controllers.contacttypes.inputfeeds

import controllers.BaseController
import models.contacttype.ContactType
import models.contacttype.daos.ContactTypeDaos
import models.contacttype.tojsonserializers.ContactTypeSerializers
import org.jooq.generated.Tables.CONTACT_TYPES
import orm.contacttypegeneratedrepository.ContactTypeRecord
import router.src.ServletRequestContext

/**
 * Created by Муса on 21.12.2017.
 */
object ContactTypeInputFeedsController {

    fun forPerson(context: ServletRequestContext): ForPerson {
        return ForPerson(context)
    }

    fun forCounterParty(context: ServletRequestContext) : ForCounterParty {
        return ForCounterParty(context)
    }

     class ForPerson(context: ServletRequestContext) : BaseController(context) {
         fun index() {
             val contactTypes = ContactTypeDaos.index.getSpecificForPerson()

             renderJson(
                     ContactTypeSerializers.InputFeeds.person.onSuccess(contactTypes)
             )
         }
     }



     class ForCounterParty(context: ServletRequestContext) : BaseController(context) {
        fun index() {
            val contactTypes = ContactTypeDaos.index.getSpecificForCounterParty()

            renderJson(
                    ContactTypeSerializers.InputFeeds.counterParty.onSuccess(contactTypes)
            )
        }
     }

}