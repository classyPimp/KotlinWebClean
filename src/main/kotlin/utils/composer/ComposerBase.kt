package utils.composer

import org.jooq.exception.DataAccessException

/**
 * Created by Муса on 20.11.2017.
 */
abstract class ComposerBase {

    val recordedMultipleFailures: MutableList<Throwable> by lazy { mutableListOf<Throwable>() }
    var failed: Boolean = false
    var hasMultipleFailures: Boolean = false

    fun run() {
        try {
            beforeCompose()
        } catch(error: Exception) {
            failImmediately(error)
            return
        }
        try {
            compose()
        } catch (error: Throwable) {
            if (error is DataAccessException) {
                failImmediately(error.cause!!)
                return
            }
            failImmediately(error)
            return
        }
        if (failed) {
            return
        }
        success()
    }

    fun failImmediately(failure: Throwable) {
        failed = true
        fail(failure)
    }

    private fun recordFailureAndContinue(failure: Throwable){
        this.failed = true
        this.hasMultipleFailures = true
        this.recordedMultipleFailures.add(failure)
    }


    abstract fun compose()

    abstract fun beforeCompose()

    abstract fun fail(error: Throwable)

    abstract fun success()

}