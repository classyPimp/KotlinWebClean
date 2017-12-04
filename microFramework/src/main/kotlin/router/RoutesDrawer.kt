package router

import router.src.Router
import router.src.ServletRequestContext

/**
 * Created by Муса on 10.10.2017.
 */
open class RoutesDrawer(open val router: Router) {

    var currentNameSpace: String? = null

    fun namespace(
            namespaceName: String,
            block: () ->Unit
    ) {
        val nameSpaceSavePoint = this.currentNameSpace
        this.currentNameSpace = namespaceName
        nameSpaceSavePoint?.let {
            currentNameSpace = nameSpaceSavePoint + currentNameSpace
        }
        block.invoke()
        this.currentNameSpace = nameSpaceSavePoint
    }

    fun get(path: String, block: (ServletRequestContext)->Unit) {
        var path = path
        this.currentNameSpace?.let {
            path = currentNameSpace + path
        }
        router.get(path, block)
    }

    fun post(path: String, block: (ServletRequestContext)->Unit) {
        var path = path
        this.currentNameSpace?.let {
            path = currentNameSpace + path
        }
        router.post(path, block)
    }

    fun put(path: String, block: (ServletRequestContext)->Unit) {
        var path = path
        this.currentNameSpace?.let {
            path = currentNameSpace + path
        }
        router.put(path, block)
    }


    fun delete(path: String, block: (ServletRequestContext)->Unit) {
        var path = path
        this.currentNameSpace?.let {
            path = currentNameSpace + path
        }
        router.delete(path, block)
    }

    open fun run() {

    }

}