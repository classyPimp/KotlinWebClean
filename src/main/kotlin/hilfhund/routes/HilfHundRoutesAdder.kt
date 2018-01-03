package hilfhund.routes

import hilfhund.controllers.GenerationController
import hilfhund.controllers.HilfhundHomeController
import hilfhund.controllers.MigrationsController
import router.RoutesDrawer
import router.src.Router

object HilfHundRoutesAdder: RoutesDrawer(Router) {

    override fun run(){

        get("/hilfhund") {
            HilfhundHomeController(it).index()
        }

        get("/hilfhund/*") {
            HilfhundHomeController(it).index()
        }

        namespace("/hilfhund") {
            namespace("/migrations") {
                post("/create") {
                    MigrationsController(it).create()
                }
                post("/list") {
                    MigrationsController(it).listMigrations()
                }
                post("/migrate") {
                    MigrationsController(it).migrate()
                }
                post("/rollback") {
                    MigrationsController(it).rollBack()
                }
            }
            namespace("/generate") {
                post("/model") {
                    GenerationController(it).generateModel()
                }
                post("/jooq") {
                    GenerationController(it).jooqGenerate()
                }
                post("/factory") {
                    GenerationController(it).generateFactory()
                }
                post("/toJsonSerializer") {
                    GenerationController(it).generateToJsonSerializer()
                }

                post("/jsModel") {
                    GenerationController(it).generateJsModel()
                }

                post("/reactComponent") {
                    GenerationController(it).generateReactComponent()
                }

                post("/composer") {
                    GenerationController(it).generateComposer()
                }

                post("/validator") {
                    GenerationController(it).generateValidator()
                }

                post("/updater") {
                    GenerationController(it).generateUpdater()
                }

                post("/requestParametersWrapper") {
                    GenerationController(it).generateRequestParametersWrapper()
                }

                post("/daos") {
                    GenerationController(it).generateDaos()
                }

                post("/controller") {
                    GenerationController(it).generateController()
                }
            }
        }

    }

}