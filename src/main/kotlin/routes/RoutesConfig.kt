package routes

import controllers.HomeController
import controllers.contacttypes.ContactTypesController
import controllers.persons.PersonsController
import controllers.sessions.SessionsController
import controllers.users.UsersController
import models.contact.Contact
import router.RoutesDrawer
import router.src.Router
import servletUtils.SimpleFileServer
import servletUtils.ToDefaultServletForwarder

/**
 * Created by Муса on 01.11.2017.
 */
class RoutesConfig(override val router: Router): RoutesDrawer(router) {

    override fun run(){

        get("/favicon.ico") {
            ToDefaultServletForwarder().forward(it)
        }

        get("/public/*") {
            SimpleFileServer(
                    App.component.publicFolderConfig().pathToPublicFolderParentDir
            ).serveFile(it)
        }

        get("/uploads/*") {
            SimpleFileServer(
                    ""
            )
        }

        get("/*") {
            HomeController(it).get()
        }

        namespace("/api") {
            namespace("/users") {
                post("/create") {
                    UsersController(it).create()
                }
            }

            namespace("/contactTypes") {
                post("") {
                    ContactTypesController(it).create()
                }

                get("") {
                    ContactTypesController(it).index()
                }

                get("/:id") {
                    ContactTypesController(it).get()
                }

                put("/:id") {
                    ContactTypesController(it).update()
                }

                delete("/:id") {
                    ContactTypesController(it).destroy()
                }
            }


            namespace("/sessions") {
                post("") {
                    SessionsController(it).create()
                }
                delete("") {
                    SessionsController(it).destroy()
                }
            }

            namespace("/persons") {
                post("") {
                    PersonsController(it).create()
                }

                get("") {
                    PersonsController(it).index()
                }

                get("/:id") {
                    PersonsController(it).get()
                }

                put("/:id") {
                    PersonsController(it).update()
                }

                delete("/:id") {
                    PersonsController(it).destroy()
                }
            }

        }



    }

}

