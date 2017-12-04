package orm.services

import java.lang.Exception

/**
 * Created by classypimp on 10/28/17.
 */
class ModelInvalidException(
        override val message: String?
) : Exception(message) {

}