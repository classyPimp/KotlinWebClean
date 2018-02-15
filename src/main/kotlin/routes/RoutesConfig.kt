package routes

import controllers.HomeController
import controllers.contacttypes.ContactTypesController
import controllers.contract.ContractController
import controllers.contractcategories.ContractCategoriesController
import controllers.contracttouploadeddocumentlinkreason.ContractToUploadedDocumentLinkReasonController
import controllers.counterparties.CounterPartiesController
import controllers.documenttemplatecategories.DocumentTemplateCategoriesController
import controllers.documenttemplates.DocumentTemplatesController
import controllers.documenttemplatevariables.DocumentTemplateVariablesController
import controllers.incorporationforms.IncorporationFormsController
import controllers.persons.PersonsController
import controllers.persontocounterpartylinkreasons.PersonToCounterPartyLinkReasonController
import controllers.persontocounterpartylinks.PersonToCounterPartyLinksController
import controllers.persontocounterpartylinktouploadeddoclinkreasons.PersonToCounterPartyLinkToUploadedDocLinkReasonController
import controllers.sessions.SessionsController
import controllers.users.UsersController
import models.contracttouploadeddocumentlinkreason.ContractToUploadedDocumentLinkReason
import models.persontocounterpartylinktouploadeddoclinkreason.PersonToCounterPartyLinkToUploadedDocLinkReason
import org.w3c.dom.css.Counter
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
            ).serveFile(it)
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

                    get("/counterParty") {
                        ContactTypesController.inputFeeds.forCounterParty(it).index()
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

                namespace("/formFeeds") {
                    get("") {
                        PersonsController.formFeeds(it).index()
                    }
                }

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

                namespace("/formFeeds") {
                    get("") {
                        PersonToCounterPartyLinkReasonController.formFeeds(it).index()
                    }
                }

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

            namespace("/incorporationForms") {

                namespace("/formFeeds") {
                    get("") {
                        IncorporationFormsController.formFeeds(it).index()
                    }
                }

                post("") {
                    IncorporationFormsController(it).create()
                }

                get("") {
                    IncorporationFormsController(it).index()
                }

                get("/:id") {
                    IncorporationFormsController(it).show()
                }

                get("/:id/edit") {
                    IncorporationFormsController(it).edit()
                }

                put("/:id") {
                    IncorporationFormsController(it).update()
                }

                delete("/:id") {
                    IncorporationFormsController(it).destroy()
                }

            }

            namespace("/counterParties") {

                namespace("/:counterPartyId/contacts") {

                    post("") {
                        CounterPartiesController.contacts(it).create()
                    }

                    get("") {
                        CounterPartiesController.contacts(it).index()
                    }

                    get("/:id") {
                        CounterPartiesController.contacts(it).show()
                    }

                    get("/:id/edit") {
                        CounterPartiesController.contacts(it).edit()
                    }

                    put("/:id") {
                        CounterPartiesController.contacts(it).update()
                    }

                    delete("/:id") {
                        CounterPartiesController.contacts(it).destroy()
                    }

                }

                namespace("/formFeeds") {
                    get("") {
                        CounterPartiesController.formFeeds(it).index()
                    }
                }

                namespace("/:counterPartyId/personToCounterPartyLinks") {
                    get("") {
                        CounterPartiesController.personToCounterPartyLinks(it).index()
                    }
                }

                post("") {
                    CounterPartiesController(it).create()
                }

                get("/:id") {
                    CounterPartiesController(it).show()
                }

                get("") {
                    CounterPartiesController(it).index()
                }


                get("/:id/edit") {
                    CounterPartiesController(it).edit()
                }

                put("/:id") {
                    CounterPartiesController(it).update()
                }


                delete("/:id") {
                    CounterPartiesController(it).destroy()
                }

            }

            namespace("/personToCounterPartyLinks") {

                namespace("/:personToCounterPartyLinkId/personToCounterPartyLinkToUploadedDocumentLinks") {
                    get("") {
                        PersonToCounterPartyLinksController.personToCounterPartyLinkToUploadedDocumentLinks(it).index()
                    }

                    post("") {
                        PersonToCounterPartyLinksController.personToCounterPartyLinkToUploadedDocumentLinks(it).create()
                    }

                    delete("/:id") {
                        PersonToCounterPartyLinksController.personToCounterPartyLinkToUploadedDocumentLinks(it).destroy()
                    }
                }

                post("") {
                    println("should create")
                    PersonToCounterPartyLinksController(it).create()
                }

                get("/:id") {
                    PersonToCounterPartyLinksController(it).show()
                }

                get("") {
                    PersonToCounterPartyLinksController(it).index()
                }

                get("/:id/edit") {
                    PersonToCounterPartyLinksController(it).edit()
                }

                put("/:id") {
                    PersonToCounterPartyLinksController(it).update()
                }

                delete("/:id") {
                    PersonToCounterPartyLinksController(it).destroy()
                }

            }

            namespace("/personToCounterPartyLinkToUploadedDocLinkReasons") {

                namespace("/formFeeds") {
                    get("") {
                        PersonToCounterPartyLinkToUploadedDocLinkReasonController.formFeeds(it).index()
                    }
                }

                post("") {
                    PersonToCounterPartyLinkToUploadedDocLinkReasonController(it).create()
                }

                get("") {
                    PersonToCounterPartyLinkToUploadedDocLinkReasonController(it).index()
                }

                get("/:id") {
                    PersonToCounterPartyLinkToUploadedDocLinkReasonController(it).show()
                }

                get("/:id/edit") {
                    PersonToCounterPartyLinkToUploadedDocLinkReasonController(it).edit()
                }

                put("/:id") {
                    PersonToCounterPartyLinkToUploadedDocLinkReasonController(it).update()
                }

                delete("/:id") {
                    PersonToCounterPartyLinkToUploadedDocLinkReasonController(it).destroy()
                }
            }

            namespace("/documentTemplateVariables") {
                post("") {
                    DocumentTemplateVariablesController(it).create()
                }

                get("") {
                    DocumentTemplateVariablesController(it).index()
                }

                get("/:id") {
                    DocumentTemplateVariablesController(it).show()
                }

                get("/:id/edit") {
                    DocumentTemplateVariablesController(it).edit()
                }

                put("/:id") {
                    DocumentTemplateVariablesController(it).update()
                }

                delete("/:id") {
                    DocumentTemplateVariablesController(it).destroy()
                }
            }

            namespace("/documentTemplates") {

                namespace("/prevalidations") {

                    post("") {
                        DocumentTemplatesController.prevalidations(it).create()
                    }

                }

                namespace("/arbitrary") {
                    get("/:id") {
                        DocumentTemplatesController.arbitrary(it).show()
                    }
                    post("") {
                        DocumentTemplatesController.arbitrary(it).create()
                    }
                }

                post("") {
                    DocumentTemplatesController(it).create()
                }

                get("") {
                    DocumentTemplatesController(it).index()
                }

                delete("/:id") {
                    DocumentTemplatesController(it).destroy()
                }


            }

            namespace("/documentTemplateCategories") {

                namespace("/inputFeeds") {
                    get("") {
                        DocumentTemplateCategoriesController.inputFeeds(it).index()
                    }

                }

                get("") {
                    DocumentTemplateCategoriesController(it).index()
                }

                get("/:id") {
                    DocumentTemplateCategoriesController(it).show()
                }

                get("/:id/edit") {
                    DocumentTemplateCategoriesController(it).edit()
                }

                post("") {
                    DocumentTemplateCategoriesController(it).create()
                }

                put("/:id") {
                    DocumentTemplateCategoriesController(it).update()
                }

                delete("/:id") {
                    DocumentTemplateCategoriesController(it).destroy()
                }

            }

            namespace("/contractCategories") {

                namespace("/formFeeds") {
                    get("") {
                        ContractCategoriesController.formFeeds(it).index()
                    }
                }

                get("") {
                    ContractCategoriesController(it).index()
                }

                get("/:id") {
                    ContractCategoriesController(it).show()
                }

                get("/:id/edit") {
                    ContractCategoriesController(it).edit()
                }

                post("") {
                    ContractCategoriesController(it).create()
                }

                put("/:id") {
                    ContractCategoriesController(it).update()
                }

                delete("/:id") {
                    ContractCategoriesController(it).destroy()
                }
            }

            namespace("/contractToUploadedDocumentLinkReasons") {

                namespace("/formFeeds") {
                    get("") {
                        ContractToUploadedDocumentLinkReasonController.formFeeds(it).index()
                    }
                }

                get("") {
                    ContractToUploadedDocumentLinkReasonController(it).index()
                }

                get("/:id") {
                    ContractToUploadedDocumentLinkReasonController(it).show()
                }

                get("/:id/edit") {
                    ContractToUploadedDocumentLinkReasonController(it).edit()
                }

                post("") {
                    ContractToUploadedDocumentLinkReasonController(it).create()
                }

                put("/:id") {
                    ContractToUploadedDocumentLinkReasonController(it).update()
                }

                delete("/:id") {
                    ContractToUploadedDocumentLinkReasonController(it).destroy()
                }
            }

            namespace("/contracts") {

                namespace("/:contractId/contractToCounterPartyLinks") {
                    delete("/:id") {
                        ContractController.contractToCounterPartyLink(it).destroy()
                    }
                    put("/:id/replaceWith/:counterPartyIdToReplaceWith") {
                        ContractController.contractToCounterPartyLink(it).replace()
                    }
                    post("") {
                        ContractController.contractToCounterPartyLink(it).create()
                    }

                    put("/:id") {
                        ContractController.contractToCounterPartyLink(it).update()
                    }

                }

                namespace("/:contractId/contractToUploadedDocumentLinks") {
                    post("") {
                        ContractController.contractToUploadedDocumentLink(it).create()
                    }
                    delete("/:id") {
                        ContractController.contractToUploadedDocumentLink(it).destroy()
                    }
                    get("") {
                        ContractController.contractToUploadedDocumentLink(it).index()
                    }
                }

                get("/manage/:id/edit") {
                    ContractController.manage(it).edit()
                }


                put("/manage/:id") {
                    ContractController.manage(it).update()
                }


                post("") {
                    ContractController(it).create()
                }
                get("") {
                    ContractController(it).index()
                }
                get("/:id") {
                    ContractController(it).show()
                }
            }

        }



    }



}

