package applicationServices

import dependencymanagement.MicroFrameworkDependencyManager


/**
 * Created by classypimp on 9/12/17.
 */

object ApplicationBootstrapper : Runnable {

    val userDefinedApplicatinBootstrapper: MutableList<Runnable> = mutableListOf()

    override fun run(){

        userDefinedApplicatinBootstrapper.forEach {
            it.run()
        }
        MicroFrameworkDependencyManager.provider.run()

    }

}