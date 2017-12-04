package hilfhund.controllers

import controllers.BaseController
import router.src.ServletRequestContext

class HilfhundHomeController(context: ServletRequestContext): BaseController(context) {

    fun index(){
        renderTemplate(
                "hilfhund/index.ftl",
                App.component.assetsPathProvider()
        )
    }

}