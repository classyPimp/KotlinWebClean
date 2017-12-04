package router.src

import router.src.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by classypimp on 10/8/17.
 */
object Router {
    private var getTree: Tree = Tree()
    private var headTree: Tree = Tree()
    private var postTree: Tree = Tree()
    private var putTree: Tree = Tree()
    private var patchTree: Tree = Tree()
    private var deleteTree: Tree = Tree()
    
    var notFoundRouteHandler: ServletRequestHandler = {it.response.sendError(HttpServletResponse.SC_NOT_FOUND)}

    fun get(path: String, handler: ServletRequestHandler): Router {
        getTree.addRoute(path, handler)
        return this
    }

    fun head(path: String, handler: ServletRequestHandler): Router {
        headTree.addRoute(path, handler)
        return this
    }

    fun post(path: String, handler: ServletRequestHandler): Router {
        postTree.addRoute(path, handler)
        return this
    }

    fun put(path: String, handler: ServletRequestHandler): Router {
        putTree.addRoute(path, handler)
        return this
    }

    fun patch(path: String, handler: ServletRequestHandler): Router {
        patchTree.addRoute(path, handler)
        return this
    }

    fun delete(path: String, handler: ServletRequestHandler): Router {
        deleteTree.addRoute(path, handler)
        return this
    }

    fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        val routeSearcher = getTree.findHandlerFor(request.requestURI)
        processRouteSearcher(routeSearcher, request, response)
    }

    fun doPut(request: HttpServletRequest, response: HttpServletResponse) {
        val routeSearcher = putTree.findHandlerFor(request.requestURI)
        processRouteSearcher(routeSearcher, request, response)
    }

    fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        val routeSearcher = postTree.findHandlerFor(request.requestURI)
        processRouteSearcher(routeSearcher, request, response)
    }

    fun doHead(request: HttpServletRequest, response: HttpServletResponse) {
        val routeSearcher = headTree.findHandlerFor(request.requestURI)
        processRouteSearcher(routeSearcher, request, response)
    }

    fun doDelete(request: HttpServletRequest, response: HttpServletResponse) {
        val routeSearcher = deleteTree.findHandlerFor(request.requestURI)
        processRouteSearcher(routeSearcher, request, response)
    }

    fun doPatch(request: HttpServletRequest, response: HttpServletResponse) {
        val routeSearcher = deleteTree.findHandlerFor(request.requestURI)
        processRouteSearcher(routeSearcher, request, response)
    }

    private fun processRouteSearcher(
            routeSearcher: RouteSearcher,
            request: HttpServletRequest,
            response: HttpServletResponse
    ) {
        val foundServletRequestHandler = routeSearcher.foundServletRequestHandler
        if (foundServletRequestHandler != null) {
            foundServletRequestHandler(
                ServletRequestContext(
                    request, response, routeSearcher.routeParameters, routeSearcher.format
                )
            )
        } else {
            notFoundRouteHandler(ServletRequestContext(request, response, mutableMapOf(), null))
        }
    }
}