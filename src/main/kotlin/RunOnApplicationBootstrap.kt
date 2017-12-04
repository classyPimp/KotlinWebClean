import clientimplementedutils.security.JwtPlucker
import orm.dependencymanagement.OrmDependenciesManager
import router.src.Router
import dependencymanagement.MicroFrameworkDependencyManager
import hilfhund.routes.HilfHundRoutesAdder
import org.zeroturnaround.exec.ProcessExecutor
import utils.sessions.JwtSessionHandler
import java.io.File

/**
 * Created by Муса on 01.11.2017.
 */
class RunOnApplicationBootstrap: Runnable {

    override fun run() {
        initializeApplicationComponent()
        injectRequired()
        drawRoutes()
    }

    fun drawRoutes() {
        routes.RoutesConfig(Router).run()
        HilfHundRoutesAdder.run()
    }

    private fun initializeApplicationComponent() {
        App.component = daggerComponents.DaggerApplicationComponent.builder().build()
    }

    private fun injectRequired() {
        OrmDependenciesManager.provider = OrmDependenciesProvider
        MicroFrameworkDependencyManager.provider = MicroframeworkDependenciesProvider
    }


}