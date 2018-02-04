
import orm.dependencymanagement.OrmDependenciesManager
import router.src.Router
import dependencymanagement.MicroFrameworkDependencyManager
import hilfhund.routes.HilfHundRoutesAdder




/**
 * Created by Муса on 01.11.2017.
 */
class RunOnApplicationBootstrap: Runnable {

    override fun run() {
        initializeApplicationComponent()
        injectRequired()
        drawRoutes()
    }

    private fun initializeApplicationComponent() {
        App.component = daggerComponents.DaggerApplicationComponent.builder().build()
    }

    private fun injectRequired() {
        OrmDependenciesManager.provider = OrmDependenciesProvider
        MicroFrameworkDependencyManager.provider = MicroframeworkDependenciesProvider
    }

    fun drawRoutes() {
        routes.RoutesConfig(Router).run()
        HilfHundRoutesAdder.run()
    }



}