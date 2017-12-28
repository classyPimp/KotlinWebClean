package routes

import controllers.HomeController
import controllers.contacttypes.ContactTypesController
import controllers.persons.PersonsController
import controllers.persontocounterpartylinkreasons.PersonToCounterPartyLinkReasonController
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

                namespace("/inputFeeds") {
                    get("/person") {
                        ContactTypesController.inputFeeds.forPerson(it).index()
                    }
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

                post("/:personId/contacts") {
                    PersonsController.contacts(it).create()
                }

                delete("/:personId/contacts/:id") {
                    PersonsController.contacts(it).delete()
                }

                put("/:personId/contacts/:id") {
                    PersonsController.contacts(it).update()
                }
            }

            namespace("/persontocounterpartylinkreasons") {
                post("") {
                    PersonToCounterPartyLinkReasonController(it).create()
                }

                get("") {
                    PersonToCounterPartyLinkReasonController(it).index()
                }

                get("/:id") {
                    PersonToCounterPartyLinkReasonController(it).show()
                }

                get("/:id/edit") {
                    PersonToCounterPartyLinkReasonController(it).edit()
                }

                put("/:id") {
                    PersonToCounterPartyLinkReasonController(it).update()
                }

                delete("/:id") {
                    PersonToCounterPartyLinkReasonController(it).destroy()
                }

            }

        }



    }

}

