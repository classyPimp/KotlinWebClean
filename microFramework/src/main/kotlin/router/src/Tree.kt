package router.src


class Tree() {

    private val root = PlainNode()

    fun addRoute(path: String, handler: ServletRequestHandler) {
        RouteAdder(path, handler, root).add()
    }

    fun findHandlerFor(uriPath: String): RouteSearcher {
        return  RouteSearcher(uriPath, root).find()
    }

}