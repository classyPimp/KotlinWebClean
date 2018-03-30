package permissionsystem

/**
 * Created by Муса on 30.03.2018.
 */
class CurrentUserUnauthorizedError(override val message: String = "unauthorized"): Throwable(message)