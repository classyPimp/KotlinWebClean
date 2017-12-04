import java.io.Closeable

/**
 * Created by Муса on 01.11.2017.
 */
class RunOnApplicationShutdown : Runnable {

    override fun run() {
        (App.component.dataSource() as Closeable).close()
    }

}