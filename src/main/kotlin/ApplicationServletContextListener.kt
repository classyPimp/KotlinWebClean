import applicationServices.ApplicationBootstrapper
import applicationServices.ApplicationCloser
import javax.servlet.ServletContextEvent
import javax.servlet.ServletContextListener
import javax.servlet.annotation.WebListener

/**
 * Created by classypimp on 10/5/17.
 */

@WebListener
class ApplicationServletContextListener : ServletContextListener {

    override fun contextInitialized(servletContextEvent: ServletContextEvent) {
        main(arrayOf())
    }

    override fun contextDestroyed(servletContextEvent: ServletContextEvent) {
        ApplicationCloser.run()
    }
}