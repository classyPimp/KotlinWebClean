package controllers

import dependencymanagement.MicroFrameworkDependencyManager
import utils.requestparameters.IParam
import java.io.PrintWriter
import java.io.StringWriter

/**
 * Created by Муса on 06.10.2017.
 */
abstract class BaseController(
       val context: router.src.ServletRequestContext
) {
    fun requestParams(maxContentLength: Long? = null): IParam {
        return MicroFrameworkDependencyManager.provider.servletRequestParametersWrapper.createTree(context.request, maxContentLength)
    }

    fun routeParams(): Map<String, String> {
        return context.routeParameters
    }

    fun renderTemplate(
            templateName: String,
            dataModel: Any
    ){
        MicroFrameworkDependencyManager.provider.templateProcessor.process(
                templateName,
                dataModel,
                context.response.writer
        )
        context.response.writer.close()
    }

    fun renderTemplateToString(
            templateName: String,
            dataModel: Any
    ): String {
        val stringWriter = StringWriter()
        val stringPrintWiriter = PrintWriter(stringWriter)
        MicroFrameworkDependencyManager.provider.templateProcessor.process(
                templateName,
                dataModel,
                stringPrintWiriter
        )
        stringPrintWiriter.let {
            it.flush()
            it.close()
        }
        return stringWriter.toString()
    }

    fun requestQueryStringParams(): Map<String,String> {
        return MicroFrameworkDependencyManager.provider.queryStringParametersWrapper.parse(context.request.queryString)
    }

    fun sendError(errorCode: Int) {
        context.response.sendError(errorCode)
    }

    fun renderJson(body: String) {
        context.response.let {
            it.contentType = "application/json"
            it.characterEncoding = "UTF-8"
            it.writer.print(body)
            it.writer.close()
        }
    }

}






