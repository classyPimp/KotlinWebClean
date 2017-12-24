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
        var finalPath = path
        this.currentNameSpace?.let {
            finalPath = currentNameSpace + finalPath
        }
        router.get(finalPath, block)
    }

    fun post(path: String, block: (ServletRequestContext)->Unit) {
        var finalPath = path
        this.currentNameSpace?.let {
            finalPath = currentNameSpace + finalPath
        }
        router.post(finalPath, block)
    }

    fun put(path: String, block: (ServletRequestContext)->Unit) {
        var finalPath = path
        this.currentNameSpace?.let {
            finalPath = currentNameSpace + finalPath
        }
        router.put(finalPath, block)
    }


    fun delete(path: String, block: (ServletRequestContext)->Unit) {
        var finalPath = path
        this.currentNameSpace?.let {
            finalPath = currentNameSpace + finalPath
        }
        router.delete(finalPath, block)
    }

    open fun run() {

    }

}