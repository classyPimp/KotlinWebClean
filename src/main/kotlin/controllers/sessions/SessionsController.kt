package controllers.sessions

import composers.sessions.SessionsComposers
import controllers.ApplicationControllerBase
import controllers.BaseController
import orm.usergeneratedrepository.UserToJsonSerializer
import router.src.ServletRequestContext

/**
 * Created by Муса on 27.11.2017.
 */
class SessionsController(context: ServletRequestContext): ApplicationControllerBase(context) {

    fun create(){
        SessionsComposers.create(requestParams()).let {

            it.onSuccess = {
                user ->

                session.let {
                    it.logIn(user.id!!)
                    it.addToSession("name", user.name)
                    it.commit(60 * 60 * 24 * 365)
                }

                UserToJsonSerializer(user).let {
                    it.only { arrayOf(it.id, it.name) }
                    it.includeAccount() {
                        it.except { arrayOf(it.password) }
                    }
                    renderJson(it.serializeToString())
                }

            }

            it.onError = {
                user ->

                println("onError")
                UserToJsonSerializer(user).let {
                    it.includeAccount() {
                        it.includeErrors()
                    }
                    it.includeErrors()
                    renderJson(it.serializeToString())
                }
            }

            it.run()
        }
    }

    fun destroy() {
        session.let {
            it.logOut()
        }
    }

}