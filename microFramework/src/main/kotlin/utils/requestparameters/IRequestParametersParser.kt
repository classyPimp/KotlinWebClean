package utils.requestparameters

import javax.servlet.http.HttpServletRequest

/**
 * Created by Муса on 14.11.2017.
 */
interface IRequestParametersParser {

    fun parse(request: HttpServletRequest): IParam

    fun parse(request: HttpServletRequest, maxContentLength: Long?): IParam

}