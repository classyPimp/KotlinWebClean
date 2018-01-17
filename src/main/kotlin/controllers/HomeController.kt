package controllers

import utils.requestparameters.querystring.QueryStringParametersWrapper

/**
 * Created by classypimp on 6/4/17.
 */


class HomeController(
        servletRequestContext: router.src.ServletRequestContext
) : ApplicationControllerBase(servletRequestContext) {

    fun get(){
        renderTemplate(
                "home/index.ftl",
                App.component.assetsPathProvider()
        )

        val qp = context.request.queryString
        QueryStringParametersWrapper().parse(qp).let {
            it.forEach {
                k,v ->
                println("$k - $v")
            }
        }

    }

}
