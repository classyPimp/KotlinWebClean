package utils.sessions

import utils.sessions.ISessionHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

interface ISessionHandlerFactory {

    fun create(
            request: HttpServletRequest,
            response: HttpServletResponse
    ): ISessionHandler

}