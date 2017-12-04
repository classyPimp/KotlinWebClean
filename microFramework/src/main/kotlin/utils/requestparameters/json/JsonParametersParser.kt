package utils.requestparameters.json

import com.fasterxml.jackson.databind.ObjectMapper
import utils.requestparameters.IParam
import utils.requestparameters.IRequestParametersParser
import javax.servlet.http.HttpServletRequest

/**
 * Created by Муса on 14.11.2017.
 */
class JsonParametersParser(
        val objectMapper: ObjectMapper
) : IRequestParametersParser {


    override fun parse(request: HttpServletRequest): IParam {
        objectMapper.readTree(request.inputStream).let {
            return JsonParam(it)
        }
    }

    override fun parse(request: HttpServletRequest, maxContentLength: Long?): IParam {
        throw Exception("unsupported")
    }

}