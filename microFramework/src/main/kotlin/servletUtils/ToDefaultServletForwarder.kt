package servletUtils

import router.src.ServletRequestContext

/**
 * Created by Муса on 08.11.2017.
 */
class ToDefaultServletForwarder {

    fun forward(context: ServletRequestContext){
        context.request.session.servletContext.getNamedDispatcher("default").forward(context.request, context.response)
    }

}