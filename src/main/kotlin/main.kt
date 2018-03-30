import applicationServices.ApplicationBootstrapper
import applicationServices.ApplicationCloser


//
///**
// * Created by classypimp on 9/6/17.
// */
//
//
//

fun main(args: Array<String>) {
    ApplicationBootstrapper.userDefinedApplicatinBootstrapper.add(RunOnApplicationBootstrap())
    ApplicationCloser.userDefinedApplicationCloser.add(RunOnApplicationShutdown())
    ApplicationBootstrapper.run()
}


