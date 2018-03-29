package orm.services

import java.lang.Exception

/**
 * Created by classypimp on 10/28/17.
 */
class ModelInvalidError(
        override val message: String? = "invalid"
) : Exception(message) {

}