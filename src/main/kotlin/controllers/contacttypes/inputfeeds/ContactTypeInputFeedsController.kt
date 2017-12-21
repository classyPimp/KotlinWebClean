package controllers.contacttypes.inputfeeds

import controllers.BaseController
import models.contacttype.ContactType
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
             val contactTypes = ContactTypeRecord.GET().where(
                     CONTACT_TYPES.IS_SPECIFIC_FOR_TYPE.isNull
                             .or(CONTACT_TYPES.IS_SPECIFIC_FOR_TYPE.eq(ContactType.Companion.IsSpecificForTypeAllowedValues.person))
             ).execute()

             renderJson(
                     ContactTypeSerializers.InputFeeds.person.onSuccess(contactTypes)
             )
         }
     }

     class ForCounterParty(context: ServletRequestContext) : BaseController(context) {

     }

}