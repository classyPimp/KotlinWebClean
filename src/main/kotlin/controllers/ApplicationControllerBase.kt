package controllers

import utils.sessions.ISessionHandler
import utils.sessions.JwtSessionHandler

/**
 * Created by Муса on 14.11.2017.
 */
open class ApplicationControllerBase(
        context: router.src.ServletRequestContext
) : BaseController(context) {

    val session by lazy { JwtSessionHandler(this.context.request, this.context.response) }


}