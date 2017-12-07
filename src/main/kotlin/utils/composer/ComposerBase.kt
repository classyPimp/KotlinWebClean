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
        } catch(error: Throwable) {
            fail(error)
            return
        }
        try {
            compose()
        } catch (error: Throwable) {
            if (error is DataAccessException) {
                fail(error.cause!!)
                return
            }
            fail(error)
            return
        }
        if (failed) {
            fail(Throwable("Unknown Reason"))
        }
        success()
    }

    fun failImmediately(failure: Throwable) {
        failed = true
        throw failure
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