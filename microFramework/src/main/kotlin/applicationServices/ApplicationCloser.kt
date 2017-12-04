package applicationServices

/**
 * Created by Муса on 06.10.2017.
 */
object ApplicationCloser {

    val userDefinedApplicationCloser: MutableList<Runnable> = mutableListOf()

    fun run() {
        userDefinedApplicationCloser.forEach {
            it.run()
        }
    }

}