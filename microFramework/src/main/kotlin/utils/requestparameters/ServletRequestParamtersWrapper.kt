package utils.requestparameters
import org.apache.commons.fileupload.servlet.ServletFileUpload
import utils.requestparameters.json.JsonParametersParser
import java.lang.Exception
import javax.servlet.http.HttpServletRequest

/**
 * Created by Муса on 14.11.2017.
 */
class ServletRequestParamtersWrapper(
        val multiPartFormDataParametersParser: IRequestParametersParser,
        val jsonParametersParser: JsonParametersParser
) {
    val applicationJson: String = "application/json"

    fun createTree(request: HttpServletRequest, maxContentLength: Long? = null): IParam {
        if (ServletFileUpload.isMultipartContent(request)) {
            return multiPartFormDataParametersParser.parse(request, maxContentLength)
        } else if (request.contentType == applicationJson) {
            return jsonParametersParser.parse(request)
        } else {
            throw Exception()
        }
    }

}