package utils.composer.composerexceptions

/**
 * Created by Муса on 20.12.2017.
 */
class BadRequestError(message: String = "bad request, invalid request data"): Throwable(message) {
}