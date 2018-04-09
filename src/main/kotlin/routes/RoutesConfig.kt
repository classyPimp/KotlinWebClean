package routes

import controllers.HomeController
import controllers.approval.ApprovalOfContractController
import controllers.approvalrejection.ApprovalRejectionOfApprovalOfContractController
import controllers.approvalstep.ApprovalStepOfApprovalOfContractController
import controllers.approvalsteptoapproverlink.ApprovalStepToApproverLinkOfContractController
import controllers.contacttypes.ContactTypesController
import controllers.contract.ContractController
import controllers.contractcategories.ContractCategoriesController
import controllers.contractstatus.ContractStatusForContractController
import controllers.contracttocounterpartylink.ContractToCounterPartyLinkForContractController
import controllers.contracttouploadeddocumentlinkreason.ContractToUploadedDocumentLinkReasonController
import controllers.counterparties.CounterPartiesController
import controllers.discussion.DiscussionController
import controllers.discussionmessage.DiscussionMessageOfApprovalRejectionOfContractController
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

                get("/forSearchForm") {
                    UsersController(it).forSearchFormIndex()
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

                get("/:id/showMinimal") {
                    PersonsController(it).showMinimal()
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

                get("/:personId/contacts") {
                    PersonsController.contacts(it).index()
                }

                get("/:personId/contacts/edit") {
                    PersonsController.contacts(it).indexForEdit()
                }


                delete("/:personId/contacts/:id") {
                    PersonsController.contacts(it).delete()
                }

                put("/:personId/contacts/:id") {
                    PersonsController.contacts(it).update()
                }

                get("/:personId/personToCounterPartyLinks") {
                    PersonToCounterPartyLinksController(it).indexForPerson()
                }

                get("/:personId/personToCounterPartyLinks/:id/editGeneralInfo") {
                    PersonToCounterPartyLinksController(it).forPersonEditGeneralInfo()
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
                    get("/:id/edit") {
                        CounterPartiesController.personToCounterPartyLinks(it).edit()
                    }
                    get("/edit") {
                        CounterPartiesController.personToCounterPartyLinks(it).indexEdit()
                    }
                    get("/:id") {
                        CounterPartiesController.personToCounterPartyLinks(it).show()
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

                namespace("/:contractId/manage") {
                    namespace("/monetaryObligations") {
                        post("") {
                            ContractController.manage.monetaryObligation(it).create()
                        }
                        get("") {
                            ContractController.manage.monetaryObligation(it).index()
                        }
                    }
                }

                namespace("/:contractId/approval") {
                    post("") {
                        ApprovalOfContractController(it).create()
                    }

                    get("") {
                        ApprovalOfContractController(it).show()
                    }
                }

                namespace("/:contractId/contractToCounterPartyLinks") {
                    get("") {
                        ContractToCounterPartyLinkForContractController(it).index()
                    }
                    get("/edit") {
                        ContractToCounterPartyLinkForContractController(it).indexEdit()
                    }
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
                    get("") {
                        ContractController.contractToUploadedDocumentLink(it).index()
                    }
                    post("") {
                        ContractController.contractToUploadedDocumentLink(it).create()
                    }
                    get("/indexEdit") {
                        ContractController.contractToUploadedDocumentLink(it).indexEdit()
                    }
                    delete("/:id") {
                        ContractController.contractToUploadedDocumentLink(it).destroy()
                    }
                    put("/:id") {
                        ContractController.contractToUploadedDocumentLink(it).update()
                    }
                }

                namespace("/:contractId/contractStatuses") {
                    get("") {
                        ContractStatusForContractController(it).show()
                    }
                    get("/edit") {
                        ContractStatusForContractController(it).edit()
                    }
                    put("/:id") {
                        ContractStatusForContractController(it).update()
                    }
                }

                get("/manage/:id/edit") {
                    ContractController.manage(it).edit()
                }

                get("/:contractId/editGeneralInfo") {
                    ContractController(it).editGeneralInfo()
                }

                put("/:contractId/generalInfo") {
                    ContractController(it).updateGeneralInfo()
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
                get("/:contractId/generalInfo") {
                    ContractController(it).showGeneralInfo()
                }
            }

            namespace("/approvalStepToApproverLink/ofContract") {
                put("/:approvalStepToApproverLinkId") {
                    ApprovalStepToApproverLinkOfContractController(it).approve()
                }


            }

            namespace("/discussion") {
                get("/:discussionId") {
                    DiscussionController(it).show()
                }

                namespace("/:discussionId") {
                    namespace("/ofApprovalRejectionOfContract") {
                        namespace("/discussionMessage") {
                            post("") {
                                DiscussionMessageOfApprovalRejectionOfContractController(it).create()
                            }
                        }
                    }
                }
            }

            namespace("/approval/ofContract") {
                namespace("/:approvalId/approvalStep") {
                    post("") {
                        ApprovalStepOfApprovalOfContractController(it).create()
                    }
                }
                namespace("/:approvalId") {
                    post("/approvalRejection") {
                        ApprovalRejectionOfApprovalOfContractController(it).create()
                    }
                }
            }


        }

        namespace("/monetaryObligations") {
            put("/:id") {
                ContractController.manage.monetaryObligation(it).update()
            }
        }



    }



}

